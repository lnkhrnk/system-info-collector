package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SystemInfoCollectorTest {

    @Test
    void mainPrintsValidJson() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        SystemInfoCollector.main(new String[0]);

        String output = out.toString().trim();

        assertTrue(output.startsWith("{"), "Output should be valid JSON");
        assertTrue(output.contains("\"os\""), "JSON must contain os field");
        assertTrue(output.contains("\"cpu\""), "JSON must contain cpu field");
        assertTrue(output.contains("\"memory\""), "JSON must contain memory field");

        System.setOut(original);
    }
}