import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.launcher.VertxCommandLauncher;
import io.vertx.core.impl.launcher.VertxLifecycleHooks;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLauncher extends VertxCommandLauncher implements VertxLifecycleHooks {
    private Logger logger = LogManager.getLogger();

    @Override
    public void afterConfigParsed(JsonObject jsonObject) {
        logger.info("After Config Parsed");
    }

    @Override
    public void beforeStartingVertx(VertxOptions vertxOptions) {
        logger.info("Before Starting Vertex");
        vertxOptions.setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true).setRegistryName("registry"));
    }

    @Override
    public void afterStartingVertx(Vertx vertx) {
        logger.info("After Starting Vertx");
        vertx.deployVerticle(SenderVerticle.class, new DeploymentOptions().setInstances(16));
        vertx.deployVerticle(new ReceiverVerticle());
        vertx.deployVerticle(new PerformanceVerticle());
    }

    @Override
    public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
        logger.info("Before Deploying Verticle");
    }

    @Override
    public void beforeStoppingVertx(Vertx vertx) {

    }

    @Override
    public void afterStoppingVertx() {

    }

    @Override
    public void handleDeployFailed(Vertx vertx, String s, DeploymentOptions deploymentOptions, Throwable throwable) {

    }
}
