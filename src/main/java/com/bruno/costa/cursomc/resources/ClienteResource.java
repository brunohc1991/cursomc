package com.bruno.costa.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.costa.cursomc.domain.Categoria;
import com.bruno.costa.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class ClienteResource {
	
	@Autowired
	private CategoriaService catServ;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		
		Categoria obj;
		obj = catServ.findById(id);

		return ResponseEntity.ok(obj);
		
	}
}
