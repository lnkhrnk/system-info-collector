package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SystemInfoCollectorTest {

    @Test
    void mainPrintsValidJsonToStdout() {
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(capturedOutput));
            SystemInfoCollector.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String output = capturedOutput.toString().trim();

        assertFalse(output.isEmpty(), "Output should not be empty");
        assertTrue(output.startsWith("{"), "Output should be valid JSON object");
        assertTrue(output.contains("\"os\""), "JSON must contain 'os' field");
        assertTrue(output.contains("\"cpu\""), "JSON must contain 'cpu' field");
        assertTrue(output.contains("\"memory\""), "JSON must contain 'memory' field");
    }
}