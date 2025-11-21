package com.example.systeminfo.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemoryParserTest {
    @Test
    void factoryReturnsCorrectImplementation() {
        assertTrue(MemoryParser.forOS("windows") instanceof WindowsMemoryParser);
        assertTrue(MemoryParser.forOS("linux") instanceof LinuxMemoryParser);
    }
}