package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class SystemInfoCollectorTest {
    @Test
    void mainPrintsJsonWithAllFields() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        SystemInfoCollector.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("\"os\""));
        assertTrue(output.contains("\"cpu\""));
        assertTrue(output.contains("\"memory\""));

        System.setOut(originalOut);
    }
}