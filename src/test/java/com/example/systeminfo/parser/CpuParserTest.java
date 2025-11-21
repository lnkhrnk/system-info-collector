package com.example.systeminfo.parser;

import com.example.systeminfo.model.CpuInfo;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CpuParserTest {

    @Test
    void linuxCpuParser() throws Exception {
        Process process = mock(Process.class);
        InputStream inputStream = new ByteArrayInputStream(
                "Model name:          Intel(R) Core(TM) i7-10750H\nCPU(s):              12\n".getBytes()
        );
        when(process.getInputStream()).thenReturn(inputStream);
        when(process.waitFor()).thenReturn(0);

        LinuxCpuParser parser = spy(new LinuxCpuParser());
        doReturn(process).when(parser).exec(any(String.class));

        CpuInfo info = parser.parse();
        assertEquals("Intel(R) Core(TM) i7-10750H", info.model());
        assertEquals(12, info.cores());
    }

    @Test
    void windowsCpuParser() throws Exception {
        Process process = mock(Process.class);
        InputStream inputStream = new ByteArrayInputStream(
                "Name=Intel(R) Core(TM) i7-10750H\nNumberOfCores=12\n".getBytes()
        );
        when(process.getInputStream()).thenReturn(inputStream);

        WindowsCpuParser parser = spy(new WindowsCpuParser());
        doReturn(process).when(parser).exec(any(String.class));

        CpuInfo info = parser.parse();
        assertEquals("Intel(R) Core(TM) i7-10750H", info.model());
        assertEquals(12, info.cores());
    }
}
