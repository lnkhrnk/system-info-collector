package com.example.systeminfo.model;

/**
 * Represents information about the Operating System.
 *
 * @param name    The name of the OS (e.g., "Ubuntu", "Windows 10").
 * @param version The version of the OS.
 */
public record OsInfo(String name, String version) {}