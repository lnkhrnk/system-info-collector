package com.example.systeminfo.parser;

import com.example.systeminfo.model.MemoryInfo;
import java.io.*;

/**
 * Interface defining the contract for retrieving Memory (RAM) information.
 */
public interface MemoryParser {
    /**
     * Executes a system command to fetch total memory.
     *
     * @param unit The desired output unit (e.g., "MB" or "GB").
     * @return MemoryInfo object containing the total memory size.
     * @throws IOException If an I/O error occurs.
     */
    MemoryInfo parse(String unit) throws IOException;

    /**
     * Factory method to return the correct parser implementation based on the OS.
     *
     * @param os The OS identifier ("windows" or "linux").
     * @return An instance of WindowsMemoryParser or LinuxMemoryParser.
     */
    static MemoryParser forOS(String os) {
        return os.equals("windows") ? new WindowsMemoryParser() : new LinuxMemoryParser();
    }
}

/**
 * Implementation of MemoryParser for Linux systems.
 * reads from '/proc/meminfo'.
 */
class LinuxMemoryParser implements MemoryParser {
    @Override
    public MemoryInfo parse(String unit) throws IOException {
        Process p = Runtime.getRuntime().exec("cat /proc/meminfo");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            long totalKb = 0;
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("MemTotal:")) {
                    // Extract digits from the string
                    totalKb = Long.parseLong(line.split("\\s+")[1]);
                    break;
                }
            }
            long totalMb = totalKb / 1024;
            long result = unit.equalsIgnoreCase("GB") ? totalMb / 1024 : totalMb;
            return new MemoryInfo(result);
        }
    }
}

/**
 * Implementation of MemoryParser for Windows systems.
 * Uses 'wmic OS get TotalVisibleMemorySize'.
 */
class WindowsMemoryParser implements MemoryParser {
    @Override
    public MemoryInfo parse(String unit) throws IOException {
        // TotalVisibleMemorySize returns value in Kilobytes
        Process p = Runtime.getRuntime().exec("wmic OS get TotalVisibleMemorySize /format:list");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            long totalKb = 0;
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("TotalVisibleMemorySize=")) {
                    totalKb = Long.parseLong(line.substring(23).trim());
                    break;
                }
            }
            long totalMb = totalKb / 1024;
            long result = unit.equalsIgnoreCase("GB") ? totalMb / 1024 : totalMb;
            return new MemoryInfo(result);
        }
    }
}