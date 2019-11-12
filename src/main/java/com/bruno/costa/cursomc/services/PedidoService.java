package com.bruno.costa.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.ItemPedido;
import com.bruno.costa.cursomc.domain.PagamentoComBoleto;
import com.bruno.costa.cursomc.domain.Pedido;
import com.bruno.costa.cursomc.domain.enums.EstadoPagamento;
import com.bruno.costa.cursomc.repositories.ItemPedidoRepository;
import com.bruno.costa.cursomc.repositories.PagamentoRepository;
import com.bruno.costa.cursomc.repositories.PedidoRepository;
import com.bruno.costa.cursomc.repositories.ProdutoRepository;
import com.bruno.costa.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagRepo;
	
	@Autowired
	private ProdutoRepository prodRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	public Pedido findById(Long id){
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID = " + id +", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido entidade) {
		entidade.setId(null);
		entidade.setDate(new Date());
		entidade.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		entidade.getPagamento().setPedido(entidade);
		if(entidade.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) entidade.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, entidade.getDate());
		}
		
		entidade = repo.save(entidade);
		pagRepo.save(entidade.getPagamento());
		
		for(ItemPedido item : entidade.getItens()) {
			item.setDesconto(0.0);
			item.setPreco(prodRepo.findById(item.getProduto().getId()).get().getPreco());
			item.setPedido(entidade);
		}
		
		itemPedidoRepo.saveAll(entidade.getItens());
		
		return entidade;
	}
}
