package movingframe;

import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class Starter {

    public static void main(String[] args) throws Exception {
        
        ExecutorService es = newSingleThreadExecutor();
        es.execute(new MovingFrame());
        es.shutdown();
        
    }
}
