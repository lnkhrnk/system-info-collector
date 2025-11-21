package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class SystemInfoCollectorTest {

    @Test
    void mainOutputsJson() throws Exception {
        // Просто проверяем, что программа запускается и выводит JSON
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        SystemInfoCollector.main(new String[]{});

        String output = out.toString();
        assertTrue(output.contains("os"));
        assertTrue(output.contains("cpu"));
        assertTrue(output.contains("memory"));

        System.setOut(originalOut);
    }
}