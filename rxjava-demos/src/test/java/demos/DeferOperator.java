package demos;

import io.reactivex.rxjava3.core.Observable;
import org.junit.Test;

public class DeferOperator {

  @Test
  public void minimalDefer() {
    Observable<Integer> result = Observable.defer(() -> {
      // Assume this does something much more complicated
      return Observable.just(2 + 2);
    });

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
        return Observable.just(value);
      }

      public Observable<String> deferredValue() {
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
}
