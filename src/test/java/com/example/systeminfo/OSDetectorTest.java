package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OSDetectorTest {

    @Test
    void detectWindows() {
        OSDetector detector = new OSDetector() {
            @Override
            public String detect() {
                return System.setProperty("os.name", "Windows 10") == null ? "windows" : super.detect();
            }
        };
        // Лучше через рефлексию или мок, но для простоты:
        assertEquals("windows", new OSDetector().detect()); // если ты на Windows
        // Или проверяем логику:
        String os = System.getProperty("os.name").toLowerCase();
        String expected = os.contains("win") ? "windows" : "linux";
        assertEquals(expected, new OSDetector().detect());
    }
}