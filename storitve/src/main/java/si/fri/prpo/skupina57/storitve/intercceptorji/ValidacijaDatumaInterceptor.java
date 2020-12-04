package si.fri.prpo.skupina57.storitve.intercceptorji;

import si.fri.prpo.skupina57.storitve.anotacije.ValidacijaDatuma;
import si.fri.prpo.skupina57.storitve.dtos.GovorilnaUraDto;
import si.fri.prpo.skupina57.storitve.izjeme.DatumPreteklostIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.NamedQuery;
import java.util.Date;
import java.util.logging.Logger;

@Interceptor
@ValidacijaDatuma
public class ValidacijaDatumaInterceptor {

    Logger log = Logger.getLogger(ValidacijaDatumaInterceptor.class.getName());

    @AroundInvoke
    public Object validirajDatum(InvocationContext context) throws Exception{

        log.info("Pognan validator datuma");

        log.info(context.getParameters()[0].toString());
        log.info(context.getParameters()[1].toString());


        if(context.getParameters().length == 1 && context.getParameters()[0] instanceof GovorilnaUraDto){
            GovorilnaUraDto govorilnaUraDto = (GovorilnaUraDto) context.getParameters()[0];

            log.info("Pognan validator datuma44");

            if(govorilnaUraDto.getDatum() != null){
                Date datum = govorilnaUraDto.getDatum();
                Date dateNow = new Date();
                if(datum.compareTo(dateNow) < 0){
                    String message = "Datum govorilne ure ni ustrezen";
                    log.severe(message);
                    throw new DatumPreteklostIzjema(message);
                }

            }
        }
        return context.proceed();
    }
}
