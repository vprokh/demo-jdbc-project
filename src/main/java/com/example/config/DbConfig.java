package com.example.config;

import com.example.util.ConfigurationFileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DbConfig {
    private static final String CONFIG_FILE_PATH = "application.properties";

    private static final String DB_URL_CONFIG_KEY = "db.url";
    private static final String DB_USER_CONFIG_KEY = "db.user";
    private static final String DB_PASSWORD_CONFIG_KEY = "db.password";

    private static final Map<String, String> CONFIGURATION = new HashMap<>();

    public DbConfig() {
        CONFIGURATION.putAll(ConfigurationFileUtils.getConfigurationFromFile(CONFIG_FILE_PATH));
    }

    public Properties getDbProperties() {
        return ConfigurationFileUtils.getPropertiesFromFile(CONFIG_FILE_PATH);
    }

    public String getDbUrl() {
        return CONFIGURATION.get(DB_URL_CONFIG_KEY);
    }

    public String getDbUser() {
        return CONFIGURATION.get(DB_USER_CONFIG_KEY);
    }

    public String getDbPassword() {
        return CONFIGURATION.get(DB_PASSWORD_CONFIG_KEY);
    }
}
