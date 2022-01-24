package rxjava.lab02;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

import java.util.*;

public class Manager extends Employee implements ObservableSource<Employee> {
  private List<Employee> team;
  private Observer<? super Employee> observer;

  public Manager(String firstName, String lastName, Integer salary, List<Employee> team) {
    super(firstName, lastName, salary);
    Objects.requireNonNull(team, "Team is null");
    this.team = team;
  }

  // public List<Employee> getTeam() {
  //   return team;
  // }

  public Observable<Employee> getTeam() {
    return Observable.fromIterable(team);
    // return Observable.just(team);
  }

  @Override
  public void subscribe(@NonNull Observer<? super Employee> observer) {
    this.observer = observer;
  }

  public boolean addTeamMember(Employee e) {
    if (!team.contains(e)) {
      observer.onNext(e);
      return team.add(e);
    } else {
      return false;
    }
  }

  public boolean removeTeamMember(Employee e) {
    if (team.contains(e)) {
      observer.onNext(e);
      return team.remove(e);
    } else {
      return false;
    }
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
