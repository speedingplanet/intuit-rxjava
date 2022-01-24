package rxjava.lab01;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ManagerPublishSubjectTest {
  Employee emp1, emp2, emp3, emp4, emp5, emp6, emp7;
  ManagerPublisherSubject mgr1, mgr2;
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

    mgr1 = new ManagerPublisherSubject("Steve", "Topdog", 100000, mgr1Team);
    mgr2 = new ManagerPublisherSubject("Jenny", "Bigcheese", 100000, mgr2Team);
  }

  @Test
  public void testAddTeamMemberWatch() {
    TestObserver<Employee> testWatcher = mgr1.test();
    testWatcher.assertNoValues();
    testWatcher.assertEmpty();
    testWatcher.assertValueCount(0);

    mgr1.addTeamMember(emp4);

    testWatcher.assertValueCount(1);
    testWatcher.assertValuesOnly(emp4);

  }

  @Ignore
  @Test
  public void testTeamObservable() {
    assertNotNull(mgr1.getTeam());
  }

  @Ignore
  @Test
  public void testTeamMember() {
    assertEquals(emp1, mgr1.getTeam().blockingFirst());
  }

  @Ignore
  @Test
  public void testTeamMembers() {
    mgr1.getTeam().test().assertValueAt(2, emp3);
  }

  @Ignore
  @Test
  public void testTeamMembersResult() {
    mgr1.getTeam().test().assertResult(emp1, emp2, emp3);
  }

  @Ignore
  @Test
  public void testTeamAsList() {
    // These pass
    mgr1.getTeam().toList().test().assertValue(mgr1Team);
    // mgr1.getTeam().toList().test().assertValue(duplicateMgr1Team);

    // Fails
    // mgr1.getTeam().toList().test().assertValue(shuffledMgr1Team);
  }

  @Ignore
  @Test
  public void testModifyTeam() {
    mgr1.addTeamMember(emp4);
    // mgr1.getTeam().toList().test().assertValue(mgr1Team);
    mgr1.getTeam().test().assertResult(emp1, emp2, emp3, emp4);

    mgr1.removeTeamMember(emp2);
    // mgr1.getTeam().toList().test().assertValue(mgr1Team);
    mgr1.getTeam().test().assertResult(emp1, emp3, emp4);
  }

}
