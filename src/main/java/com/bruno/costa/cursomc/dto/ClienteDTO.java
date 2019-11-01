package com.bruno.costa.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.bruno.costa.cursomc.domain.Cliente;
import com.bruno.costa.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 3, max = 120, message = "O tamanho deve estar entre 3 - 120 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Email(message = "E-mail invalido.")
	private String email;
	
	public ClienteDTO() {}
	
	public ClienteDTO(Long id, String nome, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	
	public ClienteDTO(Cliente entidade) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.email = entidade.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
