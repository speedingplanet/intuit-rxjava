package rxjava.lab02;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.*;

public class Manager extends Employee implements ObservableSource<Employee> {
  private List<Employee> team;
  private PublishSubject<Employee> teamTracker;

  public Manager(String firstName, String lastName, Integer salary, List<Employee> team) {
    super(firstName, lastName, salary);
    Objects.requireNonNull(team, "Team is null");
    this.team = team;
    this.teamTracker = PublishSubject.create();
  }

  public Observable<Employee> getTeam() {
    return Observable.fromIterable(team);
  }

  public void subscribe(@NonNull Observer<? super Employee> observer) {
    teamTracker.subscribe(observer);
  }

  public void subscribe(@NonNull Consumer<? super Employee> onNext,
                        @NonNull Consumer<? super Throwable> onError,
                        @NonNull Action onComplete) {
    teamTracker.subscribe(onNext, onError, onComplete);
  }

  public boolean addTeamMember(Employee e) {
    if (!team.contains(e)) {
      teamTracker.onNext(e);
      return team.add(e);
    } else {
      return false;
    }
  }

  public boolean removeTeamMember(Employee e) {
    if (team.contains(e)) {
      teamTracker.onNext(e);
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
                            "]").add("firstName = " + firstName)
                                .add("lastName = " + lastName)
                                .add("employees=" + team)
                                .toString();
  }

}
