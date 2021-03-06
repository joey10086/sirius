package de.unijena.bioinf.ChemistryBase.properties;
/**
 * Created by Markus Fleischauer (markus.fleischauer@gmail.com)
 * as part of the sirius
 * 31.08.17.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Properties;

/**
 * @author Markus Fleischauer (markus.fleischauer@gmail.com)
 */
public class PropertyManager {
    public static final String PROPERTY_BASE = "de.unijena.bioinf";
    public static final Properties PROPERTIES;

    static {
        PROPERTIES = loadDefaultProperties();
    }

    public static void addPropertiesFromStream(InputStream stream) throws IOException {
        Properties props = new Properties();
        props.load(stream);
        PropertyManager.PROPERTIES.putAll(props);
    }

    public static void addPropertiesFromFile(Path files) {
        try {
            if (Files.exists(files)) {
                addPropertiesFromStream(Files.newInputStream(files, StandardOpenOption.READ));
            }
        } catch (IOException e) {
            System.err.println("WARNING: could not load Properties from: " + files.toString());
            e.printStackTrace();
        }
    }

    private static Properties loadDefaultProperties() {
        String p = System.getProperties().getProperty("de.unijena.bioinf.ms.sirius.props");
        LinkedHashSet<String> resources = new LinkedHashSet<>();
        resources.add("sirius.build.properties");

        if (p != null && !p.isEmpty())
            resources.addAll(Arrays.asList(p.split(",")));

        Properties global = new Properties();
        for (String resource : resources) {
            try (InputStream input = PropertyManager.class.getResourceAsStream("/" + resource)) {
                Properties props = new Properties();
                props.load(input);
                global.putAll(props);
            } catch (IOException e) {
                System.err.println("Could not load properties from " + resource.toString());
                e.printStackTrace();
            }
        }
        return global;

    }

    public static Object setProperty(String key, String value) {
        return PROPERTIES.setProperty(key, value);
    }

    public static Object put(String key, String value) {
        return PROPERTIES.put(key, value);
    }

    public static int getNumberOfCores() {
        return Integer.valueOf(PROPERTIES.getProperty("de.unijena.bioinf.sirius.cpu.cores", "1"));
    }

    public static int getNumberOfThreads() {
        return Integer.valueOf(PROPERTIES.getProperty("de.unijena.bioinf.sirius.cpu.threads", "2"));
    }

    public static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getStringProperty(String key, String backupKey, String defaultValue) {
        return getProperty(key, getProperty(backupKey, defaultValue));
    }


    public static String getStringProperty(String key, String backupKey) {
        return getStringProperty(key, backupKey, null);
    }

    public static int getIntProperty(String key, String backupKey) {
        return Integer.valueOf(getStringProperty(key, backupKey));
    }

    public static double getDoubleProperty(String key, String backupKey) {
        return Double.valueOf(getStringProperty(key, backupKey));
    }

    public static boolean getBooleanProperty(String key, String backupKey) {
        return Boolean.valueOf(getStringProperty(key, backupKey));
    }

    public static int getIntProperty(String key, int defaultValue) {
        String v = getProperty(key);
        return v == null ? defaultValue : Integer.valueOf(v);
    }

    public static double getDoubleProperty(String key, double defaultValue) {
        String v = getProperty(key);
        return v == null ? defaultValue : Double.valueOf(v);
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String v = getProperty(key);
        return v == null ? defaultValue : Boolean.valueOf(v);
    }

    public static boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, false);
    }

    public static Path getPath(String key) {
        String v = getProperty(key);
        return (v == null) ? null : Paths.get(v);
    }

    public static File getFile(String key) {
        String v = getProperty(key);
        return (v == null) ? null : new File(v);
    }


    public static void main(String[] args) {
        PropertyManager.PROPERTIES.get("foo");
        System.out.println(PropertyManager.PROPERTIES);
    }

}
