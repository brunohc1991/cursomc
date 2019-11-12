package com.bruno.costa.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.bruno.costa.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pgtoBoleto, Date dataPedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pgtoBoleto.setDataVencimento(calendar.getTime());
	}
}
