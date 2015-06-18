/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backing;

import ejb.StudentsFacade;
import ejb.UsersFacade;
import entities.Students;
import entities.Users;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static util.Messages.addFlashMessage;

/**
 *
 * @author omosaziegbe
 */
@Named(value = "studentEdit")
@SessionScoped
public class StudentEdit implements Serializable {

    /**
     * Creates a new instance of StudentEdit
     */
    public StudentEdit() {
    }

    private Students student;
    private Part file;
    String imageFileName;

    @EJB
    private StudentsFacade studentsFacade;

    public void preRenderView() {
        if (student == null) {
            student = new Students();
        }
    }

    public String saveUser() {
        upload();
        if (student.getId() != null) {
            student.setImageurl(imageFileName);
            studentsFacade.edit(student);
            //studentDAO.update(student);
        } else {
            student.setImageurl(imageFileName);
            studentsFacade.create(student);
            //studentDAO.save(student);
        }

        addFlashMessage("Student " + student.getName() + " saved");
        return "/index.xhtml?faces-redirect=true";
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = student.getImageurl();
    }

    public void upload() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        String path = externalContext.getRealPath("/");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String filename = getFilename(file);
            File files = new File("/Users/omosaziegbe/Downloads/img/" + filename);
            bis = new BufferedInputStream(file.getInputStream());
            FileOutputStream fos = new FileOutputStream(files);
            bos = new BufferedOutputStream(fos);
            int x;
            while ((x = bis.read()) != -1) {
                bos.write(x);
            }
        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(bos);
            close(bis);
        }
        imageFileName = getFilename(file);
        System.out.println("This is the file name " + " " + imageFileName + " Name is" + student.getName());
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   private Users user;
    @EJB
    private UsersFacade usersFacade;
    public Users getUsers(){
        if (user == null){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String name = context.getUserPrincipal().getName();
            user = usersFacade.findByUsername(name);
        }
    return user;
    }

    public boolean isUserAdmin() {
        return getRequest().isUserInRole("admin");
    }

    public String logOut() {
        getRequest().getSession().invalidate();
        return "/index.xhtml?faces-redirect=true";
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
}
