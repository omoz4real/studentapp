/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backing;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author omosaziegbe
 */
@Named
@RequestScoped
public class ReportGenerator {

    public void generateReport(ActionEvent actionEvent) throws ClassNotFoundException, SQLException, IOException,
            JRException {
        Connection connection;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/reports/student.jasper");
        //String report = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/customer.jasper");
        ServletOutputStream servletOutputStream = response.getOutputStream();

        HashMap parameterMap = new HashMap();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String name = params.get("name");
        String phone = params.get("phone");
        String email = params.get("email");
        String course = params.get("course");
        String imageUrl = params.get("imageUrl");
        parameterMap.put("name", name);
        parameterMap.put("phone", phone);
        parameterMap.put("email", email);
        parameterMap.put("course", course);
        parameterMap.put("imageUrl", imageUrl);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        response.setContentType("application/octet");
//        response.setHeader("Content-Disposition", "inline;filename=\"" + "student.pdf" + "\"");
        response.setHeader("Content-disposition", "attachment; filename=\"student.pdf");
        response.setContentType("application/pdf");
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}
