package com.example.systeminfo;

/**
 * Detects the type of operating system.
 */
public class OSDetector {
    /**
     * @return "windows" или "linux"
     */
    public String detect() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "windows";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return "linux";
        }
        throw new UnsupportedOperationException("Неизвестная ОС: " + os);
    }
}