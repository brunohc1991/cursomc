package com.bruno.costa.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.costa.cursomc.domain.Cliente;
import com.bruno.costa.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class CategoriaResource {
	
	@Autowired
	private ClienteService cliServ;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		
		Cliente obj;
		obj = cliServ.findById(id);

		return ResponseEntity.ok(obj);
		
	}
}

