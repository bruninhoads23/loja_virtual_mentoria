package br.com.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	
	/*pode fazer qualquer tipo de validação antes de salvar*/
	public Acesso save(Acesso acesso) {
		return acessoRepository.save(acesso);
	}

}
