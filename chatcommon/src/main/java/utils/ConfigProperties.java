package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigProperties {

    private final String propertyFileName;
    private final Properties properties = new Properties();

    public ConfigProperties(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    protected void loadFromFile()  {
        InputStream in;
        InputStreamReader iReader;

        try {
            ClassLoader classLoader = ConfigProperties.class.getClassLoader();
            in = classLoader.getResourceAsStream(propertyFileName);

            if (null == in) {
                String filePath = "./" + propertyFileName;
                in = new FileInputStream(filePath);
            }

            iReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            properties.load(iReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }

    public String getValue(String key) {
        return readProperty(key);
    }
}
