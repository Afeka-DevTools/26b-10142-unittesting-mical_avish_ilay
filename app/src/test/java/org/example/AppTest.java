package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link App}.
 *
 * Each nested class covers one method of App, aiming for full branch/path
 * coverage (not just line coverage): every if/else branch, every loop
 * boundary (0 iterations, 1 iteration, many iterations), and every
 * documented exception path is exercised at least once.
 */
class AppTest {

    @Nested
    @DisplayName("add()")
    class AddTests {
        @Test
        void addsTwoPositiveNumbers() {
            assertEquals(5, App.add(2, 3));
        }

        @Test
        void addsPositiveAndNegative() {
            assertEquals(1, App.add(-2, 3));
        }

        @Test
        void addsTwoNegativeNumbers() {
            assertEquals(-5, App.add(-2, -3));
        }

        @Test
        void addsZeros() {
            assertEquals(0, App.add(0, 0));
        }
    }

    @Nested
    @DisplayName("isPrime()")
    class IsPrimeTests {
        @Test
        void negativeNumbersAreNotPrime() {
            assertFalse(App.isPrime(-5));
        }

        @Test
        void zeroIsNotPrime() {
            assertFalse(App.isPrime(0));
        }

        @Test
        void oneIsNotPrime() {
            // n < 2 branch
            assertFalse(App.isPrime(1));
        }

        @Test
        void twoIsPrime() {
            // smallest prime; loop condition i <= sqrt(n) never true (boundary)
            assertTrue(App.isPrime(2));
        }

        @Test
        void threeIsPrime() {
            assertTrue(App.isPrime(3));
        }

        @Test
        void fourIsNotPrime_loopFindsDivisorImmediately() {
            assertFalse(App.isPrime(4));
        }

        @Test
        void nineIsNotPrime_loopFindsDivisorAfterIterating() {
            assertFalse(App.isPrime(9));
        }

        @Test
        void seventeenIsPrime_loopCompletesWithoutDivisor() {
            assertTrue(App.isPrime(17));
        }
    }

    @Nested
    @DisplayName("reverse()")
    class ReverseTests {
        @Test
        void reversesARegularString() {
            assertEquals("olleh", App.reverse("hello"));
        }

        @Test
        void reversingEmptyStringGivesEmptyString() {
            assertEquals("", App.reverse(""));
        }

        @Test
        void reversingSingleCharGivesSameChar() {
            assertEquals("a", App.reverse("a"));
        }

        @Test
        void nullInputThrowsNullPointerException() {
            assertThrows(NullPointerException.class, () -> App.reverse(null));
        }
    }

    @Nested
    @DisplayName("factorial()")
    class FactorialTests {
        @Test
        void negativeNumberThrowsIllegalArgumentException() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> App.factorial(-1));
            assertEquals("Negative number", ex.getMessage());
        }

        @Test
        void factorialOfZeroIsOne_loopDoesNotExecute() {
            assertEquals(1, App.factorial(0));
        }

        @Test
        void factorialOfOneIsOne_loopDoesNotExecute() {
            assertEquals(1, App.factorial(1));
        }

        @Test
        void factorialOfFive() {
            assertEquals(120, App.factorial(5));
        }

        @Test
        void factorialOfTen() {
            assertEquals(3628800, App.factorial(10));
        }
    }

    @Nested
    @DisplayName("isPalindrome()")
    class IsPalindromeTests {
        @Test
        void simplePalindromeIsTrue() {
            assertTrue(App.isPalindrome("racecar"));
        }

        @Test
        void nonPalindromeIsFalse() {
            assertFalse(App.isPalindrome("hello"));
        }

        @Test
        void palindromeWithSpacesCaseAndPunctuationIsTrue() {
            assertTrue(App.isPalindrome("A man a plan a canal Panama"));
        }

        @Test
        void numericPalindromeIsTrue() {
            assertTrue(App.isPalindrome("12321"));
        }

        @Test
        void numericNonPalindromeIsFalse() {
            assertFalse(App.isPalindrome("12345"));
        }

        @Test
        void singleCharIsPalindrome() {
            assertTrue(App.isPalindrome("a"));
        }

        @Test
        void stringWithNoAlphanumericCharsIsTreatedAsPalindrome() {
            // Edge case: after stripping non-alphanumeric chars, "!!!" becomes
            // the empty string, and "" equals its own reverse -> true.
            // This is verified real behavior, not assumed.
            assertTrue(App.isPalindrome("!!!"));
        }
    }

    @Nested
    @DisplayName("fibonacciUpTo()")
    class FibonacciUpToTests {
        @Test
        void negativeInputThrowsIllegalArgumentException() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> App.fibonacciUpTo(-1));
            assertEquals("Negative input", ex.getMessage());
        }

        @Test
        void zeroReturnsOnlyZero() {
            assertEquals(List.of(0), App.fibonacciUpTo(0));
        }

        @Test
        void oneIncludesBothOnesInSequence() {
            // Verified real behavior: fib sequence has two consecutive 1's
            // (F1=1, F2=1) and both are <= 1, so both are included.
            assertEquals(List.of(0, 1, 1), App.fibonacciUpTo(1));
        }

        @Test
        void fiveReturnsSequenceUpToFive() {
            assertEquals(List.of(0, 1, 1, 2, 3, 5), App.fibonacciUpTo(5));
        }

        @Test
        void tenReturnsSequenceUpToTen() {
            assertEquals(List.of(0, 1, 1, 2, 3, 5, 8), App.fibonacciUpTo(10));
        }
    }

    @Nested
    @DisplayName("charFrequency()")
    class CharFrequencyTests {
        @Test
        void countsRepeatedCharacters() {
            Map<Character, Integer> result = App.charFrequency("aabbc");
            assertEquals(2, result.get('a'));
            assertEquals(2, result.get('b'));
            assertEquals(1, result.get('c'));
            assertEquals(3, result.size());
        }

        @Test
        void emptyStringGivesEmptyMap() {
            assertTrue(App.charFrequency("").isEmpty());
        }

        @Test
        void isCaseSensitiveAndCountsSpaces() {
            Map<Character, Integer> result = App.charFrequency("A a");
            assertEquals(1, result.get('A'));
            assertEquals(1, result.get('a'));
            assertEquals(1, result.get(' '));
            assertEquals(3, result.size());
        }
    }

    @Nested
    @DisplayName("isAnagram()")
    class IsAnagramTests {
        @Test
        void simpleAnagramIsTrue() {
            assertTrue(App.isAnagram("listen", "silent"));
        }

        @Test
        void anagramIgnoringCaseAndSpacesIsTrue() {
            assertTrue(App.isAnagram("Listen", "Silent "));
        }

        @Test
        void differentWordsAreNotAnagrams() {
            assertFalse(App.isAnagram("hello", "world"));
        }

        @Test
        void bothEmptyStringsAreAnagrams() {
            assertTrue(App.isAnagram("", ""));
        }

        @Test
        void differentLengthStringsAreNotAnagrams() {
            assertFalse(App.isAnagram("a", "ab"));
        }
    }

    @Nested
    @DisplayName("average()")
    class AverageTests {
        private static final double DELTA = 0.0001;

        @Test
        void emptyArrayThrowsIllegalArgumentException() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> App.average(new int[]{}));
            assertEquals("Empty array", ex.getMessage());
        }

        @Test
        void singleElementArrayAverageIsThatElement() {
            assertEquals(5.0, App.average(new int[]{5}), DELTA);
        }

        @Test
        void averageOfWholeNumbersDivisibleEvenly() {
            assertEquals(2.0, App.average(new int[]{1, 2, 3}), DELTA);
        }

        @Test
        void averageThatProducesADecimalResult() {
            assertEquals(1.5, App.average(new int[]{1, 2}), DELTA);
        }

        @Test
        void averageOfNegativeNumbers() {
            assertEquals(-2.0, App.average(new int[]{-1, -2, -3}), DELTA);
        }
    }

    @Nested
    @DisplayName("filterEvens()")
    class FilterEvensTests {
        @Test
        void filtersEvensOutOfMixedList() {
            assertEquals(List.of(2, 4, 6), App.filterEvens(Arrays.asList(1, 2, 3, 4, 5, 6)));
        }

        @Test
        void emptyListGivesEmptyList() {
            assertTrue(App.filterEvens(Collections.emptyList()).isEmpty());
        }

        @Test
        void allOddsGivesEmptyList() {
            assertTrue(App.filterEvens(Arrays.asList(1, 3, 5)).isEmpty());
        }

        @Test
        void negativeEvensAndZeroAreIncluded() {
            // Verified real behavior: Java's % operator correctly identifies
            // negative even numbers (-4 % 2 == 0) and zero as even.
            assertEquals(List.of(-4, -2, 0), App.filterEvens(Arrays.asList(-4, -3, -2, -1, 0)));
        }
    }

    @Nested
    @DisplayName("mostCommonWord()")
    class MostCommonWordTests {
        @Test
        void findsClearWinnerAmongMultipleWords() {
            assertEquals("the", App.mostCommonWord("the quick brown fox the quick fox the"));
        }

        @Test
        void singleWordIsItsOwnMostCommon() {
            assertEquals("hello", App.mostCommonWord("hello"));
        }

        @Test
        void isCaseInsensitiveAndStripsPunctuation() {
            assertEquals("hello", App.mostCommonWord("Hello, hello! HELLO."));
        }

        @Test
        void emptyStringReturnsEmptyWord() {
            // Verified real behavior: "".split("\\W+") yields a single-element
            // array containing "", so the map has one entry: "" -> 1.
            assertEquals("", App.mostCommonWord(""));
        }

        @Test
        void textWithNoWordCharactersThrowsNoSuchElementException() {
            // Discovered edge case / bug: when the input contains no word
            // characters at all (e.g. only punctuation), String.split("\\W+")
            // returns a zero-length array, so the frequency map stays empty
            // and Collections.max() throws NoSuchElementException instead of
            // App handling this case gracefully. Verified against a real JDK.
            assertThrows(java.util.NoSuchElementException.class,
                () -> App.mostCommonWord("!!!"));
        }
    }
}
