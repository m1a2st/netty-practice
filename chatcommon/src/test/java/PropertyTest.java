import config.SystemConfig;
import org.junit.jupiter.api.Test;

public class PropertyTest {

    @Test
    public void testProperty() {
        new SystemConfig("system.properties");
        System.out.println(SystemConfig.TEST);
        System.out.println(SystemConfig.ADDRESS);
        System.out.println(SystemConfig.PORT);
    }
}
