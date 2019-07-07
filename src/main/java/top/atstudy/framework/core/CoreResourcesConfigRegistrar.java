package top.atstudy.framework.core;

import org.apache.commons.lang3.StringUtils;
import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.kit.ConfigKit;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class CoreResourcesConfigRegistrar extends AbstractCoreRegistrar {
    private static final String CLASS_PATH = "java.class.path";
    private static final String PROPERTIES_SUFFIX = ".properties";
    private static final String YAML_SUFFIX = ".yml";
    private static final String CLASSPATH_PREFIX = "classpath:";

    public CoreResourcesConfigRegistrar(IApplicationConfig systemConfig, IApplicationConfig applicationConfig, List<IComponentConfig> componentList) {
        super(systemConfig, applicationConfig, componentList);
    }

    public PropertySourcesPlaceholderConfigurer readConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        List<Properties> resources = new ArrayList();
        this.loadArgumentConfig(resources);
        this.loadJarConfig(resources);
        this.loadClassPathConfig(resources);
        if (resources.size() > 0) {
            Collections.reverse(resources);
            pspc.setPropertiesArray((Properties[])resources.toArray(new Properties[0]));
        }

        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }

    private void loadArgumentConfig(List<Properties> resources) throws IOException {
        String configLocation = System.getProperty("spring.config.location");
        if (StringUtils.isNotEmpty(configLocation)) {
            String[] var3 = configLocation.split(",");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String config = var3[var5];
                config = config.trim();
                Resource resource = null;
                if (config.startsWith("classpath:")) {
                    resource = new ClassPathResource(config.substring("classpath:".length()));
                } else {
                    resource = new UrlResource(config);
                }

                if (config.endsWith(".yml")) {
                    YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
                    yaml.setResources(new Resource[]{(Resource)resource});
                    Properties propertiesObject = yaml.getObject();
                    if (propertiesObject.size() > 0) {
                        resources.add(propertiesObject);
                    }
                } else if (config.endsWith(".properties")) {
                    ConfigKit.loadProperties(((Resource)resource).getInputStream());
                    if (ConfigKit.getProperties() != null && ConfigKit.getProperties().size() > 0) {
                        resources.add(ConfigKit.getProperties());
                    }
                }
            }
        }

    }

    private void loadJarConfig(List<Properties> resources) throws IOException {
        String property = System.getProperty("java.class.path");
        if (property.split(File.pathSeparator).length == 1) {
            resources.add(this.extractJarProperties(property));
        }

    }

    private void loadClassPathConfig(List<Properties> resources) throws IOException {
        String mainResourcesDir = this.getMainResources();
        File configFolder = new File(mainResourcesDir);
        if (configFolder.isDirectory()) {
            FilenameFilter ff = new FilenameFilter() {
                public boolean accept(File file, String string) {
                    return string.endsWith(".properties");
                }
            };
            File[] listFiles = configFolder.listFiles(ff);

            for(int i = 0; i < listFiles.length; ++i) {
                if (listFiles[i].isFile()) {
                    ConfigKit.loadProperties(listFiles[i].getPath());
                    if (ConfigKit.getProperties() != null && ConfigKit.getProperties().size() > 0) {
                        resources.add(ConfigKit.getProperties());
                    }
                }
            }
        }

    }

    private Properties extractJarProperties(String property) throws IOException {
        Properties properties = new Properties();
        JarFile jarFile = new JarFile(property);
        Enumeration entries = jarFile.entries();

        while(entries.hasMoreElements()) {
            String fileName = ((JarEntry)entries.nextElement()).getName();
            if (fileName.endsWith(".properties")) {
                InputStream resourceAsStream = Thread.currentThread().getClass().getResourceAsStream("/" + fileName);
                properties.load(resourceAsStream);
            }
        }

        return properties;
    }

    private String getMainResources() throws IOException {
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("");

        String mainResourcesDir;
        URL url;
        for(mainResourcesDir = ""; StringUtils.isEmpty(mainResourcesDir) && urls.hasMoreElements(); mainResourcesDir = url.getPath().contains("resources/main/") ? url.getPath() : "") {
            url = (URL)urls.nextElement();
        }

        if (StringUtils.isEmpty(mainResourcesDir)) {
            urls = Thread.currentThread().getContextClassLoader().getResources("");
        }

        while(StringUtils.isEmpty(mainResourcesDir) && urls.hasMoreElements()) {
            url = (URL)urls.nextElement();
            mainResourcesDir = url.getPath().contains("resources/") ? url.getPath() : "";
        }

        return mainResourcesDir;
    }

    public Object registry() {
        PropertySourcesPlaceholderConfigurer bean = null;

        try {
            bean = this.readConfigurer();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return bean;
    }
}
