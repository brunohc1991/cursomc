package com.bruno.costa.cursomc.dto;

import java.io.Serializable;

import com.bruno.costa.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Double preco;
	
	public ProdutoDTO() {}
	
	public ProdutoDTO(Produto entidade) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.preco = entidade.getPreco();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
	
}
