package br.com.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.PessoaFisica;


@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>  {

	@Query(value ="select pf from PessoaFisica pf where upper(trim(pf.nome)) like %?1%")
	public List<PessoaFisica> pesquisaPorNome(String nome);
	
	@Query(value ="select pf from PessoaFisica pf where pf.cpf =?1")
	public List<PessoaFisica> pesquisaPorCpfPF(String cpf);
}
