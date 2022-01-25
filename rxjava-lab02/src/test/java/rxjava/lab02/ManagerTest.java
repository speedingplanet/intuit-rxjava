package rxjava.lab02;

import static org.junit.Assert.*;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.TestObserver;
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
  public void testAddTeamMemberWatch() {
    TestObserver<Employee> testWatcher = mgr1.getTrackerObserver();
    testWatcher.assertNoValues();
    testWatcher.assertEmpty();
    testWatcher.assertValueCount(0);

    mgr1.addTeamMember(emp4);

    testWatcher.assertValueCount(1);
    testWatcher.assertValuesOnly(emp4);

  }

  @Test
  public void testAddTeamMemberWithObserver() {
    TestObserver<Employee> testWatcher = TestObserver.create();
    mgr1.subscribe(testWatcher);
    testWatcher.assertNoValues();
    testWatcher.assertEmpty();
    testWatcher.assertValueCount(0);

    mgr1.addTeamMember(emp4);

    testWatcher.assertValueCount(1);
    testWatcher.assertValuesOnly(emp4);
  }

  @Test
  public void testTeamObservable() {
    assertNotNull(mgr1.getTeam());
  }

  @Test
  public void testTeamMember() {
    assertEquals(emp1,
                 mgr1.getTeam()
                     .blockingFirst());
  }

  @Test
  public void testTeamMembers() {
    mgr1.getTeam()
        .test()
        .assertValueAt(2, emp3);
  }

  @Test
  public void testTeamMembersResult() {
    mgr1.getTeam()
        .test()
        .assertResult(emp1, emp2, emp3);
  }

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

