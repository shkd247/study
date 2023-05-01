package com.example.effective_software_testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NumberUtilsTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void shouldReturnCorrectResult(List<Integer> left, List<Integer> right, List<Integer> expected) throws IllegalAccessException {
        assertThat(new NumberUtils().add(left, right)).isEqualTo(expected);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(null, numbers(8, 2), null), // T1
                Arguments.of(numbers(), numbers(7, 2), numbers(7, 2)), // T2
                Arguments.of(numbers(9, 8), null, null), // T3
                Arguments.of(numbers(9, 8), numbers(), numbers(9, 8)), // T4

                Arguments.of(numbers(1), numbers(2), numbers(3)), // T5
                Arguments.of(numbers(9), numbers(2), numbers(1, 1)), // T6

                Arguments.of(numbers(2, 2), numbers(3, 3), numbers(5, 5)), // T7
                Arguments.of(numbers(2, 9), numbers(2, 3), numbers(5, 2)), // T8
                Arguments.of(numbers(2, 9, 3), numbers(1, 8, 3), numbers(4, 7, 6)), // T9
                Arguments.of(numbers(1, 7, 9), numbers(2, 6, 8), numbers(4, 4, 7)), // T10
                Arguments.of(numbers(1, 9, 1, 7, 1), numbers(1, 8, 1, 6, 1), numbers(3, 7, 3, 3, 2)), // T11
                Arguments.of(numbers(9, 9, 8), numbers(1, 7, 2), numbers(1, 1, 7, 0)), // T12

                Arguments.of(numbers(2, 2), numbers(3), numbers(2, 5)), // T13.1
                Arguments.of(numbers(3), numbers(2, 2), numbers(2, 5)), // T13.2
                Arguments.of(numbers(2, 2), numbers(9), numbers(3, 1)), // T14.1
                Arguments.of(numbers(9), numbers(2, 2), numbers(3, 1)), // T14.2
                Arguments.of(numbers(1, 7, 3), numbers(9, 2), numbers(2, 6, 5)), // T15.1
                Arguments.of(numbers(9, 2), numbers(1, 7, 3), numbers(2, 6, 5)), // T15.2
                Arguments.of(numbers(3, 1, 7, 9), numbers(2, 6, 8), numbers(3, 4, 4, 7)), // T16.1
                Arguments.of(numbers(2, 6, 8), numbers(3, 1, 7, 9), numbers(3, 4, 4, 7)), // T16.2
                Arguments.of(numbers(1, 9, 1, 7, 1), numbers(2, 1, 8, 1, 6, 1), numbers(2, 3, 7, 3, 3, 2)), // T17.1
                Arguments.of(numbers(2, 1, 8, 1, 6, 1), numbers(1, 9, 1, 7, 1), numbers(2, 3, 7, 3, 3, 2)), // T17.2
                Arguments.of(numbers(9, 9, 8), numbers(9, 1, 7, 2), numbers(1, 0, 1, 7, 0)), // T18.1
                Arguments.of(numbers(9, 1, 7, 2), numbers(9, 9, 8), numbers(1, 0, 1, 7, 0)), // T18.2

                Arguments.of(numbers(0, 0, 0, 1, 2), numbers(0, 2, 3), numbers(3, 5)), // T19
                Arguments.of(numbers(0, 0, 0, 1, 2), numbers(0, 2, 9), numbers(4, 1)), // T20

                Arguments.of(numbers(9, 9), numbers(1), numbers(1, 0, 0)) // T21
        );
    }

@ParameterizedTest
@MethodSource("digitsOutOfRange")
void shouldThrowExceptionWhenDifitsAreOutOfRange(List<Integer> left, List<Integer> right, List<Integer> expected) {
    assertThatThrownBy(() -> new NumberUtils().add(left, right)).isInstanceOf(IllegalArgumentException.class);
}

static Stream<Arguments> digitsOutOfRange() {
    return Stream.of(
            Arguments.of(numbers(1, -1, 1), numbers(1)),
            Arguments.of(numbers(1), numbers(1, -1, 1)),
            Arguments.of(numbers(1, 10, 1), numbers(1)),
            Arguments.of(numbers(1), numbers(1, 11, 1))
    );
}

    private static List<Integer> numbers(int... nums) {
        List<Integer> list = new ArrayList<>();
        for(int n : nums)
            list.add(n);
        return list;
    }
}