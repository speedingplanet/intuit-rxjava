package rxjava.lab01.solutions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import rxjava.lab01.Employee;

import static org.junit.Assert.assertEquals;

public class SolutionEmployeeTest {
  Employee emp1, emp2, emp3;

  @Before
  public void setUp() {
    emp1 = new Employee("John", "Paxton", 50000);
    emp2 = new Employee("Angela", "Cortez", 65000);
    emp3 = new Employee("Jack", "Hawksmoor", 60000);
  }

  @Test
  public void testGetFirstName() {
    Observable<String> firstName = emp1.getFirstName();
    firstName.subscribe(
        value -> {
          assertEquals("Bob", value);
        },
        error -> {
          System.err.println("Some kind of error: " + error);
        });
  }

  @Test
  public void betterTestGetFirstName() {
    Observable<String> firstName = emp1.getFirstName();
    TestObserver<String> to = firstName.test();
    to.assertValue("Bob");
  }

}
