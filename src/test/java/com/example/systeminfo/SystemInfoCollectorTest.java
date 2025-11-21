package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SystemInfoCollectorTest {

    @Test
    void mainPrintsJson() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));

        // Теперь main() без throws Exception → тест компилируется
        SystemInfoCollector.main(new String[0]);

        String output = baos.toString();
        assertTrue(output.contains("os"));
        assertTrue(output.contains("cpu"));
        assertTrue(output.contains("memory"));
        assertTrue(output.contains("{"));

        System.setOut(original);
    }
}