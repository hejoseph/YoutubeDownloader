package com.joseph.youtube.config;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Configuration {

    private String configFile;

    private Properties config;

    private String destinationFolderName = "C:\\workspace\\stream\\youtube\\";
    private String downloadedFileName = "%(channel)s-%(upload_date)s-%(title)s\\ -\\ %(resolution)s.%(ext)s";

    private Scanner scanner;

    public Configuration(String configFile, Scanner scanner) {
        this.configFile = configFile;
        this.scanner = scanner;
        loadConfigFile();
    }

    public String getOutput(){
        return this.destinationFolderName+this.downloadedFileName.replace(" ", "");
    }

    public String getDestinationFolderName() {
        return destinationFolderName;
    }

    public void setDestinationFolderName(String destinationFolderName) {
        this.destinationFolderName = destinationFolderName;
    }

    public String getDownloadedFileName() {
        return downloadedFileName;
    }

    public void setDownloadedFileName(String downloadedFileName) {
        this.downloadedFileName = downloadedFileName;
    }

    private void loadConfigFile(){
        config = new Properties();

        // Check if the configuration file exists
        File file = new File(configFile);
        if (!file.exists()) {
            // If it doesn't exist, create it with default values
            try (OutputStream output = new FileOutputStream(configFile)) {
                config.setProperty("destinationFolderName", destinationFolderName);
                config.setProperty("downloadedFileName", downloadedFileName);
                config.store(output, "Default configuration");
                System.out.println("Configuration file created with default values at : "+file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Load the configuration file
        try (InputStream input = new FileInputStream(configFile)) {
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Access and use configuration values
        destinationFolderName = config.getProperty("destinationFolderName");
        downloadedFileName = config.getProperty("downloadedFileName");
//        boolean debugMode = Boolean.parseBoolean(config.getProperty("app.debug"));

        System.out.println("--------------------------------");
        System.out.println("Configuration settings");
        System.out.println("destinationFolderName: " + destinationFolderName);
        System.out.println("downloadedFileName: " + downloadedFileName);
    }

    private void saveConfigFile(){
        try (FileOutputStream output = new FileOutputStream(this.configFile)) {
            config.store(output, "Updated configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSettings() {
        this.scanner = scanner;
        while (true) {
            System.out.println("Configuration Menu:");
            System.out.println("1. View Configuration");
            System.out.println("2. Change Configuration Value");
            System.out.println("3. Save and Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewConfiguration();
                    break;
                case "2":
                    changeConfigurationValue();
                    break;
                case "3":
                    saveConfigFile();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

    }

    public void viewConfiguration() {
        System.out.println("------------------------------");
        System.out.println("Current Configuration Values:");
        config.forEach((key, value) -> System.out.println(key + " = " + value));
    }

    private void changeConfigurationValue() {
        viewConfiguration();
        System.out.print("Enter the key of the configuration value to change: ");
        String key = scanner.nextLine();
        System.out.print("Enter the new value: ");
        String value = scanner.nextLine();
        config.setProperty(key, value);
        saveConfigFile();
        loadConfigFile();
    }
}
