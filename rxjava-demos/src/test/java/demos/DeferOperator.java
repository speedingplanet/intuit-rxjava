package demos;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DeferOperator {

  @Test
  public void minimalDefer() throws InterruptedException {
    Observable<Integer> result = Observable.defer(() -> {
      // Assume this does something much more complicated
      return Observable.just(2 + 2);
    });

    // Observable.just(/* complicated/expensive value */)
    Observable<Long> time = Observable.fromSupplier(() -> {
      return (new Date()).getTime();
    });

    System.out.println("Current time: " + (new Date()).getTime());

    Thread.sleep(3000);

    time.subscribe(v -> System.out.println("Time after sleeping: " + v));

    result.subscribe(v -> System.out.println("Deferred result: " + v));
  }

  @Test
  public void usefulDefer() {
    class TestWrapper {
      private String value;

      public void setValue(String value) {
        this.value = value;
      }

      public Observable<String> valueAsObservable() {
        return Observable.just(value); // value is evaluated
      }

      public Observable<String> deferredValue() {
        // value is not evaluated until subscription time
        return Observable.defer(() -> Observable.just(value));
      }
    }

    TestWrapper wrapper = new TestWrapper();
    // Fails
    // Observable<String> retrievedValue = wrapper.valueAsObservable();

    // Succeeds
    Observable<String> retrievedValue = wrapper.deferredValue();
    wrapper.setValue("Greetings");
    retrievedValue.subscribe(v -> System.out.println("Retrieved value: " + v));
  }

  @Test
  public void testHotToCold() throws InterruptedException {
    Observable<Long> cold = Observable.defer(() -> {
      PublishSubject<Long> subject = PublishSubject.create();
      Observable<Long> intervalNumbers = Observable.interval(0, 200, TimeUnit.MILLISECONDS);
      // intervalNumbers is hot here....
      intervalNumbers.subscribe(v -> subject.onNext(v));
      return subject;
    });

    Thread.sleep(1000);

    // This will be a bit behind.
    cold.subscribe(v -> System.out.println("Next value from subject: " + v));

    Thread.sleep(1000);
  }


}
