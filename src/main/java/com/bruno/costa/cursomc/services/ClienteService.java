package com.bruno.costa.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.Cliente;
import com.bruno.costa.cursomc.dto.ClienteDTO;
import com.bruno.costa.cursomc.repositories.ClienteRepository;
import com.bruno.costa.cursomc.services.exception.DataIntegrityException;
import com.bruno.costa.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repo;
	
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
			throw new DataIntegrityException("Não é possível excluir uma Cliente que possui registros vinculados.");
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
	
	private void uddateData(Cliente newEntidade, Cliente entidade) {
		newEntidade.setNome(entidade.getNome());
		newEntidade.setEmail(entidade.getEmail());
	}
}
