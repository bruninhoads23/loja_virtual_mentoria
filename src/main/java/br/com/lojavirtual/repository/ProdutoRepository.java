package br.com.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query(nativeQuery = true, value= "select count(1) > 0 from Produto where upper(trim(nome)) = upper(trim(?1))")
	public boolean existeProduto (String nomeProduto);
	
	@Query("select 	a from Produto a where upper(trim(a.nome)) like %?1%")
	List<Produto> buscarNomeProduto (String nome);
	
	@Query("select 	a from Produto a where upper(trim(a.nome)) like %?1%  and empresa.id =?2")
	List<Produto> buscarNomeProduto (String nome, Long idempresa);
	

}
