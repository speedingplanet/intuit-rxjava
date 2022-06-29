package rxjava.lab01.solutions;

import io.reactivex.rxjava3.core.Observable;
import rxjava.lab01.Employee;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class SolutionManager extends Employee {
  private List<Employee> team;

  public SolutionManager(String firstName, String lastName, Integer salary, List<Employee> team) {
    super(firstName, lastName, salary);
    Objects.requireNonNull(team, "Team is null");
    this.team = team;
  }

  public List<Employee> getTeamOriginal() {
    return team;
  }
  public Observable<Employee> getTeam() {
    return Observable.fromIterable(team);
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
    SolutionManager manager = (SolutionManager) o;
    return team.equals(manager.team);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), team);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ",
                            SolutionManager.class.getSimpleName() + "[",
                            "]").add("firstName = " + getFirstName())
                                .add("lastName = " + getLastName())
                                .add("employees=" + team)
                                .toString();
  }

}
