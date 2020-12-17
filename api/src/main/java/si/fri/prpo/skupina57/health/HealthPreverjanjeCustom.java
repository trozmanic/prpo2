package si.fri.prpo.skupina57.health;

import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class HealthPreverjanjeCustom implements HealthCheck {
    //custom health check ki vrne vedno up
    public HealthCheckResponse call() {
        return HealthCheckResponse.up(HealthPreverjanjeCustom.class.getSimpleName());
    }

}
