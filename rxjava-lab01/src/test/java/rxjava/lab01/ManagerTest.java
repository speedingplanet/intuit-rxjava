package rxjava.lab01;

import static org.assertj.core.api.Assertions.assertThat;
// import org.junit.Assert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerTest {
  Employee emp1, emp2, emp3, emp4, emp5, emp6, emp7;
  Manager mgr1, mgr2;
  List<Employee> mgr1Team, mgr2Team;

  @Before()
  public void setUp() {
    emp1 = new Employee("John", "Paxton", 50000);
    emp2 = new Employee("Angela", "Cortez", 65000);
    emp3 = new Employee("Jack", "Hawksmoor", 60000);
    emp4 = new Employee("Shen", "Li-Min", 70000);
    emp5 = new Employee("Jeroen", "Thornedike", 60000);
    emp6 = new Employee("Lucas", "Trent", 40000);
    emp7 = new Employee("Jenny", "Quantum", 80000);

    mgr1Team = new ArrayList<>(Arrays.asList(emp1, emp2, emp3));
    mgr2Team = new ArrayList<>(Arrays.asList(emp4, emp5, emp6));

    mgr1 = new Manager("Steve", "Topdog", 100000, mgr1Team);
    mgr2 = new Manager("Jenny", "Bigcheese", 100000, mgr2Team);
  }

  @Ignore
  @Test
  public void testResults() {
    mgr1.getTeamAsObservable()
        .subscribe(v -> {
          System.out.println("Employee: " + emp1);
        }, e -> {
          System.err.println("Error: " + e);
        }, () -> {
          System.out.println("Finished");
        });
  }

  @Ignore
  @Test
  public void testOneValueOutsideObservable() {
    Iterable<Employee> empList = mgr1.getTeamAsObservable()
                                     .blockingIterable();
    assertThat(empList).contains(emp1);
  }

  @Ignore
  @Test
  public void testOneValueOutsideObservableFails() {
    Iterable<Employee> empList = mgr1.getTeamAsObservable()
                                     .blockingIterable();
    assertThat(empList).contains(emp4);
  }

  @Ignore
  @Test
  public void testOneValueObservableFirstElement() {
    mgr1.getTeamAsObservable()
        .firstElement()
        .subscribe(emp -> {
          // This raises an AssertionError if it fails
          // Details here: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling
          Assert.assertSame(emp, emp1);
        }, err -> {
          System.err.println("*** Custom error:" + err.getLocalizedMessage());
        });
  }

  @Ignore
  @Test
  public void testOneValueObservableFirstElementFails() {
    mgr1.getTeamAsObservable()
        .firstElement()
        .subscribe(emp -> {
          // This raises an AssertionError if it fails
          Assert.assertSame(emp, emp4);
        }, err -> {
          System.err.println("*** Custom error:" + err.getLocalizedMessage());
        });
  }
}
