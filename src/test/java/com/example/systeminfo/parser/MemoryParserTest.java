package com.example.systeminfo.parser;

import com.example.systeminfo.model.MemoryInfo;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryParserTest {

    @Test
    void linuxMemoryParserMB() throws Exception {
        Process process = mock(Process.class);
        when(process.getInputStream()).thenReturn(
                new ByteArrayInputStream("MemTotal:       16384 kB\n".getBytes())
        );

        LinuxMemoryParser parser = spy(new LinuxMemoryParser());
        doReturn(process).when(parser).exec(any(String.class));

        MemoryInfo info = parser.parse("MB");
        assertEquals(16, info.totalMb()); // 16384 / 1024 = 16
    }

    @Test
    void windowsMemoryParserGB() throws Exception {
        Process process = mock(Process.class);
        when(process.getInputStream()).thenReturn(
                new ByteArrayInputStream("TotalVisibleMemorySize=16777216\n".getBytes())
        );

        WindowsMemoryParser parser = spy(new WindowsMemoryParser());
        doReturn(process).when(parser).exec(any(String.class));

        MemoryInfo info = parser.parse("GB");
        assertEquals(16, info.totalMb()); // 16 GB
    }
}
