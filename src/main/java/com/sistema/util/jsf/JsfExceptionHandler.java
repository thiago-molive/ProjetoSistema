package com.sistema.util.jsf;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistema.service.NegocioException;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {
	
	private static Logger log = LogManager.getLogger(JsfExceptionHandler.class);
	private ExceptionHandler wrapped;

	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		
		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext exceptionContext = (ExceptionQueuedEventContext) event.getSource();
			Throwable exception = exceptionContext.getException();
			NegocioException negocio = getNegocioException(exception);
			boolean handled = false;
			try {
				if (exception instanceof ViewExpiredException) {
					handled = true;
					redireciona("/");
				}else if(negocio != null){
					handled = true;
					FacesUtil.addMessage(negocio.getMessage(), FacesMessage.SEVERITY_ERROR);
				}
				else{
					handled = true;
					log.error("Error de sistema: " + exception.getMessage(), exception);
					redireciona("/erro.xhtml");
				}
			} finally {
				if(handled)
				events.remove();
			}
		}
		getWrapped().handle();
	}

	private NegocioException getNegocioException(Throwable exception) {
		if(exception instanceof NegocioException){
			return (NegocioException) exception;
		}else if(exception.getCause()!=null){
			return getNegocioException(exception.getCause());
		}
		
		return null;
	}

	private void redireciona(String pagina) {
		try {
			FacesContext faces = FacesContext.getCurrentInstance();
			ExternalContext external = faces.getExternalContext();

			String contextPath = external.getRequestContextPath();
			external.redirect(contextPath+pagina);
			faces.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}
}
