package demos;

import static org.junit.Assert.*;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestingObservables {
  private Integer[] n;
  private List<Integer> nList;
  private Observable<Integer> oList;

  @Before
  public void beforeAllTests() {
    n = new Integer[] { 1, 2, 3, 4, 5 };
    nList = Arrays.asList(n);
    oList = Observable.fromArray(n);
  }

  @Ignore
  @Test
  public void testObservableContent() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating TestObserver, single value");
    System.out.println("======================================================");
    int value = 10;
    Observable<Integer> o = Observable.just(value);
    TestObserver<Integer> testObserver = o.test();

    // What's in this Observable?
    testObserver.assertValue(value);

    // What state is the Observable in?
    testObserver.assertComplete();
    testObserver.assertNoErrors();
  }

  @Ignore
  @Test
  public void testObservableArray() {
    System.out.println("======================================================");
    System.out.println("= Demonstrating TestObserver, from an array");
    System.out.println("======================================================");
    TestObserver<Integer> testObserver = oList.test();
    testObserver.assertValues(1, 2, 3, 4, 5); // implicitly Complete after values
    testObserver.assertValueCount(n.length);
    testObserver.assertComplete();

    // testObserver.assertError(someError)
    // testObserver.assertNotComplete(); // Would fail, if assertValues() is true
  }

  @Ignore
  @Test
  public void testObservableOneItem() {
    System.out.println("======================================================");
    System.out.println("= Testing one item from an Observable");
    System.out.println("======================================================");
    Integer first = oList.blockingFirst(); // also blockingLast()
    assertEquals(first, n[0]);
  }

}
