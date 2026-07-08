# Chat Transcript — Part 2: Learning About Java Unit Testing

**Tool:** Claude (Claude Sonnet 5)
**Purpose:** Self-learning conversation requested by HW2 Part 2, ahead of writing actual unit tests.

---

**User:**
> HW2: Gradle & Java unit testing. Please explain Java unit testing to me — what it is, what tools/annotations to use with JUnit and Gradle, which Assert methods matter, and how to know if a test suite is sufficient (line coverage vs. branch/path coverage vs. edge cases). This is in preparation for writing unit tests for functions in App.java using AppTest.java.

**Claude:**

**What is a unit test?**
A unit test verifies that a single, isolated piece of code (usually one method or function) behaves correctly for a given input. It's automated, repeatable, and doesn't depend on external systems (databases, network, file system) unless explicitly mocked.

**The tooling: JUnit + Gradle**
Gradle projects typically use JUnit (4 or 5/Jupiter — check `build.gradle` for the dependency). Standard project layout:

```
src/main/java/...   → actual code (e.g. App.java)
src/test/java/...   → test code (e.g. AppTest.java)
```

Running `./gradlew test` compiles and executes everything under `src/test/java` and generates a report under `build/reports/tests/`.

**Anatomy of a test class**
```java
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testAddPositiveNumbers() {
        App app = new App();
        assertEquals(5, app.add(2, 3));
    }
}
```
`@Test` marks a method as a test case. Tests should be independent of each other — order shouldn't matter, and one failing test shouldn't cascade into others failing.

**Key Assert methods**
- `assertEquals(expected, actual)` — compares expected vs. actual value
- `assertTrue(condition)` / `assertFalse(condition)` — boolean checks
- `assertNull(obj)` / `assertNotNull(obj)` — null checks
- `assertThrows(Exception.class, () -> {...})` (JUnit 5) or `@Test(expected = Exception.class)` (JUnit 4) — verifies the right exception is thrown
- `assertArrayEquals(expectedArray, actualArray)` — array comparisons

**What makes a test suite "sufficient"?**
Two dimensions matter:
- **Line coverage** — did every line execute at least once? Necessary but not sufficient.
- **Branch/path coverage** — did every possible branch execute (every if/else, every loop run 0/1/many times, every switch case)? Example:
  ```java
  public int classify(int x) {
      if (x < 0) return -1;
      else if (x == 0) return 0;
      else return 1;
  }
  ```
  This needs at least 3 tests (negative, zero, positive) for full branch coverage.

**Edge cases to always consider:** boundary values (0, -1, max/min int), null or empty inputs, empty/single-element collections, and any value sitting exactly on a conditional boundary.

**Checking coverage practically:** Gradle's JaCoCo plugin can generate coverage reports (`./gradlew test jacocoTestReport`). Without it, the manual equivalent is tracing every if/else/for/while and confirming a test forces each branch.

---

**User:**
> So it doesn't really make sure that your new code works with all the code but just makes sure that the unit that you are going to commit really works?

**Claude:**

That's correct. Unit tests deliberately test a single unit in isolation, mocking or ignoring its dependencies. A unit test for `App.add(2,3)` only proves `add` returns the correct result for those inputs — it says nothing about whether `add` is called correctly elsewhere, or whether the whole program works end-to-end.

Verifying that a change doesn't break other parts of the system, or that units work together correctly, is the job of other test types:
- **Integration tests** — check that multiple units (or a unit and a real dependency) work together correctly.
- **Regression tests** — often just the existing unit test suite, re-run after a change, to confirm previously-working units still work.
- **End-to-end (E2E) tests** — run the whole application as a user would, verifying full workflows.

Since HW2 asks specifically for unit tests on the functions in `App.java`, the goal is narrower than proving the whole program works — it's proving each individual function does exactly what it should, for every meaningful input, in isolation.

---

**User:**
> yup you can save it! I think I understand

**Claude:** Saved the above as `logs/LEARNING.md`.
