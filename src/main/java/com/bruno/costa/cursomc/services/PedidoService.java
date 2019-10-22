package com.bruno.costa.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.Pedido;
import com.bruno.costa.cursomc.repositories.PedidoRepository;
import com.bruno.costa.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository repo;
	
	public Pedido findById(Long id){
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID = " + id +", Tipo: " + Pedido.class.getName()));
	}
	
}
