package br.com.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

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
import br.com.lojavirtual.model.ContaPagar;
import br.com.lojavirtual.model.NotaFiscalCompra;
import br.com.lojavirtual.repository.NotaFiscalCompraRepository;

@RestController
public class NotaFiscalCompraController {

	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@ResponseBody
	@GetMapping(value = "/buscarNotaFiscaisPorDesc/{desc}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarNotaPorDesc(@PathVariable("desc") String desc) { 
		
		List<NotaFiscalCompra> notasFiscais = notaFiscalCompraRepository.buscarNotaPorDesc(desc.toUpperCase().trim());
		
		return new ResponseEntity<List<NotaFiscalCompra>>(notasFiscais,HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "/obterNotaFiscalCompra/{id}")
	public ResponseEntity<NotaFiscalCompra> obterNotaFiscalCompra(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		NotaFiscalCompra notaFiscal= notaFiscalCompraRepository.findById(id).orElse(null);
		
		if(notaFiscal == null) {
			
			throw new ExceptionMentoriaJava("A Nota Fiscal de compra com ID: " + id + "não foi encontrado");
			
		}
		
		return new ResponseEntity<NotaFiscalCompra>(notaFiscal,HttpStatus.OK);
	}
	
	//DELETAR PRODUTO POR ID
	@ResponseBody 
	@DeleteMapping(value ="/deleteNotaFiscalCompraPorId/{id}")
	public ResponseEntity<?> deleteNotaFiscalCompraPorId(@PathVariable("id") Long id) {
		
		notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id);//delete os filhos
		notaFiscalCompraRepository.deleteById(id); //deleta o pai
		return new ResponseEntity("Nota Fiscal de Compra removida por id com sucesso!",HttpStatus.OK);
    }
	
	//SALVAR Produto
		@ResponseBody /*para poder dar um retorno para API*/
		@PostMapping(value ="/salvarNotaFiscalCompra")/*mapeando a url para receber JSON*/
		public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra notaFiscalCompra) throws ExceptionMentoriaJava {/*receber JSON e converte para Objeto*/
			
			//verifica se ja existe a decrição da nota com a mesma descrição que o usuario quer cadastrar
			
			if(notaFiscalCompra.getId()==null) {	
				if(notaFiscalCompra.getDescricaoObs() !=null) {
					List<NotaFiscalCompra> notasFiscais = notaFiscalCompraRepository.buscarNotaPorDesc(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());
					if(!notasFiscais.isEmpty()) {
						 throw new ExceptionMentoriaJava("Já existe Nota Fiscal de Compra com a mesma descrição");
					}
				}
			}
			
			//a pessoa da nota é nula ou  nao foi informada 
			if(notaFiscalCompra.getPessoa()==null || notaFiscalCompra.getPessoa().getId() <=0) {
				
				 throw new ExceptionMentoriaJava("A pessoa Juridica da nota fiscal deve ser informada");
			}
			
			//a Empresa da nota é nula ou nao foi informada 
			if(notaFiscalCompra.getEmpresa()==null || notaFiscalCompra.getEmpresa().getId() <=0) {
				
				 throw new ExceptionMentoriaJava("A Empresa da nota fiscal deve ser informada");
			}
			
			//a Conta a pagar da nota é nula ou nao foi informada 
			if(notaFiscalCompra.getContaPagar()==null || notaFiscalCompra.getContaPagar().getId() <=0) {
				
				 throw new ExceptionMentoriaJava("A Conta a Pagar da nota fiscal deve ser informada");
			}

			NotaFiscalCompra notaFiscalCompraSalva = notaFiscalCompraRepository.save(notaFiscalCompra);
			return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraSalva, HttpStatus.OK);
		}
		
	
	
	
	
	
}
