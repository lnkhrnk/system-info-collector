package com.example.systeminfo;

import com.example.systeminfo.model.*;
import com.example.systeminfo.parser.*;
import com.fasterxml.jackson.databind.*;
import java.nio.file.*;
import java.util.Properties;

public class SystemInfoCollector {

    public static void main(String[] args) {
        try {
            Properties config = loadConfig();
            String unit = config.getProperty("memory.output.unit", "MB");

            String os = new OSDetector().detect();

            CpuInfo cpu = CpuParser.forOS(os).parse();
            MemoryInfo memory = MemoryParser.forOS(os).parse(unit);
            OsInfo osInfo = OsParser.forOS(os).parse();

            SystemInfo info = new SystemInfo(osInfo, cpu, memory);

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println(mapper.writeValueAsString(info));
        } catch (Exception e) {
            System.err.println("Error collecting system info: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Properties loadConfig() {
        Properties props = new Properties();
        Path path = Paths.get("app.properties");
        if (Files.exists(path)) {
            try (var in = Files.newInputStream(path)) {
                props.load(in);
            } catch (Exception ignored) {}
        }
        return props;
    }
}