package com.example.systeminfo.model;

/**
 * An aggregate record containing all gathered system information.
 *
 * @param os     Information about the Operating System.
 * @param cpu    Information about the CPU.
 * @param memory Information about the RAM.
 */
public record SystemInfo(OsInfo os, CpuInfo cpu, MemoryInfo memory) {}