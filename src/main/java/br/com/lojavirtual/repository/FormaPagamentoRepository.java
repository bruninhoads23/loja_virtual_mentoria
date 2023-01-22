package br.com.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lojavirtual.model.FormaPagamento;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

	@Query(value = "select f from FormaPagamento f where f.empresa.id = ?1")
	List<FormaPagamento> findAll(Long idEmpresa);

}
