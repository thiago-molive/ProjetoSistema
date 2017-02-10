package com.sistema.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sistema.model.Cliente;
import com.sistema.repositoty.Clientes;
import com.sistema.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {
	//@Inject
	private Clientes clientes;
	
	public ClienteConverter() {
		clientes =  CDIServiceLocator.getBean(Clientes.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent comp, String value) {
		Cliente retorno = null;
		if(value == null || value.isEmpty()){
			return null;
		}
			Long id = Long.parseLong(value);
			retorno = clientes.porId(id);
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent comp, Object value) {
		if(value != null){
			Cliente cliente = (Cliente) value;
			return cliente.getId() == null ? null : cliente.getId().toString();
		}
		
 		return "";
	}

}
