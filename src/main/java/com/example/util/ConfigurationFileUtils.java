package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public final class ConfigurationFileUtils {

    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private ConfigurationFileUtils() {
        // util class
    }

    public static Map<String, String> getConfigurationFromFile(String filePath) {
        Map<String, String> configuration = new HashMap<>();
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource(filePath).getPath();

        try (Stream<String> lines = Files.lines(Paths.get(fileUrl))) {
            lines.filter(line -> line.contains(KEY_VALUE_SEPARATOR))
                    .forEach(line -> {
                        String[] keyValuePair = line.split(KEY_VALUE_SEPARATOR);
                        String key = keyValuePair[KEY_INDEX];
                        String value = keyValuePair.length > VALUE_INDEX ? keyValuePair[VALUE_INDEX] : "";
                        configuration.put(key, value);
                    });
        } catch (IOException e) {
            // we should log this actually and return empty map
            e.printStackTrace();
        }

        return configuration;
    }

    public static Properties getPropertiesFromFile(String filePath) {
        Properties properties = new Properties();
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource(filePath).getPath();

        try (InputStream stream = Files.newInputStream(Paths.get(fileUrl))) {
            properties.load(stream);
        } catch (IOException e) {
            // we should log this actually and return empty map
            e.printStackTrace();
        }

        return properties;
    }
}
