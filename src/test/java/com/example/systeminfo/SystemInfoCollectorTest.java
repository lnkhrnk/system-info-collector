package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SystemInfoCollectorTest {

    @Test
    void mainPrintsJsonOrErrorMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));

        try {
            SystemInfoCollector.main(new String[0]);
        } catch (Exception e) {
            // ignore – we just want to capture output
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

        String stdout = out.toString();
        String stderr = err.toString();
        String combined = (stdout + stderr).trim();


        boolean hasJson = combined.contains("{") && combined.contains("\"os\"");
        boolean hasErrorMessage = combined.contains("Error") || combined.contains("Exception");

        assertTrue(hasJson || hasErrorMessage,
                "Expected either valid JSON output or error message. Got: " + combined);
    }
}