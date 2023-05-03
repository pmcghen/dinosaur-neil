package com.example.dinosaurneil.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StringToLocalDateTimeTest {
    @Test
    @DisplayName("Convert string to LocalDateTime")
    void convertStringToDateTime() {
        assertEquals(
                LocalDateTime.of(2023, 3, 25, 8, 0, 0),
                StringToLocalDateTime.convertStringToDateTime("Sat, 25 Mar 2023 08:00:00 +0000")
        );
        assertEquals(
                LocalDateTime.of(2023, 4, 21, 0, 0, 0),
                StringToLocalDateTime.convertStringToDateTime("Fri, 21 Apr 2023 00:00:00 +0000")
        );
        assertEquals(
                LocalDateTime.of(2023, 3, 29, 13, 0, 0),
                StringToLocalDateTime.convertStringToDateTime("Wed, 29 Mar 2023 13:00:00 -0400")
        );
    }
}