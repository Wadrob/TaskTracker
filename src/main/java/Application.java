import manager.TaskManager;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        new TaskManager().run();
    }
}
