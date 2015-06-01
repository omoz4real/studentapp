/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Students;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
