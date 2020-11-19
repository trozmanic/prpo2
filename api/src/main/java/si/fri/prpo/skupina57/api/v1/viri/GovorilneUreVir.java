package si.fri.prpo.skupina57.api.v1.viri;


import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;
import si.fri.prpo.skupina57.katalog.entitete.Student;
import si.fri.prpo.skupina57.storitve.zrna.GovorilneUreZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("govorilne-ure")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GovorilneUreVir {


    //@Context
    //protected UriInfo uriInfo;

    @Inject
    private GovorilneUreZrno govorilneUreZrno;


    @GET
    public Response pridobiGovorilneUre(){
        List<GovorilnaUra> govorilneUre = govorilneUreZrno.getGovorilneUre();

        return Response.ok(govorilneUre).header("X-Total-Count", govorilneUre.size()).build();
    }



    @GET
    @Path("{id}")
    public Response pridobiGovorilnoUro(@PathParam("id") Integer id){
        GovorilnaUra govorilnaUra = govorilneUreZrno.pridobiGovorilnoUro(id);

        if(govorilnaUra != null){
            return Response.ok(govorilnaUra).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response dodajGovorilnoUro(GovorilnaUra govorilnaUra){
        return Response.status(Response.Status.CREATED).entity(govorilneUreZrno.dodajGovorilnoUro(govorilnaUra)).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiGovorilnoUro(@PathParam("id") Integer id, GovorilnaUra govorilnaUra){
        return Response.status(Response.Status.OK).entity(govorilneUreZrno.posodobiGovorilnoUro(id, govorilnaUra)).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniGovorilnoUro(@PathParam("id") Integer id){
        if(govorilneUreZrno.odstraniGovorilnoUro(id)){
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
