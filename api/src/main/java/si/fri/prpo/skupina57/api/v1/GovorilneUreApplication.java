package si.fri.prpo.skupina57.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@OpenAPIDefinition(info = @Info(title = "Govorilne Ure API", version = "v1",
        contact = @Contact(email = "govrilneure@mail.com"),
        license = @License(name = "dev"), description = "API za govorilne ure."),
        servers = @Server(url = "http://localhost:8080/"))


@ApplicationPath("v1")
public class GovorilneUreApplication extends Application {

}
