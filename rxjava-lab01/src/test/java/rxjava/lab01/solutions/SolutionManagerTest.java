package rxjava.lab01.solutions;

import io.reactivex.rxjava3.observers.TestObserver;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rxjava.lab01.Employee;
import rxjava.lab01.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionManagerTest {
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

  @Test
  public void testGetTeam() {
    TestObserver<Employee> to = mgr1.getTeam().test();

    // One possibility
    Assertions.assertThat(to.values()).containsExactlyElementsOf(mgr1Team);

    // Second possibility
    Employee[] e = new Employee[3];
    to.assertValues(mgr1Team.toArray(e));

    // emp7 is an alternate in case first() can't return a value
    Assert.assertEquals(mgr1.getTeam().first(emp7).blockingGet(), emp1);

    // Third possibility!
    to.assertValues(emp1, emp2, emp3);
    to.assertComplete();
    to.assertNoErrors();
    to.assertValueAt(0, emp1);

  }
}
