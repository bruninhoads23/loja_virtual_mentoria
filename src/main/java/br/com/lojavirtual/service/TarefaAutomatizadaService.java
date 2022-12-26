package br.com.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioRepository;

@Component
@Service
public class TarefaAutomatizadaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	//initialDelay = 2000 = começa depois de 2 segundos que subir pro servidor
	@Scheduled(initialDelay = 2000, fixedDelay = 86400000) /*86400000 equivale para Rodar a cada 24 horas*/
	//@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") /*0 segundo, 0 minuto, Vai rodar todo dia as 11 horas da manhã horario de Sao paulo*/
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		//traz a lista de usuarios que estão com a senha vencida
		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
		for (Usuario usuario : usuarios) {
			
			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
			msg.append("Está na hora de trocar sua senha, já passou 90 dias de validade.").append("<br/>");
			msg.append("Troque sua senha a loja virtual do Bruninho - Desenvolvedor");
			
			serviceSendEmail.enviarEmailHtml("Troca de senha", msg.toString(), usuario.getLogin());
			//a cada 3 segundos ele envia email e libera recursos. ele dorme 3 segundos antes de enviar o próximo email para o proximo usuario
			Thread.sleep(3000);
			
		}
		
		
	}

}
