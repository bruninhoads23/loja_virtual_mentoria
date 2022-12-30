package br.com.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.ExceptionMentoriaJava;
import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.model.CategoriaProduto;
import br.com.lojavirtual.model.dto.CategoriaProdutoDTO;
import br.com.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@ResponseBody
	@PostMapping(value = "/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoria (@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava{
		
		if(categoriaProduto.getEmpresa()==null || (categoriaProduto.getEmpresa().getId() == null||categoriaProduto.getEmpresa().getId() <= 0)) {
			throw new ExceptionMentoriaJava("A Empresa deve ser informada");
			
		}
		//SE O ID NÃO EXISITR E A DESCRIÇÃO DA CATEGORIA JÁ EXISITR  
		if( categoriaProduto.getId()==null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase().trim())) {
			
			throw new ExceptionMentoriaJava("Não pode cadastrar categoria com mesmo nome");
			
		}
		
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();
		categoriaProdutoDTO.setId(categoriaSalva.getId());
		categoriaProdutoDTO.setNomeDesc(categoriaSalva.getNomeDesc());
		categoriaProdutoDTO.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		
		return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoDTO,HttpStatus.OK);
	}
	
	
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/deleteCategoria")/*mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) {/*receber JSON e converte para Objeto*/
		
		categoriaProdutoRepository.deleteById(categoriaProduto.getId());
		return new ResponseEntity("Categoria  removida",HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/buscarPorCategoria/{desc}")
	public ResponseEntity<List<CategoriaProduto>> buscarPorCategoria(@PathVariable("desc") String desc) { 
		
		List<CategoriaProduto> categoria = categoriaProdutoRepository.buscarDescCategoria(desc.toUpperCase());
		
		return new ResponseEntity<List<CategoriaProduto>>(categoria,HttpStatus.OK);
	}
	
	
	@ResponseBody 
	@DeleteMapping(value ="/deleteCategoriaProdPorId/{id}")
	public ResponseEntity<?> deleteCategoriaProdPorId(@PathVariable("id") Long id) {
		
		categoriaProdutoRepository.deleteById(id);
		return new ResponseEntity("Categoria do Produto removido por id",HttpStatus.OK);
    }
	
	
	@ResponseBody
	@GetMapping(value = "/obterCategoriaProd/{id}")
	public ResponseEntity<CategoriaProduto> obterCategoriaProd(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(id).orElse(null);
		
		if(categoriaProduto == null) {
			
			throw new ExceptionMentoriaJava("Categoria do Produto  com ID: " + id + "não foi encontrado");
			
		}
		
		return new ResponseEntity(categoriaProduto,HttpStatus.OK);
	}
	
	
	  
	
	
	

}
