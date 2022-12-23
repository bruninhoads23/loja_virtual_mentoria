package br.com.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.ExceptionMentoriaJava;
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
	
	
	
	//SALVAR ACESSO
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/salvarAcesso")/*mapeando a url para receber JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava {/*receber JSON e converte para Objeto*/
		
		if(acesso.getId() == null) {

		List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
		 if(!acessos.isEmpty()) {
			 throw new ExceptionMentoriaJava("Já existe acesso com a descrição " + acesso.getDescricao());
		 }
		
		}

		Acesso acessoSalvo = acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	

	//DELETAR ACESSO
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/deleteAcesso")/*mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {/*receber JSON e converte para Objeto*/
		
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso removido",HttpStatus.OK);
	}
	
	
	//DELETAR ACESSO POR ID
	@ResponseBody 
	@DeleteMapping(value ="/deleteAcessoPorId/{id}")
	public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) {
		
		acessoRepository.deleteById(id);
		return new ResponseEntity("Acesso removido por id",HttpStatus.OK);
    }
	
	@ResponseBody
	@GetMapping(value = "/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		
		if(acesso == null) {
			
			throw new ExceptionMentoriaJava("Não encontrou o acesso com o código" + id);
			
		}
		
		return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/buscarPorDesc/{desc}")
	public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) { 
		
		List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<Acesso>>(acesso,HttpStatus.OK);
	}
	

}
