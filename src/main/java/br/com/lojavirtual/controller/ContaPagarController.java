package br.com.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.lojavirtual.ExceptionMentoriaJava;
import br.com.lojavirtual.model.ContaPagar;
import br.com.lojavirtual.repository.ContaPagarRepository;


@Controller
@RestController
public class ContaPagarController  {

	
	@Autowired
	private ContaPagarRepository contaPagarRepository;

	//SALVAR Produto
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/salvarContaPagar")/*mapeando a url para receber JSON*/
	public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody @Valid ContaPagar contaPagar) throws ExceptionMentoriaJava {/*receber JSON e converte para Objeto*/
		
		if(contaPagar.getEmpresa()==null || contaPagar.getEmpresa().getId() < 0) {
			 throw new ExceptionMentoriaJava("A Conta deve ser informada ");
		}
		
		if(contaPagar.getPessoa()==null || contaPagar.getEmpresa().getId() < 0) {
			
			 throw new ExceptionMentoriaJava("A Pessoa responsável deve ser informada ");
		}
		
		if(contaPagar.getPessoa_fornecedor()==null || contaPagar.getPessoa_fornecedor().getId() < 0) {
			
			 throw new ExceptionMentoriaJava("O fornecedor responsável deve ser informado ");
		}
		
		//verificar se existe uma conta com a mesma descrição no Banco de dados
		if(contaPagar.getId()==null) {
			List<ContaPagar> contaspagar = contaPagarRepository.buscarContaPorDesc(contaPagar.getDescricao().toUpperCase().trim());
			if(!contaspagar.isEmpty()) {
				
				 throw new ExceptionMentoriaJava("Já existe conta a pagar com a mesma descrição! ");
			}
			
		}
		
		ContaPagar contaPagarSalva = contaPagarRepository.save(contaPagar);
		return new ResponseEntity<ContaPagar>(contaPagarSalva, HttpStatus.OK);
	}
	

	//DELETAR PRODUTO
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/deleteContaPagar")/*mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteContaPagar(@RequestBody ContaPagar contaPagar) {/*receber JSON e converte para Objeto*/
		
		contaPagarRepository.deleteById(contaPagar.getId());
		return new ResponseEntity<String>("A Conta a Pagar foi removido com sucesso!",HttpStatus.OK);
	}
	
	
	//DELETAR PRODUTO POR ID
	@ResponseBody 
	@DeleteMapping(value ="/deleteContapagarPorId/{id}")
	public ResponseEntity<?> deleteContapagarPorId(@PathVariable("id") Long id) {
		
		contaPagarRepository.deleteById(id);
		return new ResponseEntity<String>("Conta a pagar removida por id com sucesso!",HttpStatus.OK);
    }
	
	@ResponseBody
	@GetMapping(value = "/obterContarPagar/{id}")
	public ResponseEntity<ContaPagar> obterContarPagar(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		ContaPagar contaPagar= contaPagarRepository.findById(id).orElse(null);
		
		if(contaPagar == null) {
			
			throw new ExceptionMentoriaJava("A conta a pagar com ID: " + id + "não foi encontrado");
			
		}
		
		return new ResponseEntity<ContaPagar>(contaPagar,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/buscarContaPorDesc/{desc}")
	public ResponseEntity<List<ContaPagar>> buscarContaPorDesc(@PathVariable("desc") String desc) { 
		
		List<ContaPagar> contas = contaPagarRepository.buscarContaPorDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<ContaPagar>>(contas,HttpStatus.OK);
	}
	

}
