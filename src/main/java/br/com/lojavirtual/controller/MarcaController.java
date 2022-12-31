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
import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.model.MarcaProduto;
import br.com.lojavirtual.repository.AcessoRepository;
import br.com.lojavirtual.repository.MarcaRepository;
import br.com.lojavirtual.service.AcessoService;

@Controller
@RestController
public class MarcaController {
	
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	
	
	//SALVAR MARCA
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/salvarMarca")/*mapeando a url para receber JSON*/
	public ResponseEntity<MarcaProduto> salvarMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionMentoriaJava {/*receber JSON e converte para Objeto*/
		
		if(marcaProduto.getId() == null) {

		List<MarcaProduto> marcas = marcaRepository.buscarMarcaDesc(marcaProduto.getNomeDesc().toUpperCase());
		 if(!marcas.isEmpty()) {
			 throw new ExceptionMentoriaJava("Já existe acesso com a descrição " + marcaProduto.getNomeDesc());
		 }
		}

		MarcaProduto marcaProdtoSalva = marcaRepository.save(marcaProduto);
		return new ResponseEntity<MarcaProduto>(marcaProdtoSalva, HttpStatus.OK);
	}
	

	//DELETAR MARCA
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/deleteMarca")/*mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteMarca(@RequestBody MarcaProduto marcaProduto) {/*receber JSON e converte para Objeto*/
		
		marcaRepository.deleteById(marcaProduto.getId());
		return new ResponseEntity("Marca do Produto removido com Sucesso",HttpStatus.OK);
	}
	
	
	//DELETA MARCA POR ID
	@ResponseBody 
	@DeleteMapping(value ="/deleteMarcaPorId/{id}")
	public ResponseEntity<?> deleteMarcaPorId(@PathVariable("id") Long id) {
		
		marcaRepository.deleteById(id);
		return new ResponseEntity("Marca do Produto removida por id com sucesso",HttpStatus.OK);
    }
	
	//OBTEM MARCA PASSANDO ID
	@ResponseBody
	@GetMapping(value = "/obterMarca/{id}")
	public ResponseEntity<MarcaProduto> obterMarca(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		MarcaProduto marca = marcaRepository.findById(id).orElse(null);
		
		if(marca == null) {
			
			throw new ExceptionMentoriaJava("Marca do Produto com ID: " + id + "não foi encontrada");
			
		}
		
		return new ResponseEntity<MarcaProduto>(marca,HttpStatus.OK);
	}
	
	//OBTEM MARCA PASSANDO O NOME
	@ResponseBody
	@GetMapping(value = "/buscarPorNomeMarca/{nome}")
	public ResponseEntity<List<MarcaProduto>> buscarPorNomeMarca(@PathVariable("nome") String nome) { 
		
		List<MarcaProduto> marcas = marcaRepository.buscarMarcaDesc(nome.toUpperCase());
		
		return new ResponseEntity<List<MarcaProduto>>(marcas,HttpStatus.OK);
	}
	

}
