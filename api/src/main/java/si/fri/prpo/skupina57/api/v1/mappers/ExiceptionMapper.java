package si.fri.prpo.skupina57.api.v1.mappers;

import si.fri.prpo.skupina57.storitve.izjeme.DatumPreteklostIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExiceptionMapper implements ExceptionMapper<DatumPreteklostIzjema> {

    @Override
    public Response toResponse(DatumPreteklostIzjema datumPreteklostIzjema) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \""+datumPreteklostIzjema.getMessage()+"\"}")
                .build();
    }
}
