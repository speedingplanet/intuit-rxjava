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

* Update `getTeam` so it returns an Observable of the individual team members 
  * That is, it returns `Observable<Employee>` **NOT** `Observable<List<Employee>>`
* Open `ManagerTest`. Write a test for:
  * Retrieving at least one item out of the `getTeam` Observable to verify it is correct.

### Something to think about
* What happens if a team member is added or removed? Is that reflected in the
  corresponding Observable?
* Is there a way to track whether someone has been added or removed from the team?

## Part 3

Use TestObserver to write better tests for Manager. Specifically:
* Test whether the `getTeam` Observable completed
* Test whether the `getTeam` Observable had any errors
* Test whether the Observable returned the right values in the right order

## Part 4

Let's watch for changes. Open `Manager`. 

* Implement code such that we can watch when Managers add or remove team members
* Probably want to use a `PublishSubject`
* Tie it to when `addTeamMember` and `removeTeamMember` are called
* Testing
  * How can we test this? Is your tracker private, public, or with a different visibility?
* What should the subscription return? The added/removed Employee? boolean based on success or failure?
