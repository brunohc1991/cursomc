package com.bruno.costa.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bruno.costa.cursomc.domain.Cliente;
import com.bruno.costa.cursomc.dto.ClienteDTO;
import com.bruno.costa.cursomc.dto.ClienteNewDTO;
import com.bruno.costa.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Cliente obj;
		obj = service.findById(id);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO entidadeDTO){
		Cliente entidade = service.fromDto(entidadeDTO);
		entidade = service.insert(entidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entidade.getId()).toUri();
		return ResponseEntity.created(uri).build();		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO entidadeDTO, @PathVariable Long id){
		entidadeDTO.setId(id);
		Cliente entidade = service.fromDto(entidadeDTO);
		entidade = service.update(entidade);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map(entidade -> new ClienteDTO(entidade)).collect(Collectors.toList());
		return ResponseEntity.ok(listDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPerPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linePerPage", defaultValue = "24") Integer linePerPage, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		
		Page<Cliente> list = service.findPage(page, linePerPage, direction, orderBy);
		Page<ClienteDTO> listDTO = list.map(entidade -> new ClienteDTO(entidade));
		
		return ResponseEntity.ok(listDTO);
	}
}

