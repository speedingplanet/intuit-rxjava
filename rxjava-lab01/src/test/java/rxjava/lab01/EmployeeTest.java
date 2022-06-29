package rxjava.lab01;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.assertj.core.api.Assertions;
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
  public void getFirstName() {
    emp1.getFirstName().subscribe(
            name -> Assertions.assertThat(name).isEqualTo("zJohn"),
            // note that simply rethrowing from the error callback will not actually cause the test to fail!
            // it is possible to access exceptions thrown from onError but it may require thread management
            // so generally better to handle exceptions in onError callbacks
            error -> Assertions.fail("Should not error. Error was: " + error),
            () -> {}
    );
  }

  @Test
  public void anotherGetFirstName() {
    Observable<String> firstName = emp1.getFirstName();
    TestObserver<String> testObserver = firstName.test();

    testObserver.assertComplete();
    testObserver.assertNoErrors();
    testObserver.assertValues("John");
  }

  @Test
  public void getLastName() {
    emp1.getLastName().subscribe(
            name -> Assertions.assertThat(name).isEqualTo("Paxton"),
            // note that simply rethrowing from the error callback will not actually cause the test to fail!
            error -> Assertions.fail("Should not error. Error was: " + error),
            () -> {}
    );
  }

  @Test
  public void getSalary() {
    emp1.getSalary().subscribe(
            salary -> Assertions.assertThat(salary).isEqualTo(50000),
            // note that simply rethrowing from the error callback will not actually cause the test to fail!
            error -> Assertions.fail("Should not error. Error was: " + error),
            () -> {}
    );
  }
}
