package top.atstudy.framework.kit;

import com.alibaba.fastjson.JSONReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

public abstract class ConfigKit {
    private static final String DEFAULT_PROPERTIES_PATH = "config.properties";
    private static final String TRUE_STR = "true";
    private static final String FALSE_STR = "false";
    private static Properties PROP;

    public ConfigKit() {
    }

    public static synchronized void loadProperties(String propertiesPath) {
        FileInputStream in = null;

        try {
            in = new FileInputStream(propertiesPath);
            loadProperties((InputStream)in);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static synchronized void loadProperties(InputStream in) {
        Properties pro = new Properties();

        try {
            pro.load(in);
            in.close();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        setProperties(pro);
    }

    public static void loadProperties() {
        Properties pro = new Properties();
        InputStream in = null;

        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            pro.load(isr);
            in.close();
            isr.close();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        PROP = pro;
    }

    public static String readJson(String filePath) {
        String json = "";
        URL resource = Thread.currentThread().getContextClassLoader().getResource(filePath);
        if (resource == null) {
            return json;
        } else {
            String path = resource.getPath();

            try {
                json = (new JSONReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))).readString();
            } catch (UnsupportedEncodingException | FileNotFoundException var5) {
                var5.printStackTrace();
            }

            return json;
        }
    }

    public static Properties getProperties() {
        return PROP;
    }

    public static void setProperties(Properties properties) {
        PROP = properties;
    }

    public static String getProperty(String key) {
        return PROP == null ? null : PROP.getProperty(key);
    }

    public static String getString(String key) {
        return getProperty(key);
    }

    public static int getInt(String key) {
        String property = getProperty(key);

        assert property != null;

        return Integer.parseInt(property);
    }

    public static Long getLong(String key) {
        String property = getProperty(key);

        assert property != null;

        return Long.parseLong(property);
    }

    public static boolean getBoolean(String key) {
        String property = getProperty(key);

        assert property != null;

        if (!"true".equalsIgnoreCase(property) && !"false".equalsIgnoreCase(property)) {
            property = Integer.parseInt(property) != 0 ? "true" : "false";
        }

        return Boolean.parseBoolean(property);
    }

    public static void main(String[] args) {
        loadProperties();
        System.out.println(getString("sss"));
    }
}
