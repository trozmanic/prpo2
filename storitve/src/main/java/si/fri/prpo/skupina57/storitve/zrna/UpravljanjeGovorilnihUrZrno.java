package si.fri.prpo.skupina57.storitve.zrna;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;
import si.fri.prpo.skupina57.katalog.entitete.Profesor;
import si.fri.prpo.skupina57.katalog.entitete.Student;
import si.fri.prpo.skupina57.storitve.anotacije.BeleziKliceZrno;
import si.fri.prpo.skupina57.storitve.dtos.GovorilnaUraDto;
import si.fri.prpo.skupina57.storitve.dtos.KanalDto;
import si.fri.prpo.skupina57.storitve.dtos.PrijavaOdjavaDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeGovorilnihUrZrno {

    private int id;
    private static final Logger log = Logger.getLogger(StudentiZrno.class.getName());

    private Client client;
    private String baseUrl;

    @Inject
    StudentiZrno studentiZrno;

    @Inject
    ProfesorjiZrno profesorjiZrno;

    @Inject
    GovorilneUreZrno govorilneUreZrno;

    @PostConstruct
    private void init(){
        id = (int)(Math.random() * 10000) + 1;
        log.info("Inicializiranje zrna UpravljanjeGovorilnihUrZrno "+id+".");

        client = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance()
                .get("integrations.komnunikacijski-kanali.base-url:")
                .orElse("http://localhost:8081/v1");
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna "+id+".");
    }
    @BeleziKliceZrno
    @Transactional
    public GovorilnaUra dodajGovorilneUre(GovorilnaUraDto govorilnaUraDto){
        log.info(govorilnaUraDto.toString());

        Profesor profesor = profesorjiZrno.pridobiProfesorja(govorilnaUraDto.getProfesor_id());
        if(govorilnaUraDto.getDatum() == null)
            govorilnaUraDto.setDatum(new Date());
        if (profesor == null){
            log.info("Profesorja ni v bazi");
            return null;
        }
        GovorilnaUra govorilnaUra = new GovorilnaUra();
        govorilnaUra.setProfesor(profesor);
        govorilnaUra.setDatum(govorilnaUraDto.getDatum());
        govorilnaUra.setKanal(govorilnaUraDto.getKanal());
        govorilnaUra.setKapaciteta(govorilnaUraDto.getKapaciteta());
        govorilnaUra.setUra(govorilnaUraDto.getUra());

        dodajKanal(govorilnaUraDto.getKanal());

        profesor.getGovorilneUre().add(govorilnaUra);


        return govorilneUreZrno.dodajGovorilnoUro(govorilnaUra);
    }
    @BeleziKliceZrno
    @Transactional
    public GovorilnaUra prijavaNaTermin(PrijavaOdjavaDto prijavaDto){
        Student student = studentiZrno.pridobiStudenta(prijavaDto.getStudent_id());

        if(student == null){
            log.info("Ni studenta");
            return null;
        }

        GovorilnaUra govorilnaUra = govorilneUreZrno.pridobiGovorilnoUro(prijavaDto.getGovorilna_ura_id());

        if(govorilnaUra == null){
            log.info("Ni govorilne ure");
            return null;
        }

        if(govorilnaUra.getKapaciteta() < 1){
            log.info("Ni prostora");
            return null;
        }
        if(!govorilnaUra.getStudenti().contains(studentiZrno.pridobiStudenta(student.getId()))) {
            govorilnaUra.getStudenti().add(student);
            govorilnaUra.setKapaciteta(govorilnaUra.getKapaciteta() - 1);

            student.getGovorilneUre().add(govorilnaUra);
        }

        return govorilnaUra;
    }
    @BeleziKliceZrno
    @Transactional
    public boolean odjavaOdTermina(PrijavaOdjavaDto odjavaDto){
        Student student = studentiZrno.pridobiStudenta(odjavaDto.getStudent_id());

        if(student == null){
            log.info("Ni studenta");
            return false;
        }

        GovorilnaUra govorilnaUra = govorilneUreZrno.pridobiGovorilnoUro(odjavaDto.getGovorilna_ura_id());

        if(govorilnaUra == null){
            log.info("Ni govorilne ure");
            return false;
        }

        govorilnaUra.getStudenti().remove(student);
        govorilnaUra.setKapaciteta(govorilnaUra.getKapaciteta() + 1);

        student.getGovorilneUre().remove(govorilnaUra);

        return true;

    }

    public void dodajKanal(String naziv){
        try {
            client.target(baseUrl+"/kanali")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(new KanalDto(naziv)));
        }catch (WebApplicationException | ProcessingException e){
            log.info(e.getMessage());
        }
    }
}
