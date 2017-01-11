package com.sistema.util.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static boolean IsPostBack(){
		return FacesContext.getCurrentInstance().isPostback();
	}
	public static void addMessage(String msg, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, msg));
	}
}
