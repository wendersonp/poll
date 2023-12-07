package com.personal.poll.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;

public class AssertUtils {

    @SneakyThrows
    public static <T> void assertAllFields(T expected, T actual) {
        var fields = expected.getClass().getDeclaredFields();

        for (var field: fields) {
            field.setAccessible(true);

            var expectedValue = field.get(expected);
            var actualValue = field.get(actual);
            Assertions.assertEquals(expectedValue, actualValue);

            field.setAccessible(false);
        }
    }
}
