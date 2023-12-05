package com.joseph.youtube;

import com.joseph.youtube.config.Configuration;
import com.joseph.youtube.config.FileIO;
import com.joseph.youtube.config.Timer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeDownloader {

    public final String DEFAULT_LIST_FILE = "defaultListFile";
    public final String ONLY_NEW_VIDEOS_KEY = "downloadOnlyNewVideos";

    public final String historyFilePath = "ids.txt";

    String configFile = "config.properties";

    private Scanner scanner;
    private Configuration configuration;

    private String output;

    public YoutubeDownloader() {
        scanner = new Scanner(System.in);
        configuration = new Configuration(configFile, scanner);
        this.start();
    }

    public static void main(String[] args) {
        YoutubeDownloader yt = new YoutubeDownloader();
    }

    public static String extractPercentage(String input){
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+)%");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public void start(){
        while (true) {
            System.out.println("--------------------------");
            System.out.println("YouTube Video Downloader");
            System.out.println("1. One video MP4 format and choose resolution");
            System.out.println("2. MP3 format");
            System.out.println("3. Download full youtube playlist and choose resolution");
            System.out.println("4. Download list of videos from text file and choose resolution");
            System.out.println("5. Change Configuration Settings");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            this.configuration.viewConfiguration();

            switch (choice) {
                case "1":
                    Timer.start();
                    checkAndDownloadVideoMP4(false);
                    Timer.stop();
                    break;
                case "2":
                    Timer.start();
                    downloadVideoMP3();
                    Timer.stop();
                    break;
                case "3":
                    checkAndDownloadVideoMP4(true);
                    break;
                case "4":
                    downloadVideoMP4FromFile();
                    break;
                case "5":
                    changeConfig();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void downloadVideoMP4FromFile() {
        String defaultFilePath = configuration.getValue(DEFAULT_LIST_FILE);
        System.out.print("[Default = "+defaultFilePath+"]Enter file path of youtube list: ");
        String filePath = scanner.nextLine();

        if(filePath.equals("")) filePath = defaultFilePath;

        ArrayList<String> links = FileIO.read(filePath);
        if(links.size()==0){
            System.out.println("no youtube links in file "+defaultFilePath);
        };

        System.out.print("Enter resolution : 360?480?720?1080?best? ; best for faster download : ");
        String resolution = scanner.nextLine();

        for(String link : links){
            if(link.startsWith("f=")){
                presetParams(link);
            }else if(link.startsWith("https://www.youtube.com/watch?v")){
                checkAndDownloadVideoMP4(link, resolution, false);
            }
        }
    }

    private void presetParams(String paramString) {
        Map<String, String> params = Video.getParams(paramString);
        this.configuration.setQuality(params.get("q"));
        this.configuration.setFolder(params.get("f"));
    }

    private void logHistory(String link) {
        ArrayList<String> ids = FileIO.read(historyFilePath);
        ids.add(link);
        FileIO.write(ids,historyFilePath);
    }

    private boolean shouldDownload(String link) {
        boolean newVideoConfig = Boolean.parseBoolean(configuration.getValue(ONLY_NEW_VIDEOS_KEY));
        if(!newVideoConfig) return true; //download all vids
        if(linkInHistory(link)){
            return false;
        }
        return true;

    }

    private boolean linkInHistory(String link) {
        ArrayList<String> ids = FileIO.read(historyFilePath);
        return ids.contains(link);
    }

    private void changeConfig() {
        configuration.changeSettings();
    }

    public String selectBestCode(String link, String resolution){
        System.out.println("selecting best resolution for the video ("+link+"), if "+resolution+ " does not exist for it...");
        if(resolution.equals("best")){
            return "best";
        }
        String audioCode = "";
        String bestCode = "";
        int maxQuality = 0;


        try {
            String line;
            String command = "youtube-dl -F "+link;
            System.out.println("running command : "+command);
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if(!Character.isDigit(line.charAt(0))){
                    continue;
                }
                System.out.println(line); //<-- Parse data here.
                String code = line.substring(0,3).trim();


                if(line.contains("audio only")){
                    if(line.contains("m4a")){
                        audioCode = code;
                    }
                }else{
                    if(line.contains("mp4")){
                        if(line.contains(resolution+"p")){
                            bestCode = code;
                            break;
                        }
                        int quality = getQuality(line);
                        if(quality>=maxQuality) {
                            maxQuality=quality;
                            bestCode = code;
                        }
                    }
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        String result = "";


//        if(!resolution.equals("")){
//            if(resolution.equals("1080") && !videoCode1080.equals("")){
//                result = videoCode1080;
//            } else if(resolution.equals("720") && !videoCode720.equals("")){
//                result = videoCode720;
//            } else if(resolution.equals("480") && !videoCode480.equals("")){
//                result = videoCode480;
//            } else if(resolution.equals("360") && !videoCode360.equals("")){
//                result = videoCode360;
//            }
//        }

//        if(result.equals("")){
//            if(!videoCode1080.equals("")){
//                result = videoCode1080;
//            } else if(!videoCode720.equals("")){
//                result = videoCode720;
//            } else if(!videoCode480.equals("")){
//                result = videoCode480;
//            } else if(!videoCode360.equals("")){
//                result = videoCode360;
//            }
//        }





        if(audioCode.equals("") ||bestCode.equals("")){
            result = "best";
        }else{
            result = bestCode+"+"+audioCode;
        }

        return result;
    }

    private int getQuality(String line) {
        int result = 0;
        String[] arr = line.split("\\s+");
        String[] resolutions = arr[2].split("x");
        result = Integer.parseInt(resolutions[1]);
        return result;
    }

    public boolean runCmd(String cmd){
        String percentage = "";
        try {
            String line;
            System.out.println("running cmd : "+cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line); //<-- Parse data here.
                String word = "[download] Destination: ";
                if(line.contains(word) && this.output.equals("")){
                    this.output = line.substring(0+word.length(), line.length());
                }
                String temp = extractPercentage(line);
                if(!temp.equals("")){
                    percentage = temp;
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        }


        if(this.output.equals("")||!percentage.equals("100.0"))return false;
        return true;
    }

    public void checkAndDownloadVideoMP4(boolean isPlaylist) {
        System.out.print("Enter YouTube video URL: ");
        String videoUrlInput = scanner.nextLine();

        System.out.print("Enter resolution : 360?480?720?1080?best? ; best for faster download : ");
        String resolution = scanner.nextLine();

        checkAndDownloadVideoMP4(videoUrlInput, resolution, isPlaylist);

    }

    private boolean checkAndDownloadVideoMP4(String videoUrlInput, String resolution, boolean isPlaylist){

        Video video = Video.fromURL(videoUrlInput);
        String videoUrl = "https://www.youtube.com/watch?v="+video.getId();
        if(!shouldDownload(videoUrl)){
            System.out.println("You have already downloaded before : "+videoUrl);
            return false;
        }


        String outputFolder = this.configuration.getOutput();
        if(video.getFolder()!=null){
            File dir = new File(this.configuration.getDestinationFolderName()+"\\"+video.getFolder());
            if(!dir.exists()){
                try {
                    String path = dir.getPath();
                    Files.createDirectories(Path.of(path));
                    System.out.println(path+ " : Folder and its parents created successfully.");
                } catch (IOException e) {
                    // Handle the exception if something goes wrong
                    System.err.println("Error creating folder: " + e.getMessage());
                    return false;
                }
            }
            outputFolder = this.configuration.getDestinationFolderName()+"\\"+video.getFolder()+"\\"+this.configuration.getDownloadedFileName();
        }

        if(video.getQuality()!=null){
            resolution = video.getQuality();
        }

        if(video.getQuality()==null && video.getFolder()==null){
            String configFolder = this.configuration.getFolder();
            String configQuality = this.configuration.getQuality();
            boolean headerConfig = configFolder!=null && configQuality!=null;
            if(headerConfig){
                File dir = new File(this.configuration.getDestinationFolderName()+"\\"+configFolder);
                if(!dir.exists()){
                    try {
                        String path = dir.getPath();
                        Files.createDirectories(Path.of(path));
                        System.out.println(path+ " : Folder and its parents created successfully.");
                    } catch (IOException e) {
                        // Handle the exception if something goes wrong
                        System.err.println("Error creating folder: " + e.getMessage());
                        return false;
                    }
                }
                outputFolder = this.configuration.getDestinationFolderName()+"\\"+configFolder+"\\"+this.configuration.getDownloadedFileName();
                resolution = configQuality;
            }
        }

        if(resolution.equals("")){
            System.out.println("Canceled, no resolution for the video : "+videoUrlInput);
            return false;
        }

        String format = selectBestCode(videoUrl, resolution);

        String playlist = "--no-playlist";
        if(isPlaylist) playlist = "--yes-playlist";
        String[] command = {"youtube-dl", "-f", "mp4", "-f", format, playlist, "-o", outputFolder, videoUrl};

        this.output = "";
        boolean result = downloadVideo(command);

        if(result) logHistory(videoUrl);
        return result;
    }

    private boolean downloadVideo(String[] command){
        boolean result = runCmd(String.join(" ", command));
        if (result) {
            System.out.println("Video downloaded successfully at :"+this.output);
        } else {
            System.out.println("Failed to download video.");
        }
        System.out.println("End download : "+(new Date()));
        return result;
    }

    public void downloadVideoMP3() {
        System.out.print("Enter YouTube video URL: ");
        String url = scanner.nextLine();
        String[] command = {"youtube-dl", "-x", "--audio-format", "mp3", url};
        output = "";
        downloadVideo(command);
    }
}

