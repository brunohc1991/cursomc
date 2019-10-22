package com.bruno.costa.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruno.costa.cursomc.domain.Categoria;
import com.bruno.costa.cursomc.domain.Cidade;
import com.bruno.costa.cursomc.domain.Cliente;
import com.bruno.costa.cursomc.domain.Endereco;
import com.bruno.costa.cursomc.domain.Estado;
import com.bruno.costa.cursomc.domain.ItemPedido;
import com.bruno.costa.cursomc.domain.Pagamento;
import com.bruno.costa.cursomc.domain.PagamentoComBoleto;
import com.bruno.costa.cursomc.domain.PagamentoComCartao;
import com.bruno.costa.cursomc.domain.Pedido;
import com.bruno.costa.cursomc.domain.Produto;
import com.bruno.costa.cursomc.domain.enums.EstadoPagamento;
import com.bruno.costa.cursomc.domain.enums.TipoCliente;
import com.bruno.costa.cursomc.repositories.CategoriaRepository;
import com.bruno.costa.cursomc.repositories.CidadeRepository;
import com.bruno.costa.cursomc.repositories.ClienteRepository;
import com.bruno.costa.cursomc.repositories.EnderecoRepository;
import com.bruno.costa.cursomc.repositories.EstadoRepository;
import com.bruno.costa.cursomc.repositories.ItemPedidoRepository;
import com.bruno.costa.cursomc.repositories.PagamentoRepository;
import com.bruno.costa.cursomc.repositories.PedidoRepository;
import com.bruno.costa.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 1999.99);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 79.99);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est1.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "40191081817", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("1313456554","345654433"));
		
		Endereco ende1 = new Endereco(null, "Rua: Flores", "987", "Fundos", "Pinheirinho", "84930000", cli1, cid1);
		Endereco ende2 = new Endereco(null, "Av. Matos", "KM 1009", "Rodovia", "Pinheirinho", "84930000", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(ende1, ende2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 10:32"), cli1, cli1.getEnderecos().get(0));
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 19:35"), cli1, cli1.getEnderecos().get(1));
		
		Pagamento pagTo1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagTo1);
		
		Pagamento pagTo2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2019 00:00"), null);
		ped2.setPagamento(pagTo2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(ende1, ende2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagTo1, pagTo2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 1999.99);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 79.99);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip3));
		ped2.getItens().addAll(Arrays.asList(ip2));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
