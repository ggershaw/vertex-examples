import io.vertx.core.impl.launcher.VertxCommandLauncher;

public class LauncherMain {
    public static void main(String[] args) {
        VertxCommandLauncher customCmdLauncher = new CustomLauncher();
        String[] strings = {"run", "VertexMainVerticle"};
        customCmdLauncher.dispatch(customCmdLauncher, strings);
    }

}
