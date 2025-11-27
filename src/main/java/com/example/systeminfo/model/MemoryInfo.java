package com.example.systeminfo.model;

/**
 * Represents information about the system's Random Access Memory (RAM).
 *
 * @param totalMb The total amount of memory in Megabytes.
 */
public record MemoryInfo(long totalMb) {}