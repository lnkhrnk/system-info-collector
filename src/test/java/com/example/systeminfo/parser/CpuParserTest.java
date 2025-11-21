package com.example.systeminfo.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CpuParserTest {
    @Test
    void factoryReturnsCorrectImplementation() {
        assertTrue(CpuParser.forOS("windows") instanceof WindowsCpuParser);
        assertTrue(CpuParser.forOS("linux") instanceof LinuxCpuParser);
    }
}