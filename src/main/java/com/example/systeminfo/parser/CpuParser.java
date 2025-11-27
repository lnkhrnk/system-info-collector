package com.example.systeminfo.parser;

import com.example.systeminfo.model.CpuInfo;
import java.io.*;

/**
 * Interface defining the contract for retrieving CPU information.
 */
public interface CpuParser {
    /**
     * Executes a system command to fetch CPU details.
     *
     * @return CpuInfo object containing model and core count.
     * @throws IOException If an I/O error occurs during command execution.
     */
    CpuInfo parse() throws IOException;

    /**
     * Factory method to return the correct parser implementation based on the OS.
     *
     * @param os The OS identifier ("windows" or "linux").
     * @return An instance of WindowsCpuParser or LinuxCpuParser.
     */
    static CpuParser forOS(String os) {
        return os.equals("windows") ? new WindowsCpuParser() : new LinuxCpuParser();
    }
}

/**
 * Implementation of CpuParser for Linux systems.
 * Uses the 'lscpu' command.
 */
class LinuxCpuParser implements CpuParser {
    @Override
    public CpuInfo parse() throws IOException {
        Process p = Runtime.getRuntime().exec("lscpu");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String model = "Unknown";
            int cores = 1;
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("Model name:")) {
                    model = line.substring(11).trim();
                }
                if (line.matches("CPU\\(s\\):.*")) {
                    cores = Integer.parseInt(line.split("\\s+")[1]);
                }
            }
            return new CpuInfo(model, cores);
        }
    }
}

/**
 * Implementation of CpuParser for Windows systems.
 * Uses the 'wmic' command.
 */
class WindowsCpuParser implements CpuParser {
    @Override
    public CpuInfo parse() throws IOException {
        Process p = Runtime.getRuntime().exec("wmic cpu get Name,NumberOfCores /format:list");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String model = "Unknown";
            int cores = 1;
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("Name=")) model = line.substring(5).trim();
                if (line.startsWith("NumberOfCores=")) cores = Integer.parseInt(line.substring(14).trim());
            }
            return new CpuInfo(model, cores);
        }
    }
}