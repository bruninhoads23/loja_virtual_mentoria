package br.com.lojavirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lojavirtual.controller.AcessoController;
import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.repository.AcessoRepository;
import br.com.lojavirtual.service.AcessoService;



//@Profile(dev, produ ou test) quando temos application properties especifico... posso ter application-test-properties e colocar @Profile("Test") 
@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {

	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private WebApplicationContext wac;

	
	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_CADASTRAR_ACESSO");

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/salvarAcesso")//passa os dados do endpoint
						.content(objectMapper.writeValueAsString(acesso))//passa o conteúdo, ou seja o objeto
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)); // tipo de conteúdo que é o JSON

		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());

		/* Conveter o retorno da API para um obejto de acesso */

		Acesso objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
				Acesso.class);

		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao()); // O QUELOR EU MANDEI SALVAR E O QUE REALMENTE FOI SALVO TEM QUE VOLTAR O MESMO VA

	}
	
	@Test
	public void testRestApiDeleteAcesso() throws JsonProcessingException, Exception {
		
	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	   
	    Acesso acesso = new Acesso();
	    
	    acesso.setDescricao("ROLE_DELETE_ACESSO");
	    
	    acesso = acessoRepository.save(acesso);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/deleteAcesso")
	    						 .content(objectMapper.writeValueAsString(acesso))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString()); // TESTA  MENSAGEM DE RETORNO
	    System.out.println("Status de retorno: " + retornoApi.andReturn().getResponse().getStatus()); // TESTA O STATUS DA RESPOSTA... EX: 200, 401, 404...
	    
	   assertEquals("Acesso removido", retornoApi.andReturn().getResponse().getContentAsString());//TESTE DE CORPO DA RESPOSTA
	   assertEquals(200, retornoApi.andReturn().getResponse().getStatus());//TESTE DE STATUS
	    
	    
	}
	
	@Test
	public void testRestApiObterAcessoId() throws JsonProcessingException, Exception {
		
	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	   
	    Acesso acesso = new Acesso();
	    
	    acesso.setDescricao("ROLE_OBTER_ID");
	    
	    acesso = acessoRepository.save(acesso);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.get("/obterAcesso" + acesso.getId())
	    						 .content(objectMapper.writeValueAsString(acesso))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    
	   assertEquals(200, retornoApi.andReturn().getResponse().getStatus());//TESTE DE STATUS
	   Acesso  acessoRetorno =  objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
	    
	   assertEquals(acesso.getDescricao(),acessoRetorno.getDescricao());
	   assertEquals(acesso.getId(), acessoRetorno.getId());
	    
	}
	
	@Test
	public void testRestApiObterAcessoDesc() throws JsonProcessingException, Exception {
		
	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    Acesso acesso = new Acesso();
	    
	    acesso.setDescricao("ROLE_TESTE_OBTER_LIST");
	    
	    acesso = acessoRepository.save(acesso);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
	    						 .content(objectMapper.writeValueAsString(acesso))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

	    List<Acesso> retornoApiList = objectMapper.readValue(retornoApi.andReturn()
	    									.getResponse().getContentAsString(),
	    									 new TypeReference<List<Acesso>> () {});

	    assertEquals(1, retornoApiList.size());
	    
	    assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
	    
	    
	    acessoRepository.deleteById(acesso.getId());
	}
	
	@Test
	public void testRestApiDeletePorIDAcesso() throws JsonProcessingException, Exception {
		
	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    Acesso acesso = new Acesso();
	    acesso.setDescricao("ROLE_DELETE_POR_ID");
	    acesso = acessoRepository.save(acesso);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
	    						 .content(objectMapper.writeValueAsString(acesso))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString()); // TESTA  MENSAGEM DE RETORNO
	    System.out.println("Status de retorno: " + retornoApi.andReturn().getResponse().getStatus()); // TESTA O STATUS DA RESPOSTA... EX: 200, 401, 404...
	    
	    assertEquals("Acesso removido por id", retornoApi.andReturn().getResponse().getContentAsString());//TESTE DE CORPO DA RESPOSTA
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());//TESTE DE STATUS
	    
	    
	}
	
	


	@Test
	public void testCadastrarAcesso() throws ExceptionMentoriaJava {

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_BRUNINHO");

		/* Gravou no banco de dados */

		acesso = acessoController.salvarAcesso(acesso).getBody();

		assertEquals(true, acesso.getId() > 0);

		/* validar os dados salvos de forma correta */
		assertEquals("ROLE_BRUNINHO", acesso.getDescricao());

		/* teste de carregamento */
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

		assertEquals(acesso.getId(), acesso2.getId());

		/* teste de delete */

		acessoRepository.deleteById(acesso2.getId());

		acessoRepository.flush(); /* Roda este SQL de delete no banco de dados */

		// tenta buscar pelo ID que foi apagado. Se não achar ele volta null
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

		assertEquals(true, acesso3 == null);

		/* TESTE DE QUERY */

		acesso = new Acesso();
		acesso.setDescricao("ROLE_BRUNO");

		acesso = acessoController.salvarAcesso(acesso).getBody();

		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("BRUNO".trim().toUpperCase());

		assertEquals(1, acessos.size());

		acessoRepository.deleteById(acesso.getId());
	}

}
