# DevTools HW2 — Gradle & Java Unit Testing

## Team Members

- Mical
- Avish
- Ilay

## Project Overview

This project contains a small Java utility class (`App.java`) with unit
tests (`AppTest.java`) written using JUnit 5 (Jupiter), built with Gradle.

An LLM (Claude) was used both to learn about Java unit testing and to help
write the actual test suite. Those conversations are documented in full:

- `chats/learning_conversation.md` — raw transcript of the self-learning
  conversation about Java unit testing (Part 2 of the assignment).
- `chats/copilot_conversation.md` — raw transcript of the conversation used
  to write `AppTest.java` and evaluate test sufficiency (Part 3).
- `logs/LEARNING.md` — summarized writeup of the Part 2 conversation.
- `logs/COPILOT.md` — summarized writeup of the Part 3 conversation,
  including a real bug discovered in `App.java` (`mostCommonWord` throws an
  unhandled exception on input with no word characters) and a full
  coverage summary per method.

## Requirements

- Java 21 (matches the toolchain configured in `app/build.gradle.kts`)
- No local Gradle installation needed — this project uses the Gradle
  wrapper (`gradlew` / `gradlew.bat`), which downloads the correct Gradle
  version automatically.

## How to Clone and Run the Tests

1. Clone the repository:
   ```bash
   git clone <this-repository-url>
   cd <repository-folder>
   ```

2. Run the test suite using the Gradle wrapper:

   On macOS/Linux:
   ```bash
   ./gradlew test
   ```

   On Windows:
   ```bash
   gradlew.bat test
   ```

3. View results:
   - A summary prints directly in the terminal.
   - A full HTML report is generated at:
     `app/build/reports/tests/test/index.html`
     Open this file in a browser for a detailed, clickable breakdown of
     every test.

No additional input or configuration is required — the test suite runs
standalone against `App.java` with no external dependencies (network,
database, etc.).

## What's Tested

`AppTest.java` covers all 11 methods in `App.java`, organized into
`@Nested` test classes per method, targeting full branch/path coverage
(not just line coverage): every `if`/`else` branch, every loop's
zero/one/many-iteration cases, every thrown exception, and relevant edge
cases (empty inputs, boundary values, negative numbers, etc.) for each
method. See `logs/COPILOT.md` for the full per-method coverage summary and
notes on a bug discovered in `mostCommonWord` during testing.
