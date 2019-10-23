package com.bruno.costa.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.Categoria;
import com.bruno.costa.cursomc.repositories.CategoriaRepository;
import com.bruno.costa.cursomc.services.exception.DataIntegrityException;
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

	public Categoria update(Categoria entidade) {
		findById(entidade.getId());
		return repo.save(entidade);
	}
	
	public void deleteById(Long id) {
		findById(id);
		try {
			repo.deleteById(id);;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Categoria que possui produtos.");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
}
