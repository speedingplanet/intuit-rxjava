package rxjava.lab01;

import io.reactivex.rxjava3.observers.TestObserver;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

  @Test
  public void getTeam() {
    TestObserver<Employee> testObserver = mgr1.getTeam().test();

    Assertions.assertThat(testObserver.values()).containsExactlyElementsOf(mgr1Team);
    // testObserver.assertValues(mgr2Team.toArray(new Employee[0]));
    testObserver.assertComplete();
    testObserver.assertNoErrors();
  }

  @Test
  public void getTeam_observableCanBeModifiedBeforeSubscription() {
    mgr1.getTeam().subscribe(System.out::println);
    System.out.println();
    mgr1Team.add(new Employee("andrew", "wasicek", 20));
    mgr1.getTeam().subscribe(System.out::println);
  }

  @Test
  public void monitorTeamAdditions() {
    List<Employee> employeesToAdd = Arrays.asList(
            new Employee("emma", "wasicek", 10),
            new Employee("hillary", "wasicek", 15),
            new Employee("jake", "wasicek", 20)
    );
    List<Employee> employeesAdded = new ArrayList<>();

    mgr1.getTeamAdditionsObservable().subscribe(evt -> employeesAdded.add(evt.getEmployee()));
    for (Employee employee : employeesToAdd) {
      mgr1.addTeamMember(employee);
    }

    Assertions.assertThat(employeesAdded).containsExactlyElementsOf(employeesToAdd);
  }

  @Test
  public void monitorTeamRemovals() {
    List<Employee> employeesRemoved = new ArrayList<>();
    List<Employee> employeesToRemove = Arrays.asList(
            emp1,
            emp7, // emp7 is not actually on the team, so can't be removed
            emp3
    );
    List<Employee> expectedEmployeesRemoved = Arrays.asList(emp1, emp3);
    mgr1.getTeamRemovalsObservable().subscribe(evt -> employeesRemoved.add(evt.getEmployee()));

    for (Employee employee : employeesToRemove) {
      mgr1.removeTeamMember(employee);
    }

    Assertions.assertThat(employeesRemoved).containsExactlyElementsOf(expectedEmployeesRemoved);
  }

  @Test
  public void monitorTeamChanges() {
    List<Employee> employeesRemoved = new ArrayList<>();
    List<Employee> employeesToRemove = Arrays.asList(emp1, emp3);
    List<Employee> employeesToAdd = Arrays.asList(
            new Employee("emma", "wasicek", 10),
            new Employee("hillary", "wasicek", 15),
            new Employee("jake", "wasicek", 20)
    );
    List<Employee> employeesAdded = new ArrayList<>();
    mgr1.getTeamChangesObservable().subscribe(evt -> {
      if (evt instanceof EmployeeAddition) {
        System.out.println("++++Employee added: " + evt.getEmployee());
        employeesAdded.add(evt.getEmployee());
      } else if (evt instanceof EmployeeRemoval) {
        System.out.println("----Employee removed: " + evt.getEmployee());
        employeesRemoved.add(evt.getEmployee());
      } else {
        System.out.println("******Should never occur******");
      }
    });

    for (Employee employee : employeesToRemove) {
      mgr1.removeTeamMember(employee);
    }
    for (Employee employee : employeesToAdd) {
      mgr1.addTeamMember(employee);
    }

    Assertions.assertThat(employeesAdded).containsExactlyElementsOf(employeesToAdd);
    Assertions.assertThat(employeesRemoved).containsExactlyElementsOf(employeesToRemove);
  }

  @Test
  public void monitorTeamRemovals_afterTeamFinalized() {
    mgr1.getTeamRemovalsObservable().subscribe(System.out::println, err -> {}, () -> {});

    mgr1.finalizeTeam();

    mgr1.removeTeamMember(emp1);
    mgr1.removeTeamMember(emp7);
    mgr1.removeTeamMember(emp3);
  }
}
