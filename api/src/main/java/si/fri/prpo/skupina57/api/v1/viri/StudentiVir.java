package si.fri.prpo.skupina57.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.security.annotations.Secure;
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
import si.fri.prpo.skupina57.katalog.entitete.Student;
import si.fri.prpo.skupina57.storitve.dtos.PrijavaOdjavaDto;
import si.fri.prpo.skupina57.storitve.zrna.StudentiZrno;
import si.fri.prpo.skupina57.storitve.zrna.UpravljanjeGovorilnihUrZrno;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Secure
@ApplicationScoped
@Path("studenti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private StudentiZrno studentiZrno;


    @Inject
    private UpravljanjeGovorilnihUrZrno upravljanjeGovorilnihUrZrno;

//    @GET
//    public Response pridobiStudente(){
//        List<Student> studenti = studentiZrno.getStudenti();
//
//        return Response.ok(studenti).header("X-Total-Count", studenti.size()).build();
//    }
    @Operation(description = "Vrne seznam studentov", summary = "Seznam studentov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam studentov",
                    content = @Content(schema = @Schema(implementation = Student.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name= "X-Total-Count", description = "Število vrnjenih studentov")}
            )
    })
    @PermitAll
    @GET
    public Response pridobiStudente() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long profesorjiCount = studentiZrno.pridobiStudenteCount(query);

        return Response
                .ok(studentiZrno.pridobiStudente(query))
                .header("X-Total-Count", profesorjiCount)
                .build();
    }

    @Operation(description = "Vrne podrobnosti studenta", summary = "Podrobsnosti studenta")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobsnosti studenta",
                    content = @Content(schema = @Schema(implementation = Student.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Studenta ne obstaja"
            )
    })
    @PermitAll
    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@Parameter(
            description = "identifikator studenta za prodobivanje elementa",
            required = true
    )@PathParam("id") Integer id){
        Student student = studentiZrno.pridobiStudenta(id);

        if(student != null){
            return Response.ok(student).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @Operation(description = "Ustvari novega studenta", summary = "Dodaj studenta")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Student uspešno dodana",
                    content = @Content(schema = @Schema(implementation = Student.class))
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @RolesAllowed("profesor")
    @POST
    public Response dodajStudenta(@RequestBody(
            description = "DTO objekt za dodajanje studenta",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Student.class)
            )
    ) Student student){
        return Response.status(Response.Status.CREATED).entity(studentiZrno.dodajStudenta( student)).build();
    }

    @Operation(description = "Posodobi studenta", summary = "Posodobi studenta")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Uspešno posodobil studenta",
                    content = @Content(schema = @Schema(implementation = Student.class))
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @RolesAllowed("profesor")
    @PUT
    @Path("{id}")
    public Response posodobiStudenta(@Parameter(
            description = "identifikator studenta za posodabljanje elementa",
            required = true
    )@PathParam("id") Integer id, @RequestBody(
            description = "DTO objekt za posodabljanje studenta",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Student.class)
            )
    ) Student student){
        return Response.status(Response.Status.OK).entity(studentiZrno.posodobiStudenta(id, student)).build();
    }


    @Operation(description = "Briše studenta", summary = "Briše studenta")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Studenta uspešno izbrisan",
                    content = @Content(schema = @Schema(implementation = Student.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Student ura ne obstaja"
            )
    })
    @RolesAllowed("profesor")
    @DELETE
    @Path("{id}")
    public Response odstraniStudenta(@Parameter(
            description = "identifikator studenta za brisanje elementa",
            required = true
    )@PathParam("id") Integer id){
        if(studentiZrno.odstraniStudenta(id)){
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Prijava na termin", summary = "prijava na termin")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Uspesna prijava na termin",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Student ne obstaja"
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @RolesAllowed("student")
    @POST
    @Path("{id}/prijava-na-termin")
    public Response prijavaNaTermin(@Parameter(
            description = "identifikator studenta za prijava na termin",
            required = true
    )@PathParam("id") Integer id, @RequestBody(
            description = "DTO objekt za prijava na termin",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = PrijavaOdjavaDto.class)
            )
    )  PrijavaOdjavaDto prijavaOdjavaDto){
        GovorilnaUra govorilnaUra = upravljanjeGovorilnihUrZrno.prijavaNaTermin(prijavaOdjavaDto);

        if(govorilnaUra == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.CREATED).entity(govorilnaUra).build();
    }

    @Operation(description = "Odjava na termin", summary = "Odjava na termin")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Uspesna odjava od termina",
                    content = @Content(schema = @Schema(implementation = GovorilnaUra.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Student ne obstaja"
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka")
    })
    @RolesAllowed("student")
    @POST
    @Path("{id}/odjava-od-termina")
    public Response odjavaOdTermina(@Parameter(
            description = "identifikator studenta za odjavo od termina",
            required = true
    )@PathParam("id") Integer id, @RequestBody(
            description = "DTO objekt za odjavo od termina",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = PrijavaOdjavaDto.class)
            )
    )   PrijavaOdjavaDto prijavaOdjavaDto){
        if(upravljanjeGovorilnihUrZrno.odjavaOdTermina(prijavaOdjavaDto)){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
