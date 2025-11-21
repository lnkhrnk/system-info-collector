package com.example.systeminfo.parser;

import com.example.systeminfo.model.OsInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OsParserTest {

    @Test
    void linuxOsParser() {
        LinuxOsParser parser = new LinuxOsParser();
        OsInfo info = parser.parse();
        assertNotNull(info.name());
        assertTrue(info.name().contains("Linux") || info.name().contains("Ubuntu"));
    }

    @Test
    void windowsOsParser() {
        WindowsOsParser parser = new WindowsOsParser();
        OsInfo info = parser.parse();
        assertEquals("Windows", info.name());
        assertNotNull(info.version());
    }
}