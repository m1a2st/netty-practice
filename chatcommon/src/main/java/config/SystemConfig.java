package config;

import utils.ConfigProperties;

public class SystemConfig extends ConfigProperties {

    static ConfigProperties singleton = new SystemConfig("./system.properties");
    public SystemConfig(String propertyFileName) {
        super(propertyFileName);
        super.loadFromFile();
    }

    public static final String TEST = singleton.getValue("test");
    public static final String ADDRESS = singleton.getValue("address");
    public static final String PORT = singleton.getValue("port");
}
