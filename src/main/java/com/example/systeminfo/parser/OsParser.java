package com.example.systeminfo.parser;

import com.example.systeminfo.model.OsInfo;
import java.io.*;

public interface OsParser {
    OsInfo parse();

    static OsParser forOS(String os) {
        return os.equals("windows") ? new WindowsOsParser() : new LinuxOsParser();
    }
}

class LinuxOsParser implements OsParser {
    @Override
    public OsInfo parse() {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "lsb_release -ds || cat /etc/os-release | grep PRETTY_NAME | cut -d= -f2 | tr -d '\"' || echo Linux"});
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String name = r.readLine();
                if (name == null || name.isBlank()) name = "Linux";
                // Попробуем вытащить версию отдельно
                Process v = Runtime.getRuntime().exec(new String[]{"bash", "-c", "lsb_release -rs || cat /etc/os-release | grep VERSION_ID | cut -d= -f2 | tr -d '\"'"});
                try (BufferedReader vr = new BufferedReader(new InputStreamReader(v.getInputStream()))) {
                    String version = vr.readLine();
                    if (version == null || version.isBlank()) version = "Unknown";
                    return new OsInfo(name.trim(), version.trim());
                }
            }
        } catch (Exception e) {
            return new OsInfo("Linux", "Unknown");
        }
    }
}

class WindowsOsParser implements OsParser {
    @Override
    public OsInfo parse() {
        String name = System.getProperty("os.name");
        String version = System.getProperty("os.version");
        return new OsInfo(name, version);
    }
}