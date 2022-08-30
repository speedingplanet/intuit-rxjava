package rxjava.lab01;

import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Manager extends Employee {
  private List<Employee> team;

  public Manager(String firstName, String lastName, Integer salary, List<Employee> team) {
    super(firstName, lastName, salary);
    Objects.requireNonNull(team, "Team is null");
    this.team = team;
  }

  public List<Employee> getTeam() {
    return team;
  }

  public Observable<Employee> getTeamAsObservable() {
    return Observable.fromIterable(getTeam());
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
