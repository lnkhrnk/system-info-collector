package com.example.systeminfo.parser;

import com.example.systeminfo.model.MemoryInfo;
import java.io.*;

public interface MemoryParser {
    MemoryInfo parse(String unit) throws IOException;

    static MemoryParser forOS(String os) {
        return os.equals("windows") ? new WindowsMemoryParser() : new LinuxMemoryParser();
    }
}

class LinuxMemoryParser implements MemoryParser {
    @Override
    public MemoryInfo parse(String unit) throws IOException {
        Process p = Runtime.getRuntime().exec("cat /proc/meminfo");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            long totalKb = 0;
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("MemTotal:")) {
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

class WindowsMemoryParser implements MemoryParser {
    @Override
    public MemoryInfo parse(String unit) throws IOException {
        // TotalVisibleMemorySize возвращает значение в КБ
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