package com.sistema.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.sistema.service.NegocioException;
import com.sistema.validation.CodigoNumero;

@Entity
@Table(name="produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String numero;
	private BigDecimal valorUnitario;
	private Integer quantidadeEstoque;
	private Categoria categoria;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@NotBlank @Size(max=60)
	@Column(nullable=false,length=60)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	@NotBlank @CodigoNumero
	@Column(nullable=false,length=60, unique=true)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String sku) {
		this.numero = sku;
	}
	@NotNull @Min(0) @Max(100000)
	@Column(name="valor_unitario",nullable=false, precision=10, scale=2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	@NotNull @Min(0) @Max(value=9999, message="valor muito alto")
	@Column(name="quantidade_estoque",nullable=false,length=5)
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	@NotNull
	@ManyToOne
	@JoinColumn(name="categoria_id", nullable=false)
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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

	public void baixarEstoque(Integer quantidade) {
		int nova = this.getQuantidadeEstoque() - quantidade;
		
		if(nova < 0){
			throw new NegocioException("Não há " + quantidade + " itens no estoque do produto " + this.getNome());
		}
		
		this.setQuantidadeEstoque(nova);
	}

	public void addEstoque(Integer quantidade) {
		this.setQuantidadeEstoque(this.getQuantidadeEstoque() + quantidade);
	}

}
