package si.fri.prpo.skupina57.api.v1.viri;


import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;
import si.fri.prpo.skupina57.katalog.entitete.Student;
import si.fri.prpo.skupina57.storitve.dtos.PrijavaOdjavaDto;
import si.fri.prpo.skupina57.storitve.zrna.StudentiZrno;
import si.fri.prpo.skupina57.storitve.zrna.UpravljanjeGovorilnihUrZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("studenti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentiVir {

    @Inject
    private StudentiZrno studentiZrno;


    @Inject
    private UpravljanjeGovorilnihUrZrno upravljanjeGovorilnihUrZrno;

    @GET
    public Response pridobiStudente(){
        List<Student> studenti = studentiZrno.getStudenti();

        return Response.ok(studenti).header("X-Total-Count", studenti.size()).build();
    }

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") Integer id){
        Student student = studentiZrno.pridobiStudenta(id);

        if(student != null){
            return Response.ok(student).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response dodajStudenta(Student student){
        return Response.status(Response.Status.CREATED).entity(studentiZrno.dodajStudenta( student)).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiStudenta(@PathParam("id") Integer id, Student student){
        return Response.status(Response.Status.OK).entity(studentiZrno.posodobiStudenta(id, student)).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniStudenta(@PathParam("id") Integer id){
        if(studentiZrno.odstraniStudenta(id)){
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("{id}/prijava-na-termin")
    public Response prijavaNaTermin(@PathParam("id") Integer id, PrijavaOdjavaDto prijavaOdjavaDto){
        GovorilnaUra govorilnaUra = upravljanjeGovorilnihUrZrno.prijavaNaTermin(prijavaOdjavaDto);

        if(govorilnaUra == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.CREATED).entity(govorilnaUra).build();
    }

    @POST
    @Path("{id}/odjava-od-termina")
    public Response odjavaOdTermina(@PathParam("id") Integer id, PrijavaOdjavaDto prijavaOdjavaDto){
        if(upravljanjeGovorilnihUrZrno.odjavaOdTermina(prijavaOdjavaDto)){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
