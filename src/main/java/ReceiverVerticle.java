import io.vertx.core.AbstractVerticle;

public class ReceiverVerticle extends AbstractVerticle{
    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("testTopic", msg -> System.out.println(msg.body()));
    }
}
