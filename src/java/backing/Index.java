/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backing;

import ejb.StudentDAO;
import entities.Students;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author omosaziegbe
 */
@Named(value = "index")
@RequestScoped
public class Index {

    /**
     * Creates a new instance of Index
     */
    public Index() {
    }

    private List<Students> students;
    private Students student;
    @EJB
    private StudentDAO studentDAO;

    @PostConstruct
    public void init() {
        students = studentDAO.getAll();
    }

    public List<Students> getStudents() {
        return students;
    }

    public void preRenderView() {
        if (student == null) {
            student = new Students();
        }
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

       
//	public Students getDetails()
//    {
//        return student;
//    }
//
//    public String showDetails(Students student)
//    {
//        this.student = student;
//        System.out.println(" This are the Details : " + student.getCourse() + " " + student.getName());
//        return "student_edit.xhtml?faces-redirect=true";
//    }
//
//
//    public String update()
//    {
//        System.out.println("###UPDATE###");
//        student = studentDAO.updates(student);
//        return "index.xhtml?faces-redirect=true";
//    }
//
//    public String delete() {
//		studentDAO.delete(student);
//		return "index?faces-redirect=true";
//	}

}
