package com.bruno.costa.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.costa.cursomc.domain.Produto;
import com.bruno.costa.cursomc.dto.ProdutoDTO;
import com.bruno.costa.cursomc.resources.utils.URL;
import com.bruno.costa.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Produto obj;
		obj = service.findById(id);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPerPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linePerPage", defaultValue = "24") Integer linePerPage, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		List<Long> ids = URL.decodeLongList(categorias);
		String nomeDecode = URL.decodeParam(nome);
		Page<Produto> list = service.search(nomeDecode, ids, page, linePerPage, direction, orderBy);
		Page<ProdutoDTO> listDTO = list.map(entidade -> new ProdutoDTO(entidade));
		
		return ResponseEntity.ok(listDTO);
	}
}

