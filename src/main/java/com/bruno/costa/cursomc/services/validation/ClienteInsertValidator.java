package com.bruno.costa.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bruno.costa.cursomc.domain.enums.TipoCliente;
import com.bruno.costa.cursomc.dto.ClienteNewDTO;
import com.bruno.costa.cursomc.repositories.ClienteRepository;
import com.bruno.costa.cursomc.resources.exception.FieldMessage;
import com.bruno.costa.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {}

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		
		if(TipoCliente.PESSOA_FISICA.getCod() == objDto.getTipo() && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}
		
		if(TipoCliente.PESSOA_JURIDICA.getCod() == objDto.getTipo() && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}
		
		if(repository.findByEmail(objDto.getEmail()) != null) {
			list.add(new FieldMessage("email", "O e-mail informado já esta cadastrado."));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
