package rxjava.lab02;

import static org.junit.Assert.*;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ManagerTest {
  Employee emp1, emp2, emp3, emp4, emp5, emp6, emp7;
  Manager mgr1, mgr2;
  List<Employee> mgr1Team, mgr2Team, duplicateMgr1Team, shuffledMgr1Team;

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
    duplicateMgr1Team = new ArrayList<>(Arrays.asList(emp1, emp2, emp3));
    shuffledMgr1Team = new ArrayList<>(Arrays.asList(emp3, emp1, emp2));
    mgr2Team = new ArrayList<>(Arrays.asList(emp2, emp3, emp4));

    mgr1 = new Manager("Steve", "Topdog", 100000, mgr1Team);
    mgr2 = new Manager("Jenny", "Bigcheese", 100000, mgr2Team);
  }

  @Test
  @Ignore
  public void getEmployeeNames() {
    Observable.just(mgr1, mgr2)
              .flatMap(Manager::getTeam)
              .flatMap(e -> {
                Observable<String> firstName = e.getFirstName();
                Observable<String> lastName = e.getLastName();
                Observable<Integer> salary = e.getSalary();
                return Observable.zip(firstName, lastName, salary,
                                      (first, last, pay) -> String.format("%s %s [%d]%n", first,
                                                                          last, pay));
              })
              .subscribe(name -> {
                System.out.print(name);
              });

  }

  @Test
  @Ignore
  public void getAllSalariesRegularMap() {
    Observable.just(mgr1, mgr2)
              .flatMap(Manager::getTeam)
              .flatMap(Employee::getSalary)
              .subscribe(System.out::println);
  }

  @Test
  @Ignore
  public void getAllSalariesFlatter() {
    Observable.just(mgr1, mgr2)
              .flatMap(mgr -> {
                return mgr.getTeam()
                          .flatMap(e -> e.getSalary());
              })
              .subscribe(s -> System.out.println(s));
  }

  @Test
  @Ignore
  public void getAllSalaries() {
    Observable.just(mgr1, mgr2)
              .flatMap(mgr -> {
                return mgr.getTeam();
              })
              .flatMap(e -> e.getSalary())
              .subscribe(s -> System.out.println(s));
  }

  @Test
  @Ignore
  @SuppressWarnings("unchecked")
  public void testAddTeamMemberWatch() {
    // TestObserver and TestSubscriber are not available
    // Both require an actual Observable (or subclass) to work
    Observer<Employee> mockedObserver = mock(Observer.class);
    mgr1.subscribe(mockedObserver);
    mgr1.addTeamMember(emp4);
    verify(mockedObserver).onNext(emp4);
  }

  @Ignore
  @Test
  public void testTeamObservable() {
    assertNotNull(mgr1.getTeam());
  }

  @Ignore
  @Test
  public void testTeamMember() {
    assertEquals(emp1,
                 mgr1.getTeam()
                     .blockingFirst());
  }

  @Ignore
  @Test
  public void testTeamMembers() {
    mgr1.getTeam()
        .test()
        .assertValueAt(2, emp3);
  }

  @Ignore
  @Test
  public void testTeamMembersResult() {
    mgr1.getTeam()
        .test()
        .assertResult(emp1, emp2, emp3);
  }

  @Ignore
  @Test
  public void testTeamAsList() {
    // These pass
    mgr1.getTeam()
        .toList()
        .test()
        .assertValue(mgr1Team);
    // mgr1.getTeam().toList().test().assertValue(duplicateMgr1Team);

    // Fails
    // mgr1.getTeam().toList().test().assertValue(shuffledMgr1Team);
  }

  @Ignore
  @Test
  public void testModifyTeam() {
    mgr1.addTeamMember(emp4);
    // mgr1.getTeam().toList().test().assertValue(mgr1Team);
    mgr1.getTeam()
        .test()
        .assertResult(emp1, emp2, emp3, emp4);

    mgr1.removeTeamMember(emp2);
    // mgr1.getTeam().toList().test().assertValue(mgr1Team);
    mgr1.getTeam()
        .test()
        .assertResult(emp1, emp3, emp4);
  }

}
