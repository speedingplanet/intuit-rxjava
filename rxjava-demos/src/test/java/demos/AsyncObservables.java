package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class AsyncObservables {
  @Test
  public void createObservableFromFuture() throws InterruptedException,
    ExecutionException {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    System.out.println("Before scheduling the future");
    Future<String> future = executor.schedule(() -> "Completed value", 5, TimeUnit.SECONDS);
    System.out.println("After scheduling the future");
    Observable<String> o = Observable.fromFuture(future);
    System.out.println("Before getting");
    future.get(); // Blocks!
    System.out.println("Before subscribing");
    o.subscribe(v -> System.out.printf("Future completed with %s%n", v),
                e -> System.err.println("Something went wrong"),
                () -> System.out.println("Finished."));
    System.out.println("After subscribing");
    executor.shutdown();
  }
}
