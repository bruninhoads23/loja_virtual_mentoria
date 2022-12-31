package br.com.lojavirtual.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.NotaFiscalCompra;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long>{
	
	@Query("select n from NotaFiscalCompra n where upper(trim(n.descricaoObs)) like %?1%")
	List<NotaFiscalCompra> buscarNotaPorDesc(String desc);
	
	@Query("select n from NotaFiscalCompra n where n.pessoa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorPessoa(Long idPessoa);
	
	
	@Query("select n from NotaFiscalCompra n where n.conta.id = ?1")
	List<NotaFiscalCompra> buscarNotaContapagar(Long idContaPagar);
	
	@Query("select n from NotaFiscalCompra n where n.empresa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorEmpresa(Long idEmpresa);
	
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value="delete from nota_item_produto where nota_fiscal_compra_id = ?1")
	void deleteItemNotaFiscalCompra(Long idNotaFiscalCompra);
	
	
	//@Query(nativeQuery = true, value = "select count(1) > 0 from nota_fiscal_compra where upper(descricao_obs) like %?1% ")
	//boolean existeNotaComDescricao(String desc);
	

}
