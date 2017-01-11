package com.sistema.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sistema.model.Produto;
import com.sistema.repositoty.Produtos;
import com.sistema.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {
	//@Inject
	private Produtos produtos;
	
	public ProdutoConverter() {
		produtos =  CDIServiceLocator.getBean(Produtos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent comp, String value) {
		Produto retorno = null;
		if(value == null || value.isEmpty()){
			return null;
		}
			Long id = Long.parseLong(value);
			retorno = produtos.porId(id);
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent comp, Object value) {
		if(value != null){
			Produto produto = (Produto) value;
			return produto.getId() == null ? null : produto.getId().toString();
		}
		
 		return "";
	}

}
