package br.com.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.repository.AcessoRepository;
import br.com.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {
	
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	
	
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/salvarAcesso")/*mapeando a url para receber JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {/*receber JSON e converte para Objeto*/
		
		Acesso acessoSalvo = acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	

	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/deleteAcesso")/*mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {/*receber JSON e converte para Objeto*/
		
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso removido com sucesso",HttpStatus.OK);
	}
	

}
