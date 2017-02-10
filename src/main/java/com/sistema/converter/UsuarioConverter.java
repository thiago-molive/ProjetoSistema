package com.sistema.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sistema.model.Usuario;
import com.sistema.repositoty.Usuarios;
import com.sistema.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {
	//@Inject
	private Usuarios usuarios;
	
	public UsuarioConverter() {
		usuarios =  CDIServiceLocator.getBean(Usuarios.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent comp, String value) {
		Usuario retorno = null;
		if(value == null || value.isEmpty()){
			return null;
		}
			Long id = Long.parseLong(value);
			retorno = usuarios.porId(id);
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent comp, Object value) {
		if(value != null){
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}
		
 		return "";
	}

}
