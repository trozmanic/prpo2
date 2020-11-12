package si.fri.prpo.skupina57.storitve.zrna;

import si.fri.prpo.skupina57.katalog.entitete.Profesor;

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
public class ProfesorjiZrno {

    private static final Logger log = Logger.getLogger(ProfesorjiZrno.class.getName());

    @PersistenceContext(unitName = "govorilne_ure-jpa")
    private EntityManager em;


    @PostConstruct
    public void profesorjiZrnoInit(){
        log.info("Profesorji zrno ustvarjeno.\n");
    }

    @PreDestroy
    public void profesorjiZrnoDestroy(){
        log.info("Profesorji zrno uniceno.\n");
    }

    public Profesor pridobiProfesorja(int profesorId){
        Profesor p = em.find(Profesor.class, profesorId);

        return p;
    }

    @Transactional
    public Profesor dodajProfesorja(Profesor profesor){
        if(profesor != null){
            em.persist(profesor);
        }
        return profesor;
    }

    @Transactional
    public Profesor posodobiProfesorja(int profesorId, Profesor profesor){
        Profesor p = em.find(Profesor.class, profesorId);
        profesor.setId(profesorId);

        em.merge(profesor);

        return profesor;
    }

    @Transactional
    public boolean odstraniProfesorja(int profesorId){
        Profesor profesor = em.find(Profesor.class, profesorId);

        if(profesor != null){
            em.remove(profesor);
            return true;
        }
        return false;
    }

    public List<Profesor> getProfesorje() {

        Query q = em.createNamedQuery("Profesor.getAll");
        List<Profesor> profesorji = (List<Profesor>)(q.getResultList());
        return profesorji;
    }

    public List<Profesor> getProfesorjiCriteria(){
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

        CriteriaQuery<Profesor> criteriaQuery = criteriaBuilder.createQuery(Profesor.class);

        Root<Profesor> root = criteriaQuery.from(Profesor.class);

        criteriaQuery.select(root);

        TypedQuery<Profesor> typedQuery = em.createQuery(criteriaQuery);

        List<Profesor> profesorji = typedQuery.getResultList();

        return profesorji;
    }

}