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
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.repository.ProdutoRepository;

@Controller
@RestController
public class ProdutoController  {

	
	@Autowired
	private ProdutoRepository produtoRepository;

	//SALVAR Produto
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/salvarProduto")/*mapeando a url para receber JSON*/
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava {/*receber JSON e converte para Objeto*/
		
		if(produto.getEmpresa()==null || produto.getEmpresa().getId() < 0) {
			 throw new ExceptionMentoriaJava("A empresa deve ser informada ");
		}
		
		if(produto.getId() == null) {
		List<Produto>  produtos = produtoRepository.buscarNomeProduto(produto.getNome().toUpperCase(), produto.getEmpresa().getId());
		 if(!produtos.isEmpty()) {
			 throw new ExceptionMentoriaJava("Já existe Produto com este nome " + produto.getNome());
		 }
		}
		
		//empresa é nova e id dela não existe!
		
		
		//categoria do produto não foi passado como parâmetro ou id da categoria  não existe!
		if(produto.getCategoriaProduto() == null|| produto.getCategoriaProduto().getId() < 0) {
			 throw new ExceptionMentoriaJava("A Categoria do Produto deve ser informada ");
			
		}
		
		//marca do produto não foi passado como parâmetro ou id da marca  não existe!
				if(produto.getMarcaProduto() == null|| produto.getMarcaProduto().getId() < 0) {
					 throw new ExceptionMentoriaJava("A Marca do Produto deve ser informada ");
					
				}

		Produto produtoSalvo = produtoRepository.save(produto);
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
	}
	

	//DELETAR PRODUTO
	@ResponseBody /*para poder dar um retorno para API*/
	@PostMapping(value ="/deleteProduto")/*mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteProduto(@RequestBody Produto produto) {/*receber JSON e converte para Objeto*/
		
		produtoRepository.deleteById(produto.getId());
		return new ResponseEntity("Produto removido com sucesso!",HttpStatus.OK);
	}
	
	
	//DELETAR PRODUTO POR ID
	@ResponseBody 
	@DeleteMapping(value ="/deleteProdutoPorId/{id}")
	public ResponseEntity<?> deleteProdutoPorId(@PathVariable("id") Long id) {
		
		produtoRepository.deleteById(id);
		return new ResponseEntity("Produto removido por id com sucesso!",HttpStatus.OK);
    }
	
	@ResponseBody
	@GetMapping(value = "/obterProduto/{id}")
	public ResponseEntity<Produto> obterProduto(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		Produto produto= produtoRepository.findById(id).orElse(null);
		
		if(produto == null) {
			
			throw new ExceptionMentoriaJava("Produto com ID: " + id + "não foi encontrado");
			
		}
		
		return new ResponseEntity<Produto>(produto,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/buscarProdutoPorNome/{nome}")
	public ResponseEntity<List<Produto>> buscarProdutoPorNome(@PathVariable("nome") String nome) { 
		
		List<Produto> produtos = produtoRepository.buscarNomeProduto(nome.toUpperCase());
		
		return new ResponseEntity<List<Produto>>(produtos,HttpStatus.OK);
	}
	

}
