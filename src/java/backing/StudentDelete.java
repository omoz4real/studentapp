/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backing;


import ejb.StudentDAO;
import entities.Students;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author omosaziegbe
 */
@Named(value = "studentDelete")
@ViewScoped
public class StudentDelete {

    /**
     * Creates a new instance of StudentDelete
     */
    public StudentDelete() {
    }
    
    private Students students;
	
	@EJB
	private StudentDAO studentDAO;
	
	public String delete() {
		studentDAO.delete(students);
		return "index?faces-redirect=true";
	}
	
	public String cancel() {
		return "index?faces-redirect=true";
	}

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }
	
    
}
