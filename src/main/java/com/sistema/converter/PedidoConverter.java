package com.sistema.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sistema.model.Pedido;
import com.sistema.repositoty.Pedidos;
import com.sistema.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {
	//@Inject
	private Pedidos pedidos;
	
	public PedidoConverter() {
		pedidos =  CDIServiceLocator.getBean(Pedidos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent comp, String value) {
		Pedido retorno = null;
		if(value == null || value.isEmpty()){
			return null;
		}
			Long id = Long.parseLong(value);
			retorno = pedidos.porId(id);
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent comp, Object value) {
		if(value != null){
			Pedido pedido = (Pedido) value;
			return pedido.getId() == null ? null : pedido.getId().toString();
		}
		
 		return "";
	}

}
