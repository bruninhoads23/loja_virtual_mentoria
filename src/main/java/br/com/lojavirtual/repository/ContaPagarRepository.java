package br.com.lojavirtual.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.ContaPagar;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
	
	@Query("select a from ContaPagar a where upper(trim(a.descricao)) like %?1%")
	List<ContaPagar> buscarContaPorDesc(String descricao);
	
	
	@Query("select a from ContaPagar a where a.pessoa.id = ?1")
	List<ContaPagar> buscarContaPorPessoa(Long id);
	
	
	@Query("select a from ContaPagar a where a.pessoa_fornecedor.id = ?1")
	List<ContaPagar> buscarContaPorFornecedor(Long id_fornecedor);
	
	@Query("select a from ContaPagar a where a.empresa.id = ?1")
	List<ContaPagar> buscarContaPorEmpresa(Long idEmpresa);
	

}
