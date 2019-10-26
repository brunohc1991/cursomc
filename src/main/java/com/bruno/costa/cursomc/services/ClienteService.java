package com.bruno.costa.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.Cidade;
import com.bruno.costa.cursomc.domain.Cliente;
import com.bruno.costa.cursomc.domain.Endereco;
import com.bruno.costa.cursomc.domain.enums.TipoCliente;
import com.bruno.costa.cursomc.dto.ClienteDTO;
import com.bruno.costa.cursomc.dto.ClienteNewDTO;
import com.bruno.costa.cursomc.repositories.ClienteRepository;
import com.bruno.costa.cursomc.repositories.EnderecoRepository;
import com.bruno.costa.cursomc.services.exception.DataIntegrityException;
import com.bruno.costa.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	public Cliente findById(Long id){
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID = " + id +", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente entidade) {
		Cliente newEntidade = findById(entidade.getId());
		uddateData(newEntidade, entidade);
		return repo.save(newEntidade);
	}
	
	public void deleteById(Long id) {
		findById(id);
		try {
			repo.deleteById(id);;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Cliente que possui pedidos vinculados.");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linePerPage, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linePerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}
	
	public Cliente fromDto(ClienteNewDTO dto) {
		Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.toEnum(dto.getTipo()));
		Cidade cid = new Cidade(dto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(dto.getTelefone1());
		
		if(dto.getTelefone2() != null) {
			cli.getTelefones().add(dto.getTelefone2());
		}
		if(dto.getTelefone3() != null) {
			cli.getTelefones().add(dto.getTelefone3());
		}
		
		return cli;
	}
	
	private void uddateData(Cliente newEntidade, Cliente entidade) {
		newEntidade.setNome(entidade.getNome());
		newEntidade.setEmail(entidade.getEmail());
	}
	
	public Cliente insert(Cliente entidade) {
		entidade.setId(null);
		entidade = repo.save(entidade);
		enderecoRepo.saveAll(entidade.getEnderecos());
		return entidade;
	}
	
	
}
