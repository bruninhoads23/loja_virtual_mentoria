package br.com.lojavirtual.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.AvaliacaoProduto;
import br.com.lojavirtual.model.dto.AvaliacaoProdutoDTO;

@Repository
@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {
	
	
	@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1")
	public List<AvaliacaoProduto> avaliacaoProduto(Long idProduto);
	
	@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1 and a.pessoa.id = ?2")
	public List<AvaliacaoProduto> avaliacaoProdutoPessoa(Long idProduto, Long idPessoa);
	
	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value= "select * from avaliacao_produto a where a.pessoa_id = ?1")
	public List<AvaliacaoProduto> avaliacaoPessoa(Long idPessoa);
	
	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "delete from avaliacao_produto where id = ?1")
	void deleteAvaliacaoPorId(Long idAvaliacao);
	
	

}
