import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.annotation.Metered;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ComparatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.commons.collections4.ComparatorUtils.NATURAL_COMPARATOR;

public class SenderVerticle extends AbstractVerticle implements Handler<Long>{
    private final Meter meter;
    private static AtomicInteger meterCount = new AtomicInteger();
    private static final List<String> METER_NAMES = new CopyOnWriteArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(SenderVerticle.class);

    public SenderVerticle(){
        String meterName = "SenderVerticle " + meterCount.incrementAndGet();
        meter = SharedMetricRegistries.getOrCreate("registry").meter(meterName);
        METER_NAMES.add(meterName);
    }
    @Override
    public void start() throws Exception {
        vertx.setTimer(5000, this);
    }

    public void handle(Long aLong) {
        meter.mark();
    }

    public static List<String> getMeterNames(){
        METER_NAMES.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());
        return Collections.unmodifiableList(METER_NAMES);
    }
}
