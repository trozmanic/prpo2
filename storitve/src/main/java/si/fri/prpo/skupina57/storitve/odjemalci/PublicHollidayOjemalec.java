package si.fri.prpo.skupina57.storitve.odjemalci;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina57.storitve.dtos.PublicHolidayDto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PublicHollidayOjemalec {
    private Logger log = Logger.getLogger(PublicHollidayOjemalec.class.getName());

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + PublicHollidayOjemalec.class.getSimpleName());
        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance()
                .get("integrations.zunanji-vir.base-url:")
                .orElse("date.nager.at/Api/v2/PublicHolidays");
    }

    public boolean aliJeJavniPraznik(Date date) {
        int leto = date.getYear() + 1900;

        List<PublicHolidayDto> response = null;
        try{
            response = httpClient
                    .target("https://" + baseUrl + "/" + leto + "/SI")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<PublicHolidayDto>>() {
                    });
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        log.info("Nas Datum: " + date.getMonth() + " " + date.getDate() + ".");

        boolean jePraznik = false;
        DateFormat formatDatuma = new SimpleDateFormat("yyyy-MM-dd");
        Date temp;
        for (int i = 0; i < response.size(); i++) {
            try {
                temp = formatDatuma.parse(response.get(i).getDate());
                log.info("Praznik Datum: " + temp.getMonth() + " " + temp.getDate() + ".");
                if (temp.getMonth() == date.getMonth() && temp.getDate() == date.getDate()) {
                    jePraznik = true;
                    log.info("Napaka izbrali ste praznik: " + response.get(i).getDate().toString() + " " + response.get(i).getLocalName() + ".");
                    return true;
                }
            } catch (ParseException e) {
                log.info("Napaka pri parsanju datuma: " + response.get(i).getDate() + ".");
            }
        }
        return false;
    }
}
