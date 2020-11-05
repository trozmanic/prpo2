package si.fri.prpo.skupina57.storitve;

import si.fri.prpo.skupina57.katalog.entitete.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class StudentiZrno {

    @PersistenceContext(unitName = "govorilne_ure-jpa")
    private EntityManager em;

    public List<Student> getStudenti() {

        Query q = em.createNamedQuery("Student.getAll");
        List<Student> studenti = (List<Student>)(q.getResultList());
        return studenti;
    }
}