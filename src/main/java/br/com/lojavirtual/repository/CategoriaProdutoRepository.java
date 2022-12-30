package br.com.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.model.CategoriaProduto;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
	
	@Query(nativeQuery = true, value= "select count(1) > 0 from categoria_produto where upper(trim(nome_desc)) = upper(trim(?1))")
	public boolean existeCategoria (String nomeCategoria);
	
	@Query("select 	a from CategoriaProduto a where upper(trim(a.nomeDesc)) like %?1%")
	List<CategoriaProduto> buscarDescCategoria (String nomedesc);
	

}
