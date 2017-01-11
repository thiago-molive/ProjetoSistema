package com.sistema.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sistema.model.Categoria;
import com.sistema.repositoty.Categorias;
import com.sistema.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {
	//@Inject
	private Categorias categorias;
	
	public CategoriaConverter() {
		categorias =  CDIServiceLocator.getBean(Categorias.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent comp, String value) {
		Categoria retorno = null;
		if(value == null || value.isEmpty()){
			return null;
		}
			Long id = Long.parseLong(value);
			retorno = categorias.porId(id);
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent comp, Object value) {
		if(value != null){
			return ((Categoria) value).getId().toString(); 
		}
		
 		return "";
	}

}
