package com.example.systeminfo.parser;

import com.example.systeminfo.model.OsInfo;
import java.io.*;

/**
 * Interface defining the contract for retrieving Operating System details.
 */
public interface OsParser {
    /**
     * Fetches the OS name and version.
     *
     * @return OsInfo object.
     */
    OsInfo parse();

    /**
     * Factory method to return the correct parser implementation based on the OS.
     *
     * @param os The OS identifier ("windows" or "linux").
     * @return An instance of WindowsOsParser or LinuxOsParser.
     */
    static OsParser forOS(String os) {
        return os.equals("windows") ? new WindowsOsParser() : new LinuxOsParser();
    }
}

/**
 * Implementation of OsParser for Linux systems.
 * Tries 'lsb_release' or reads '/etc/os-release'.
 */
class LinuxOsParser implements OsParser {
    @Override
    public OsInfo parse() {
        try {
            // Complex command execution via bash to handle pipes/redirection
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "lsb_release -ds || cat /etc/os-release | grep PRETTY_NAME | cut -d= -f2 | tr -d '\"' || echo Linux"});
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String name = r.readLine();
                if (name == null || name.isBlank()) name = "Linux";

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

/**
 * Implementation of OsParser for Windows systems.
 * Uses Java System properties as a simpler alternative to 'systeminfo'.
 */
class WindowsOsParser implements OsParser {
    @Override
    public OsInfo parse() {
        String name = System.getProperty("os.name");
        String version = System.getProperty("os.version");
        return new OsInfo(name, version);
    }
}