package si.fri.prpo.skupina57.storitve.zrna;

import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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

@RequestScoped
public class GovorilneUreZrno {

    private int id;
    private static final Logger log = Logger.getLogger(GovorilneUreZrno.class.getName());

    @PersistenceContext(unitName = "govorilne_ure-jpa")
    private EntityManager em;


    @PostConstruct
    public void govorilneUreZrnoInit(){
        id = (int)(Math.random() * 10000) + 1;
        log.info("Govorilne ure zrno ustvarjeno "+id+".\n");
    }

    @PreDestroy
    public void govorilneUreZrnoDestroy(){
        log.info("Govorilne ure zrno uniceno "+id+".\n");
    }

    public GovorilnaUra pridobiGovorilnoUro(int govorilnaUraId){
        GovorilnaUra g = em.find(GovorilnaUra.class, govorilnaUraId);

        return g;
    }

    @Transactional
    public GovorilnaUra dodajGovorilnoUro(GovorilnaUra govorilnaUra){
        if(govorilnaUra != null){
            em.persist(govorilnaUra);
        }
        return govorilnaUra;
    }

    @Transactional
    public GovorilnaUra posodobiGovorilnoUro(int govorilnaUraId, GovorilnaUra govorilnaUra){
        GovorilnaUra g = em.find(GovorilnaUra.class, govorilnaUraId);
        govorilnaUra.setId(govorilnaUraId);

        em.merge(govorilnaUra);

        return govorilnaUra;
    }

    @Transactional
    public boolean odstraniGovorilnoUro(int govorilnaUraId){
        GovorilnaUra govorilnaUra = em.find(GovorilnaUra.class, govorilnaUraId);

        if(govorilnaUra != null){
            em.remove(govorilnaUra);
            return true;
        }
        return false;
    }

    public List<GovorilnaUra> getGovorilneUre() {

        Query q = em.createNamedQuery("GovorilnaUra.getAll");
        List<GovorilnaUra> govorilnaUra = (List<GovorilnaUra>)(q.getResultList());
        return govorilnaUra;
    }

    public List<GovorilnaUra> getGovorilneUreCriteria(){
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

        CriteriaQuery<GovorilnaUra> criteriaQuery = criteriaBuilder.createQuery(GovorilnaUra.class);

        Root<GovorilnaUra> root = criteriaQuery.from(GovorilnaUra.class);

        criteriaQuery.select(root);

        TypedQuery<GovorilnaUra> typedQuery = em.createQuery(criteriaQuery);

        List<GovorilnaUra> govorilnaUra = typedQuery.getResultList();

        return govorilnaUra;
    }

}