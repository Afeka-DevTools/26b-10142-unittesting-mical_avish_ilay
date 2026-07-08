# Chat Transcript — Part 3: Writing AppTest.java

**Tool:** Claude (Claude Sonnet 5, with shell access to compile/run code)
**Purpose:** In-workspace conversation requested by HW2 Part 3, to write unit tests for `App.java` and verify their sufficiency.

---

**User:** shared the full contents of `App.java` (11 static methods: `add`, `isPrime`, `reverse`, `factorial`, `isPalindrome`, `fibonacciUpTo`, `charFrequency`, `isAnagram`, `average`, `filterEvens`, `mostCommonWord`).

**Claude:** Before writing any assertions, downloaded a portable JDK 21 and the JUnit Platform Console Standalone jar into a scratch environment, compiled `App.java`, and ran probe programs to observe the actual runtime behavior of every method rather than guessing from reading the code. This surfaced several important details:

- `fibonacciUpTo(1)` returns `[0, 1, 1]`, not `[0, 1]` — the loop condition `a <= n` includes both `1`s in the Fibonacci sequence when `n=1`.
- `isPalindrome("!!!")` returns `true` — stripping non-alphanumeric chars leaves an empty string, and `"" == reverse("")`.
- `reverse(null)` throws `NullPointerException` from `new StringBuilder(null)`.
- **Discovered bug:** `mostCommonWord("!!!")` throws an unhandled `NoSuchElementException`. Root cause: `"!!!".split("\\W+")` returns a **zero-length array** (confirmed empirically), so the frequency map stays empty and `Collections.max()` on an empty entry set throws. By contrast, `mostCommonWord("")` does *not* throw, because `"".split("\\W+")` returns a one-element array containing `""`.

**User:** shared `build.gradle.kts`, confirming JUnit Jupiter (JUnit 5) with a Java 21 toolchain, and the existing `AppTest.java` stub (which called `classUnderTest.getGreeting()` — a method that doesn't exist on the real `App.java`, left over from the Gradle template).

**Claude:** Deleted the obsolete stub and wrote a full `AppTest.java`, organized with `@Nested` classes per method, targeting full branch/path coverage rather than just line coverage — for example:
- `isPrime`: tests for the `n < 2` false-branch, the smallest prime boundary (2, where the loop never executes), a composite found immediately (4) vs. after several iterations (9), and a larger prime where the loop runs to completion (17).
- `factorial`: the negative-input exception branch, plus zero-iteration cases (n=0, n=1) and multi-iteration cases.
- `filterEvens`: empty list, all-odd list, and negative evens + zero (to confirm Java's `%` behaves as expected for negative operands).
- `mostCommonWord`: a clear-winner case, single word, case-insensitivity + punctuation stripping, the empty-string edge case, and an explicit test asserting the discovered `NoSuchElementException` bug.

**User (implicit, via assignment requirement):** "How do you know the tests are sufficient? How do you check all paths are tested?"

**Claude:** Line coverage alone isn't enough — every if/else branch, every loop's 0/1/many-iteration cases, and every thrown exception path needs its own test. For branchless methods like `add`, coverage instead means testing distinct equivalence classes of input (positive+positive, positive+negative, negative+negative, zero). For `mostCommonWord`, tie-breaking between equally-frequent words was deliberately *not* asserted to a specific outcome, since `Collections.max` on a tie depends on `HashMap` iteration order — an implementation detail, not guaranteed behavior. Test inputs were chosen to have an unambiguous winner instead, to avoid a fragile test.

**Claude (verification):** Compiled `AppTest.java` against the real `App.java` using JUnit 5 and ran the full suite via the JUnit Platform Console launcher.

Result:
```
55 tests found, 55 successful, 0 failed
```

Confirms the tests compile against the project's actual JUnit Jupiter + Java 21 setup and pass against the unmodified `App.java`.

**Claude:** Saved this conversation as `logs/COPILOT.md`.
