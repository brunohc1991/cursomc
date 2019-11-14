package com.bruno.costa.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class DBService {
	
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
	
	public void instatiadeTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 1999.99);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 79.99);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
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
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
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
