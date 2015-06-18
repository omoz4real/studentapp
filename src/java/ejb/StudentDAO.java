/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Students;
import entities.Users;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author omosaziegbe
 */
@Stateless
public class StudentDAO {

    @PersistenceContext(unitName = "studentappPU")
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private EntityManager entityManager;

    public void save(Students student) {
        entityManager.persist(student);
    }

    public void update(Students student) {
        entityManager.merge(student);
    }

    public Students updates(Students student) {
        return entityManager.merge(student);
    }

    public void delete(Students student) {
        student = entityManager.find(Students.class, student.getId());
        entityManager.remove(student);
    }

    public Students getByID(int studentId) {
        return entityManager.find(Students.class, studentId);
    }

    public List<Students> getAll() {
        return entityManager.createNamedQuery("Students.findAll", Students.class).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    protected Users findOneResult(String namedQuery, Map<String, Object> parameters) {
        Users result = null;
        try {
            Query query = entityManager.createNamedQuery(namedQuery);
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
            result = (Users) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
 
    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
    public Users findByUsername(String name) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        return findOneResult(Users.FIND_BY_EMAIL, parameters);
    }

}
