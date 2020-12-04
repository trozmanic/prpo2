package si.fri.prpo.skupina57.api.v1.viri;


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
    @Operation(description = "Vrne seznam profesorjev", summary = "Seznam profesorjev")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam profesorjev",
                    content = @Content(schema = @Schema(implementation = Profesor.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name= "X-Total-Count", description = "Število vrnjenih govorilnih ur")}
            )
    })
    @GET
    public Response pridobiProfesorje(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long profesorjiCount = profesorjiZrno.pridobiProfesorjeCount(query);

        return Response
                .ok(profesorjiZrno.pridobiProfesorje(query))
                .header("X-Total-Count", profesorjiCount)
                .build();
    }
    
    
    
    @Operation(description = "Vrne podrobnosti profesorja", summary = "Podrobsnosti profesorja")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobsnosti profesorja",
                    content = @Content(schema = @Schema(implementation = Profesor.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Profesor ne obstaja",
                    content = @Content(schema = @Schema(implementation = Profesor.class))
            )
    })
    @GET
    @Path("{id}")
    public Response pridobiProfesorja(@Parameter(
            description = "identifikator profesorja za prodobivanje elementa",
            required = true
    )@PathParam("id") Integer id){
        Profesor Profesor = profesorjiZrno.pridobiProfesorja(id);

        if(Profesor != null){
            return Response.ok(Profesor).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @Operation(description = "Ustvari novega profesorja", summary = "Dodaj profesorja")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Profesor uspešno dodan",
                    content = @Content(schema = @Schema(implementation = Profesor.class))
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @POST
    public Response dodajProfesorja(@RequestBody(
            description = "Objekt za dodajanje profesorjev",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Profesor.class)
            )
    ) Profesor Profesor){
        return Response.status(Response.Status.CREATED).entity(profesorjiZrno.dodajProfesorja( Profesor)).build();
    }

    @Operation(description = "Posodobi profesorja", summary = "Posodobi profesorja")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Uspešno posodobil profesorja",
                    content = @Content(schema = @Schema(implementation = Profesor.class))
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @PUT
    @Path("{id}")
    public Response posodobiProfesorja(@Parameter(
            description = "identifikator profesorja za posodabljanje elementa",
            required = true
    ) @PathParam("id") Integer id,@RequestBody(
            description = "Objekt za posodabljanje profesorja",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Profesor.class)
            )
    ) Profesor Profesor){
        return Response.status(Response.Status.OK).entity(profesorjiZrno.posodobiProfesorja(id, Profesor)).build();
    }




    @Operation(description = "Briše profesorja", summary = "Briše profesorja")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Profesor uspešno izbrisan",
                    content = @Content(schema = @Schema(implementation = Profesor.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Profesor ne obstaja",
                    content = @Content(schema = @Schema(implementation = Profesor.class))
            )
    })
    @DELETE
    @Path("{id}")
    public Response odstraniProfesorja(@Parameter(
            description = "identifikator profesorja za brisanje elementa",
            required = true
    )@PathParam("id") Integer id){
        if(profesorjiZrno.odstraniProfesorja(id)){
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @Operation(description = "Ustvari novo govorilno uro", summary = "Dodaj govorilno uro")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Govorilna ura uspešno dodana",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Profesor ne obstaja",
                    content = @Content(schema = @Schema(implementation = Profesor.class))
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @POST
    @Path("{id}/dodaj-govorilno-uro")
    public Response dodajGovorilnoUro(@Parameter(
            description = "identifikator profesorja za dodajanje govorilne ure",
            required = true
    )@PathParam("id") Integer id, @RequestBody(
            description = "DTO objekt za dodajanje govorilnih ur",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = GovorilnaUraDto.class)
            )
    ) GovorilnaUraDto govorilnaUraDto){

        GovorilnaUra govorilnaUra = upravljanjeGovorilnihUrZrno.dodajGovorilneUre(govorilnaUraDto);

        if(govorilnaUra == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.CREATED).entity(govorilnaUra).build();
    }
}
