package com.bruno.costa.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.Cliente;
import com.bruno.costa.cursomc.repositories.ClienteRepository;
import com.bruno.costa.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repo;
	
	public Cliente findById(Long id){
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID = " + id +", Tipo: " + Cliente.class.getName()));
	}
	
}
