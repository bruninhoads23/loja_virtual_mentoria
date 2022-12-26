package br.com.lojavirtual;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/* 	@EntityScan(baseyPackages  = "br.com.lojavirtual.model")
 *  utiliza esta anotação quando alguns computadores não fazem de forma automática
 * 
 * */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EntityScan(basePackages  = "br.com.lojavirtual.model")
@ComponentScan(basePackages = {"br.*"})
@EnableJpaRepositories(basePackages = {"br.com.lojavirtual.repository"})
@EnableTransactionManagement()//gerenciar as transações do banco de dados
public class LojaVirtualMentoriaApplication implements AsyncConfigurer{

	public static void main(String[] args) {
		
		//System.out.println(new BCryptPasswordEncoder().encode("123"));		
		SpringApplication.run(LojaVirtualMentoriaApplication.class, args);

	}
	
	@Override
	@Bean
	public Executor getAsyncExecutor() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();		
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assyncrono Thread");
		executor.initialize();

		return executor;
	}

}
