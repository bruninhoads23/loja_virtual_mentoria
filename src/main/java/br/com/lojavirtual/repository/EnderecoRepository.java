package br.com.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lojavirtual.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>  {

}
