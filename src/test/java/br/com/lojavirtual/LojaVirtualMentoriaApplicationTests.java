package br.com.lojavirtual;

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
	

	@Test
	public void testCadastrarAcesso() {
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN2");
	    acessoController.salvarAcesso(acesso);
	}

}
