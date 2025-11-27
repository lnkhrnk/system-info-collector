package com.example.systeminfo.model;

/**
 * Represents information about the Central Processing Unit (CPU).
 *
 * @param model The model name of the CPU.
 * @param cores The number of physical/logical cores.
 */
public record CpuInfo(String model, int cores) {}