/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author omosaziegbe
 */
public class Messages {


	public static void addFlashMessage(String message) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

}
