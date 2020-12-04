package si.fri.prpo.skupina57.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;
import si.fri.prpo.skupina57.katalog.entitete.Profesor;
import si.fri.prpo.skupina57.storitve.dtos.GovorilnaUraDto;
import si.fri.prpo.skupina57.storitve.zrna.ProfesorjiZrno;
import si.fri.prpo.skupina57.storitve.zrna.UpravljanjeGovorilnihUrZrno;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("profesorji")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfesorjiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private ProfesorjiZrno profesorjiZrno;

    @Inject
    private UpravljanjeGovorilnihUrZrno upravljanjeGovorilnihUrZrno;

    private Logger log = Logger.getLogger(ProfesorjiVir.class.getName());
//    @GET
//    public Response pridobiProfesorje(){
//        List<Profesor> Profesori = profesorjiZrno.getProfesorje();
//
//        return Response.ok(Profesori).header("X-Total-Count", Profesori.size()).build();
//    }

    @GET
    public Response pridobiProfesorje(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long profesorjiCount = profesorjiZrno.pridobiProfesorjeCount(query);

        return Response
                .ok(profesorjiZrno.pridobiProfesorje(query))
                .header("X-Total-Count", profesorjiCount)
                .build();
    }

    @GET
    @Path("{id}")
    public Response pridobiProfesorja(@PathParam("id") Integer id){
        Profesor Profesor = profesorjiZrno.pridobiProfesorja(id);

        if(Profesor != null){
            return Response.ok(Profesor).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response dodajProfesorja(Profesor Profesor){
        return Response.status(Response.Status.CREATED).entity(profesorjiZrno.dodajProfesorja( Profesor)).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiProfesorja(@PathParam("id") Integer id, Profesor Profesor){
        return Response.status(Response.Status.OK).entity(profesorjiZrno.posodobiProfesorja(id, Profesor)).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniProfesorja(@PathParam("id") Integer id){
        if(profesorjiZrno.odstraniProfesorja(id)){
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("{id}/dodaj-govorilno-uro")
    public Response dodajGovorilnoUro(@PathParam("id") Integer id, GovorilnaUraDto govorilnaUraDto){

        GovorilnaUra govorilnaUra = upravljanjeGovorilnihUrZrno.dodajGovorilneUre(govorilnaUraDto);

        if(govorilnaUra == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.CREATED).entity(govorilnaUra).build();
    }
}
