package br.com.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/* 	@EntityScan(baseyPackages  = "br.com.lojavirtual.model")
 *  utiliza esta anotação quando alguns computadores não fazem de forma automática
 * 
 * */
@SpringBootApplication
@EntityScan(basePackages  = "br.com.lojavirtual.model")
@ComponentScan(basePackages = {"br.*"})
@EnableJpaRepositories(basePackages = {"br.com.lojavirtual.repository"})
@EnableTransactionManagement()//gerenciar as transações do banco de dados
public class LojaVirtualMentoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualMentoriaApplication.class, args);
	}

}
