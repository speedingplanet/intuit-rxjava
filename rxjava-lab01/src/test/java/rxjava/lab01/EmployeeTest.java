package rxjava.lab01;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {
  Employee emp1, emp2, emp3;

  @Before
  public void setUp() {
    emp1 = new Employee("John", "Paxton", 50000);
    emp2 = new Employee("Angela", "Cortez", 65000);
    emp3 = new Employee("Jack", "Hawksmoor", 60000);
  }

  @Test
  public void getFirstNameBlockingFirst() {
    Observable<String> firstName$ = emp1.getFirstName();
    System.out.println(firstName$.blockingFirst());
  }

  @Test
  public void getFirstNameWithSubscribe() {
    // Uses no-op onError and onComplete provided by Observable
    emp1.getFirstName()
        .subscribe(v -> System.out.println("First name: " + v));

    // Full version
    /*
    emp1.getFirstName()
        .subscribe(v -> System.out.println("First name: " + v),
                   System.err::println,
                   () -> System.out.println("Finished, with value"));
    */
  }

  @Test
  public void getFirstNameAsMaybe() {
    Observable<String> oFirstName = emp1.getFirstName();
    Maybe<String> firstName = oFirstName.firstElement();

    // Only one of these will run, ever, and only once
    firstName.subscribe(v -> System.out.println("First name: " + v),
                        System.err::println,
                        () -> System.out.println("Finished, no value"));

    // Use no-op defaults as in getFirstNameWithSubscribe
    // firstName.subscribe(v -> System.out.println("First name: " + v));

  }

  @Test
  public void getFirstNameAsSingleWithAlternate() {
    Observable<String> oFirstName = emp1.getFirstName();
    Single<String> firstName = oFirstName.first("Unknown");
    System.out.println("First name: " + firstName.blockingGet());
  }

}
