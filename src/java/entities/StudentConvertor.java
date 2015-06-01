/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import ejb.StudentDAO;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author omosaziegbe
 */
@Named
@FacesConverter("myConverter")
public class StudentConvertor implements Converter{
@EJB
	private StudentDAO students;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		int id = Integer.parseInt(value);
		
		return students.getByID(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		Integer id = ((Students) value).getId();
		
		return (id != null) ? id.toString() : null;
	}

}
