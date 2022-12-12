package br.com.lojavirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.lojavirtual.controller.AcessoController;
import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.repository.AcessoRepository;
import br.com.lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {

	@Autowired
	private AcessoService acessoService;
	
	
	@Autowired
	private AcessoController acessoController;
	
	
	@Autowired	
	private AcessoRepository acessoRepository;
	
	

	@Test
	public void testCadastrarAcesso() {
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_FUNCIONARIO");
		
		/*Gravou no banco de dados*/
		
	    acesso = acessoController.salvarAcesso(acesso).getBody();
	    
	    assertEquals(true, acesso.getId() > 0);
	    
	    /*validar os dados salvos de forma correta*/
	    assertEquals("ROLE_FUNCIONARIO", acesso.getDescricao());
	    
	    /*teste de carregamento*/
	 Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
	 
	 assertEquals(acesso.getId(), acesso2.getId());
	 
	 
	 /*teste de delete*/
	 
	 acessoRepository.deleteById(acesso2.getId());
	 
	 acessoRepository.flush();  /*Roda este SQL de delete no banco de dados*/
	 
	 //tenta buscar pelo ID que foi apagado. Se não achar ele volta null
	 Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
	 
	 assertEquals(true, acesso3 == null);
	 
	 
	 /*TESTE DE QUERY*/
	    
	 
	acesso = new Acesso();
	acesso.setDescricao("ROLE_ALUNO");
	
	acesso = acessoController.salvarAcesso(acesso).getBody();
	
	List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
	
    assertEquals(1, acessos.size());
    
    acessoRepository.deleteById(acesso.getId());
	}

}
