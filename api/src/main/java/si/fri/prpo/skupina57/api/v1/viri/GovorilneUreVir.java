package si.fri.prpo.skupina57.api.v1.viri;


import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;
import si.fri.prpo.skupina57.katalog.entitete.Student;
import si.fri.prpo.skupina57.storitve.zrna.GovorilneUreZrno;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

//@Secure
@ApplicationScoped
@Path("govorilne-ure")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, DELETE, PUT, HEAD, OPTIONS")
public class GovorilneUreVir {


    @Context
    protected UriInfo uriInfo;

    @Inject
    private GovorilneUreZrno govorilneUreZrno;


//    @GET
//    public Response pridobiGovorilneUre(){
//        List<GovorilnaUra> govorilneUre = govorilneUreZrno.getGovorilneUre();
//
//        return Response.ok(govorilneUre).header("X-Total-Count", govorilneUre.size()).build();
//    }

    @Operation(description = "Vrne seznam govorilnih ur", summary = "Seznam govorilnih ur")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam govorilnih ur",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name= "X-Total-Count", description = "Število vrnjenih govorilnih ur")}
            )
    })
    @PermitAll
    @GET
    public Response pridobiGovorilneUre(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long govorilneUreCount = govorilneUreZrno.pridobiGovorilneUreCount(query);

        return Response
                .ok(govorilneUreZrno.pridobiGovorilneUre(query))
                .header("X-Total-Count", govorilneUreCount)
                .build();
    }


    @Operation(description = "Vrne podrobnosti govorilne ure", summary = "Podrobsnosti govorilne ure")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobsnosti govorilne ure",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Govorilna ura ne obstaja"
            )
    })
    @PermitAll
    @GET
    @Path("{id}")
    public Response pridobiGovorilnoUro(@Parameter(
            description = "identifikator govorilne ure za prodobivanje elementa",
            required = true
    )@PathParam("id") Integer id){
        GovorilnaUra govorilnaUra = govorilneUreZrno.pridobiGovorilnoUro(id);

        if(govorilnaUra != null){
            return Response.ok(govorilnaUra).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }




    @Operation(description = "Ustvari novo govorilno uro", summary = "Dodaj govorilno uro")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Govorilna ura uspešno dodana",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class))
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @RolesAllowed("profesor")
    @POST
    public Response dodajGovorilnoUro(@RequestBody(
            description = "DTO objekt za dodajanje govorilnih ur",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = GovorilnaUra.class)
            )
    ) GovorilnaUra govorilnaUra){
        return Response.status(Response.Status.CREATED).entity(govorilneUreZrno.dodajGovorilnoUro(govorilnaUra)).build();
    }



    @Operation(description = "Posodobi govorilno uro", summary = "Posodobi govorilno uro")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Uspešno posodobil govorilno uro",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class))
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @RolesAllowed("profesor")
    @PUT
    @Path("{id}")
    public Response posodobiGovorilnoUro(@Parameter(
            description = "identifikator govorilne ure za posodabljanje elementa",
            required = true
    )@PathParam("id") Integer id, @RequestBody(
            description = "DTO objekt za posodabljanje govorilnih ur",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = GovorilnaUra.class)
            )
    )GovorilnaUra govorilnaUra){
        return Response.status(Response.Status.OK).entity(govorilneUreZrno.posodobiGovorilnoUro(id, govorilnaUra)).build();
    }

    @Operation(description = "Briše govorilno uro", summary = "Briše govorilno uro")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Govorilna ura uspešno izbrisana",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Govorilna ura ne obstaja"
            )
    })
    @RolesAllowed("profesor")
    @DELETE
    @Path("{id}")
    public Response odstraniGovorilnoUro(@Parameter(
            description = "identifikator govorilne ure za brisanje elementa",
            required = true
    )@PathParam("id") Integer id){
        if(govorilneUreZrno.odstraniGovorilnoUro(id)){
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
