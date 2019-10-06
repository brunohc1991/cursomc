package com.bruno.costa.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.costa.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		
		Categoria cat1 = new Categoria(1L, "Informatica");
		Categoria cat2 = new Categoria(2L, "Escritório");
		
		List<Categoria> listaCategorias = new ArrayList<>();
		
		listaCategorias.add(cat1);
		listaCategorias.add(cat2);
		
		return listaCategorias;
	}
}

