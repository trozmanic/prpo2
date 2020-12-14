package si.fri.prpo.skupina57.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@DeclareRoles({"profesor", "student"})
@OpenAPIDefinition(info = @Info(title = "Govorilne Ure API", version = "v1",
        contact = @Contact(email = "govrilneure@mail.com"),
        license = @License(name = "dev"), description = "API za govorilne ure."),
        servers = @Server(url = "http://localhost:8080/"))

@ApplicationPath("v1")
public class GovorilneUreApplication extends Application {

}

//authentikacija
// host port 8082  (8080 main app, 8081 secondary app)
// docker run -p 8082:8080 -e KEYCLOAK_USER=editor778 -e KEYCLOAK_PASSWORD=editthispa44ss jboss/keycloak:11.0.3
// username: editor778 password: editthispa44ss
// management http://localhost:8082/
// roles: profesor, student
// uporabniki:
// username: profesor pass: profesor
// username: student pass: student

/*

GET TOKEN

STUDENT

curl -L -X POST 'http://localhost:8082/auth/realms/govorilne_ure/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=govorilne_ure_token' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=student' \
--data-urlencode 'password=student'


PROFESOR

curl -L -X POST 'http://localhost:8082/auth/realms/govorilne_ure/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=govorilne_ure_token' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=profesor' \
--data-urlencode 'password=profesor'


v postman kopirej token pod auth: bearer token

pol pa normalno api tko kot prej

 */
