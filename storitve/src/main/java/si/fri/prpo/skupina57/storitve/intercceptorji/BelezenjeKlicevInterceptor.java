package si.fri.prpo.skupina57.storitve.intercceptorji;

import si.fri.prpo.skupina57.storitve.anotacije.BeleziKliceZrno;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Interceptor
@BeleziKliceZrno
public class BelezenjeKlicevInterceptor {

    // implementacija
    Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());
    Map<String, Integer> map = new HashMap<String, Integer>();

    @AroundInvoke
    public Object beleziKlice(InvocationContext context) throws Exception {
        String ime = context.getMethod().getName();

        if(map.containsKey(ime)){

            map.put(ime, map.get(ime) + 1);
        }
        else{
            map.put(ime, 1);
        }
        log.info("Metoda: "+ime+" št klicev: "+ map.get(ime));

        return context.proceed();
    }

}