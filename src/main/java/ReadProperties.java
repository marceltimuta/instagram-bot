import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

    private static final String propertiesPath = "src/main/resources/insta.properties";

    public static Properties readPropertiesFile() throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(propertiesPath);
            // create Properties class object
            prop = new Properties();
            // load properties file into it
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert fis != null;
            fis.close();
        }
        return prop;
    }
}
