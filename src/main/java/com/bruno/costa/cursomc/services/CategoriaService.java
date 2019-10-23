package com.bruno.costa.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.Categoria;
import com.bruno.costa.cursomc.repositories.CategoriaRepository;
import com.bruno.costa.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repo;
	
	public Categoria findById(Long id){
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID = " + id +", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria entidade) {
		entidade.setId(null);
		return repo.save(entidade);
	}
	
}
