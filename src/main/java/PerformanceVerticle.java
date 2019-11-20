import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.MetricsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PerformanceVerticle extends AbstractVerticle implements Handler<Long>{

    private Logger logger = LogManager.getLogger();
    private MetricsService metricsService;

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(10000, this);
        metricsService = MetricsService.create(vertx);
    }

    @Override
    public void handle(Long aLong) {
        JsonObject stats = new JsonObject();
        for(String meterName : SenderVerticle.getMeterNames()){
            stats.put(meterName,metricsService.getMetricsSnapshot(meterName));

            JsonObject senderVerticleCalls = metricsService.getMetricsSnapshot(meterName);
            logger.info("MeterName Should be: {} MeterName is: {}", meterName, senderVerticleCalls);
        }
    }
}

