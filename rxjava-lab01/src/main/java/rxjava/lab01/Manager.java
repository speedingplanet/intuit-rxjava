package rxjava.lab01;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

abstract class EmployeeEvent {
  private final Employee employee;
  public EmployeeEvent(Employee employee) { this.employee = employee; }

  public Employee getEmployee() {
    return employee;
  }
}

class EmployeeRemoval extends EmployeeEvent {
  public EmployeeRemoval(Employee employee) {
    super(employee);
  }
}

class EmployeeAddition extends EmployeeEvent {
  public EmployeeAddition(Employee employee) {
    super(employee);
  }
}

public class Manager extends Employee {
  private List<Employee> team;
  private final PublishSubject<EmployeeEvent> teamTracker;


  public Manager(String firstName, String lastName, Integer salary, List<Employee> team) {
    super(firstName, lastName, salary);
    Objects.requireNonNull(team, "Team is null");
    this.team = team;
    teamTracker = PublishSubject.create();
  }

  //   public List<Employee> getTeam() { return team; }
  public Observable<Employee> getTeam() {
    return Observable.fromIterable(team);
  }

  public void addTeamMember(Employee employee) {
    team.add(employee);
    teamTracker.onNext(new EmployeeAddition(employee));
  }

  public void removeTeamMember(Employee employee) {
    boolean wasRemoved = team.remove(employee);
    if (wasRemoved) {
      teamTracker.onNext(new EmployeeRemoval(employee));
    }
  }

  public void finalizeTeam() {
    teamTracker.onComplete();
  }

  public Observable<EmployeeAddition> getTeamAdditionsObservable() {
    // return teamTracker.filter(event -> event instanceof EmployeeAddition);
    return teamTracker.ofType(EmployeeAddition.class);
  }

  public Observable<EmployeeRemoval> getTeamRemovalsObservable() {
//    return teamTracker.filter(event -> event instanceof EmployeeRemoval);
    return teamTracker.ofType(EmployeeRemoval.class);
  }

  public <T extends EmployeeEvent> Observable<T> getEventsOfType(Class<T> eventType) {
    return teamTracker.ofType(eventType);
  }

  public Observable<EmployeeEvent> getTeamChangesObservable() {
    return teamTracker;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Manager manager = (Manager) o;
    return team.equals(manager.team);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), team);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ",
                            Manager.class.getSimpleName() + "[",
                            "]").add("firstName = " + getFirstName())
                                .add("lastName = " + getLastName())
                                .add("employees=" + team)
                                .toString();
  }

}
