package com.example.systeminfo.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OsParserTest {
    @Test
    void factoryReturnsCorrectImplementation() {
        assertTrue(OsParser.forOS("windows") instanceof WindowsOsParser);
        assertTrue(OsParser.forOS("linux") instanceof LinuxOsParser);
    }
}