package helpers;

import utilities.LanguageUtils;
import utilities.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelpers {


    private static Properties properties;
    private static String linkFile;
    private static FileInputStream file;
    private static FileOutputStream out;
    private static final String relPropertiesFilePathDefault = "src\\test\\resources\\config\\configs.properties";
    private static final String relExcelFilePath = "src\\test\\resources\\config\\DataTest.xlsx";

    public static Properties loadAllFiles() {
        LinkedList<String> files = new LinkedList<String>();
        files.add(relPropertiesFilePathDefault);
        files.add(relExcelFilePath);
        try {
            properties = new Properties();
            for (String f : files) {
                Properties tempProp = new Properties();
                linkFile = Helpers.getCurrentDir() + f;
                file = new FileInputStream(linkFile);
                tempProp.load(file);
                properties.putAll(tempProp);
            }
            Log.info("Loaded all properties files.");
            return properties;
        } catch (IOException ioe) {
            return new Properties();
        }
    }


    public static void setDefaultFile() {
        properties = new Properties();
        try {
            linkFile = Helpers.getCurrentDir() + relPropertiesFilePathDefault;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValueProperties(String key) {
        String keyVal = null;
        try {
            if (file == null) {
                properties = new Properties();
                linkFile = Helpers.getCurrentDir() + relPropertiesFilePathDefault;
                file = new FileInputStream(linkFile);
                properties.load(file);
                file.close();
            }
            keyVal = properties.getProperty(key);
            return LanguageUtils.convertCharset_ISO_8859_1_To_UTF8(keyVal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return keyVal;
    }

    public static String getValueExcel(String key) {
        String keyVal = null;
        try {
            if (file == null) {
                properties = new Properties();
                linkFile = Helpers.getCurrentDir() + relExcelFilePath;
                file = new FileInputStream(linkFile);
                properties.load(file);
                file.close();
            }
            keyVal = properties.getProperty(key);
            return LanguageUtils.convertCharset_ISO_8859_1_To_UTF8(keyVal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return keyVal;
    }

    public static void setValue(String key, String keyValue) {
        try {
            if (file == null) {
                properties = new Properties();
                file = new FileInputStream(Helpers.getCurrentDir() + relPropertiesFilePathDefault);
                properties.load(file);
                file.close();
                out = new FileOutputStream(Helpers.getCurrentDir() + relPropertiesFilePathDefault);
            }
            //Ghi vào cùng file Prop với file lấy ra
            out = new FileOutputStream(linkFile);
            System.out.println(linkFile);
            properties.setProperty(key, keyValue);
            properties.store(out, null);
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
