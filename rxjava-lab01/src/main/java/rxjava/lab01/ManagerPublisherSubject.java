package rxjava.lab01;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ManagerPublisherSubject extends Employee {
  private List<Employee> team;
  private PublishSubject<Employee> tracker;

  public ManagerPublisherSubject(String firstName, String lastName, Integer salary, List<Employee> team) {
    super(firstName, lastName, salary);
    Objects.requireNonNull(team, "Team is null");
    this.team = team;
    this.tracker = PublishSubject.create();
  }

  // public List<Employee> getTeam() {
  //   return team;
  // }
  public Observable<Employee> getTeam() {
    return Observable.fromIterable(team);
    // return Observable.just(team);
  }

  public boolean addTeamMember(Employee e) {
    if (!team.contains(e)) {
      tracker.onNext(e);
      return team.add(e);
    } else {
      return false;
    }
  }

  public boolean removeTeamMember(Employee e) {
    if (team.contains(e)) {
      tracker.onNext(e);
      return team.remove(e);
    } else {
      return false;
    }
  }

  public void subscribe(@NonNull Consumer<? super Employee> onNext, @NonNull Consumer<?
    super Throwable> onError, @NonNull Action onComplete) {
    tracker.subscribe(onNext, onError, onComplete);
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
    ManagerPublisherSubject manager = (ManagerPublisherSubject) o;
    return team.equals(manager.team);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), team);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ",
                            ManagerPublisherSubject.class.getSimpleName() + "[",
                            "]").add("firstName = " + getFirstName())
                                .add("lastName = " + getLastName())
                                .add("employees=" + team)
                                .toString();
  }

  TestObserver<Employee> test() {
    return tracker.test();
  }
}
