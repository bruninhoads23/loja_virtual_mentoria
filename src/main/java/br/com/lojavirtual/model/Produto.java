package br.com.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;
	
	@NotNull(message = "O Tipo de Unidade do Produto deve ser informado")
	@Column(nullable = false)
	private String tipoUnidade;
	
	@Size(min = 10, message = "O Nome do Produto deve ter no mínimo 10 Letras")
    @NotNull(message = "O nome do Produto deve ser informado")
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;
	
	@Column(columnDefinition = "text", length = 2000, nullable = false)
	private String descricao;

	/** Nota item nota produto - ASSOCIAR **/

	@NotNull(message = "O Peso do Produto deve ser informado")
	@Column(nullable = false)
	private Double peso; /* 1000.55 G */

	@NotNull(message = "A Lagura do Produto deve ser informada")
	@Column(nullable = false)
	private Double largura;

	@NotNull(message = "A Altura do Produto deve ser informada")
	@Column(nullable = false)
	private Double altura;

	
	@NotNull(message = "A profundidade do Produto deve ser informada")
	@Column(nullable = false)
	private Double profundidade;
	
	@NotNull(message = "O Valor da Venda do Produto deve ser informado")
	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;

	@NotNull(message = "A Quantidade de Estoque do Produto deve ser informada")
	@Column(nullable = false)
	private Integer QtdEstoque = 0;

	private Integer QtdeAlertaEstoque = 0;

	private String linkYoutube;

	private Boolean alertaQtdeEstoque = Boolean.FALSE;

	private Integer qtdeClique = 0;
	
	@NotNull(message = "A empresa responsável deve ser informada")
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "A Categoria do Produto deve ser informada")
	@ManyToOne(targetEntity = CategoriaProduto.class)
	@JoinColumn(name = "categora_produto_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categora_produto_id_fk"))
	private CategoriaProduto categoriaProduto;
	
	@NotNull(message = "A Marca do Produto deve ser informada")
	@ManyToOne(targetEntity = MarcaProduto.class)
	@JoinColumn(name = "marca_produto_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "marca_produto_id_fk"))
	private MarcaProduto marcaProduto;
	
	
	@NotNull(message = "A Nota do Item do Produto deve ser informada")
	@ManyToOne(targetEntity = NotaItemProduto.class)
	@JoinColumn(name = "nota_item_produto_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_item_produto_id_fk"))
	private NotaItemProduto notaItemProduto;
	
	
	public NotaItemProduto getNotaItemProduto() {
		return notaItemProduto;
	}

	public void setNotaItemProduto(NotaItemProduto notaItemProduto) {
		this.notaItemProduto = notaItemProduto;
	}

	public MarcaProduto getMarcaProduto() {
		return marcaProduto;
	}

	public void setMarcaProduto(MarcaProduto marcaProduto) {
		this.marcaProduto = marcaProduto;
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Integer getQtdEstoque() {
		return QtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		QtdEstoque = qtdEstoque;
	}

	public Integer getQtdeAlertaEstoque() {
		return QtdeAlertaEstoque;
	}

	public void setQtdeAlertaEstoque(Integer qtdeAlertaEstoque) {
		QtdeAlertaEstoque = qtdeAlertaEstoque;
	}

	public String getLinkYoutube() {
		return linkYoutube;
	}

	public void setLinkYoutube(String linkYoutube) {
		this.linkYoutube = linkYoutube;
	}

	public Boolean getAlertaQtdeEstoque() {
		return alertaQtdeEstoque;
	}

	public void setAlertaQtdeEstoque(Boolean alertaQtdeEstoque) {
		this.alertaQtdeEstoque = alertaQtdeEstoque;
	}

	public Integer getQtdeClique() {
		return qtdeClique;
	}

	public void setQtdeClique(Integer qtdeClique) {
		this.qtdeClique = qtdeClique;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

