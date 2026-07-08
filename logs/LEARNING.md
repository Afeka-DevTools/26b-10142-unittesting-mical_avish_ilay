# LEARNING.md — Self-Learning Conversation on Java Unit Testing

This document records the conversation held with an LLM (Claude) as required by
Part 2 of HW2, in which the team asked the model to explain unit testing in Java
before writing any actual tests.

**Tool used:** Claude (Claude Sonnet 5, via Cowork)
**Topic requested:** Explain unit testing in Java, with broad context and a specific format, ahead of writing unit tests for `App.java` using JUnit + Gradle.

---

## Conversation Transcript

**Prompt to the LLM:**
"HW2: Gradle & Java unit testing. Please explain Java unit testing to me — what it is, what tools/annotations to use with JUnit and Gradle, which Assert methods matter, and how to know if a test suite is sufficient (line coverage vs. branch/path coverage vs. edge cases). This is in preparation for writing unit tests for functions in App.java using AppTest.java."

**LLM response:**

### What is a unit test?
A unit test verifies that a single, isolated piece of code (usually one method or function) behaves correctly for a given input. It's automated, repeatable, and doesn't depend on external systems (databases, network, file system) unless explicitly mocked.

### The tooling: JUnit + Gradle
Gradle projects typically use JUnit (4 or 5/Jupiter — check `build.gradle` for the dependency). Standard project layout:

```
src/main/java/...   → actual code (e.g. App.java)
src/test/java/...   → test code (e.g. AppTest.java)
```

Running `./gradlew test` compiles and executes everything under `src/test/java` and generates a report under `build/reports/tests/`.

### Anatomy of a test class
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

### Key Assert methods
- `assertEquals(expected, actual)` — compares expected vs. actual value
- `assertTrue(condition)` / `assertFalse(condition)` — boolean checks
- `assertNull(obj)` / `assertNotNull(obj)` — null checks
- `assertThrows(Exception.class, () -> {...})` (JUnit 5) or `@Test(expected = Exception.class)` (JUnit 4) — verifies the right exception is thrown
- `assertArrayEquals(expectedArray, actualArray)` — array comparisons

### What makes a test suite "sufficient"?
Two dimensions matter:

- **Line coverage** — did every line of `App.java` execute at least once? Necessary but not sufficient — a line can run without its logic being meaningfully verified.
- **Branch/path coverage** — did every possible branch execute (every `if`/`else`, every loop run 0/1/many times, every `switch` case)? This is the real measure of thoroughness. Example:
  ```java
  public int classify(int x) {
      if (x < 0) return -1;
      else if (x == 0) return 0;
      else return 1;
  }
  ```
  This needs at least 3 tests (negative, zero, positive) for full branch coverage — one test alone gives 100% line coverage of the happy path but only ~33% branch coverage.

**Edge cases to always consider:** boundary values (0, -1, max/min int), null or empty inputs, empty/single-element collections, and any value sitting exactly on a conditional boundary.

### Checking coverage practically
Gradle's JaCoCo plugin can generate coverage reports (`./gradlew test jacocoTestReport`). Without it, the manual equivalent is tracing every `if`/`else`/`for`/`while` in `App.java` and confirming a test forces each branch.

---

**Follow-up question asked:**
"So it doesn't really make sure that your new code works with all the code but just makes sure that the unit that you are going to commit really works?"

**LLM response:**

That's correct. Unit tests deliberately test a single unit in isolation, mocking or ignoring its dependencies. A unit test for `App.add(2,3)` only proves `add` returns the correct result for those inputs — it says nothing about whether `add` is called correctly elsewhere, or whether the whole program works end-to-end.

Verifying that a change doesn't break other parts of the system, or that units work together correctly, is the job of other test types:

- **Integration tests** — check that multiple units (or a unit and a real dependency, like a database) work together correctly.
- **Regression tests** — often just the existing unit test suite, re-run after a change, to confirm previously-working units still work. This works precisely *because* each unit test only cares about its own unit — if `add()`'s test still passes after a change, `add()` itself is still correct, but that doesn't guarantee every caller of `add()` is unaffected unless those call sites are also tested.
- **End-to-end (E2E) tests** — run the whole application as a user would, verifying full workflows.

Since HW2 asks specifically for unit tests on the functions in `App.java`, the goal is narrower than proving the whole program works — it's proving each individual function does exactly what it should, for every meaningful input, in isolation. This is also why branch/edge-case coverage matters so much here: since only one function is tested at a time, thoroughness within that function is the whole game.

---

## Takeaways applied to this assignment
- Each function in `App.java` will get its own set of tests in `AppTest.java`.
- Tests will aim for full branch coverage, not just line coverage (boundary values, zero/negative/positive cases, empty/null inputs where relevant).
- `Assert` methods (`assertEquals`, `assertTrue`, `assertThrows`, etc.) will be used per JUnit conventions.
- Coverage will be manually traced against each function's branches, and JaCoCo used if available in `build.gradle`.
