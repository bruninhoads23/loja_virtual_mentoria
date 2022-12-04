package br.com.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


/* 	@EntityScan(baseyPackages  = "br.com.lojavirtual.model")
 *  utiliza esta anotação quando alguns computadores não fazem de forma automática
 * 
 * */
@SpringBootApplication
@EntityScan(basePackages  = "br.com.lojavirtual.model")
public class LojaVirtualMentoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualMentoriaApplication.class, args);
	}

}
