package si.fri.prpo.skupina57.storitve;

import org.eclipse.persistence.logging.SessionLog;
import si.fri.prpo.skupina57.katalog.entitete.Student;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class StudentiZrno {

    private static final Logger log = Logger.getLogger(StudentiZrno.class.getName());

    @PersistenceContext(unitName = "govorilne_ure-jpa")
    private EntityManager em;


    @PostConstruct
    public void studentiZrnoInit(){
        log.info("Studenti zrno ustvarjeno.\n");
    }

    @PreDestroy
    public void studentiZrnoDestroy(){
        log.info("Studenti zrno uniceno.\n");
    }

    public Student pridobiStudenta(int studentId){
        Student s = em.find(Student.class, studentId);

        return s;
    }

    @Transactional
    public Student dodajStudenta(Student student){
        if(student != null){
            em.persist(student);
        }
        return student;
    }

    @Transactional
    public Student posodobiStudenta(int studentId, Student student){
        Student s = em.find(Student.class, studentId);
        student.setId(studentId);

        em.merge(student);

        return student;
    }

    @Transactional
    public boolean odstraniStudenta(int studentId){
        Student student = em.find(Student.class, studentId);

        if(student != null){
            em.remove(student);
            return true;
        }
        return false;
    }

    public List<Student> getStudenti() {

        Query q = em.createNamedQuery("Student.getAll");
        List<Student> studenti = (List<Student>)(q.getResultList());
        return studenti;
    }

    public List<Student> getStudentiCriteria(){
        /**
         *
         * https://docs.oracle.com/cd/E19798-01/821-1841/gjitv/index.html
         *
         Use an EntityManager instance to create a CriteriaBuilder object.

         Create a query object by creating an instance of the CriteriaQuery interface. This query object's attributes will be modified with the details of the query.

         Set the query root by calling the from method on the CriteriaQuery object.

         Specify what the type of the query result will be by calling the select method of the CriteriaQuery object.

         Prepare the query for execution by creating a TypedQuery<T> instance, specifying the type of the query result.

         Execute the query by calling the getResultList method on the TypedQuery<T> object. Because this query returns a collection of entities, the result is stored in a List.
         */
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);

        Root<Student> root = criteriaQuery.from(Student.class);

        criteriaQuery.select(root);

        TypedQuery<Student> typedQuery = em.createQuery(criteriaQuery);

        List<Student> studenti = typedQuery.getResultList();

        return studenti;
    }

}