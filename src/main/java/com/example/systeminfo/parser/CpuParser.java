package com.example.systeminfo.parser;

import com.example.systeminfo.model.CpuInfo;
import java.io.*;

public interface CpuParser {
    CpuInfo parse() throws IOException;

    static CpuParser forOS(String os) {
        return os.equals("windows") ? new WindowsCpuParser() : new LinuxCpuParser();
    }
}

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