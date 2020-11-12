package si.fri.prpo.skupina57.storitve.zrna;

import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;
import si.fri.prpo.skupina57.storitve.dtos.GovorilnaUraDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Logger;

public class UpravljanjeGovorilnihUrZrno {

    private static final Logger log = Logger.getLogger(StudentiZrno.class.getName());

    @Inject
    StudentiZrno studentiZrno;

    @Inject
    ProfesorjiZrno profesorjiZrno;

    @Inject
    GovorilneUreZrno govorilneUreZrno;

    @PostConstruct
    private void init(){
        log.info("Inicializiranje zrna");
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna");
    }

    @Transactional
    public GovorilnaUra dodajGovorilneUre(GovorilnaUraDto govorilnaUraDto){
        return null;
    }
}
