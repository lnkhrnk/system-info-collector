package com.example.systeminfo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OSDetectorTest {
    @Test
    void detectReturnsWindowsOrLinux() {
        OSDetector detector = new OSDetector();
        String os = detector.detect();
        assertTrue("windows".equals(os) || "linux".equals(os));
    }
}