# Lab-01

## Part 1

Open `Employee`. We are going to change this class from being "standard" to 
Observer-based. Update the following:

* `getFirstName` should return an Observable of an appropriate type
* As should `getLastName`
* As should `getSalary`

Open `EmployeeTest`. Write one test for each of the above. 

## Part 2

Working with iterables in our observables, as well as testing them.

Open `Manager`. Update the following:

* Update `getTeam` so it returns an Observable of the individual team members (not a one-element
  Observable wrapped around the List). 
* Open `ManagerTest`. Write a test for:
  * `getTeam` returning an Observable
  * Retrieving at least one item out of the `getTeam` observable to verify it is correct.

### Something to think about
* What happens if a team member is added or removed? Is that reflected in the
  corresponding Observable?
* Is there a way to track whether someone has been added or removed from the team?

## Part 3

Let's watch for changes. Open `Manager`. 

* Implement code such that we can watch when Managers add or remove team members
* Probably want to use a `PublishSubject`
* Tie it to when `addTeamMember` and `removeTeamMember` are called
* Testing
  * How can we test this? Is your tracker private, public, or with a different visibility?
* What should the subscription return? The added/removed Employee? boolean based on success or failure?
