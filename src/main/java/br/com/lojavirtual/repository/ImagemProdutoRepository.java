package br.com.lojavirtual.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.ImagemProduto;

@Repository
@Transactional
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {
	
	@Query("select a from ImagemProduto a where a.produto.id = ?1")
	List<ImagemProduto> buscaImagemProduto(Long idProduto);
	
	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "delete from imagem_produto where produto_id = ?1")
	void deleteImagem(Long idProduto);


}
