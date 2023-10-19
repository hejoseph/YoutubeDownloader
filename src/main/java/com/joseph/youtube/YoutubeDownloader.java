package com.joseph.youtube;

import com.joseph.youtube.config.Configuration;
import com.joseph.youtube.config.FileIO;
import com.joseph.youtube.config.Timer;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class YoutubeDownloader {


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
                    downloadVideoMP4(false);
                    Timer.stop();
                    break;
                case "2":
                    Timer.start();
                    downloadVideoMP3();
                    Timer.stop();
                    break;
                case "3":
                    downloadVideoMP4(true);
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
        System.out.print("Enter file path of youtube list: ");
        String filePath = scanner.nextLine();

        ArrayList<String> links = FileIO.read(filePath);
        if(links.size()==0)return;

        System.out.print("Enter resolution : 360?480?720?1080?best? ; best for faster download : ");
        String resolution = scanner.nextLine();

        for(String link : links){
            downloadVideoMP4(link, resolution, false);
        }
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
        String videoCode1080 = "";
        String videoCode720 = "";
        String videoCode480 = "";
        String videoCode360 = "";
        try {
            String line;
            Process p = Runtime.getRuntime().exec("youtube-dl -F "+link);
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if(!Character.isDigit(line.charAt(0))){
                    continue;
                }
                System.out.println(line); //<-- Parse data here.
                String code = line.substring(0,3).trim();
                if(line.contains("audio only") && line.contains("m4a")){
                    audioCode = code;
                }else{
                    if(line.contains("mp4")){
                        if(line.contains("1080p")){
                            videoCode1080 = code;
                        }else if(line.contains("720p")){
                            videoCode720 = code;
                        }else if(line.contains("480p")){
                            videoCode480 = code;
                        }else if(line.contains("360p")){
                            videoCode360 = code;
                        }
                    }
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        String result = "";

        if(!resolution.equals("")){
            if(resolution.equals("1080") && !videoCode1080.equals("")){
                result = videoCode1080;
            } else if(resolution.equals("720") && !videoCode720.equals("")){
                result = videoCode720;
            } else if(resolution.equals("480") && !videoCode480.equals("")){
                result = videoCode480;
            } else if(resolution.equals("360") && !videoCode360.equals("")){
                result = videoCode360;
            }
        }

        if(result.equals("")){
            if(!videoCode1080.equals("")){
                result = videoCode1080;
            } else if(!videoCode720.equals("")){
                result = videoCode720;
            } else if(!videoCode480.equals("")){
                result = videoCode480;
            } else if(!videoCode360.equals("")){
                result = videoCode360;
            }
        }

        if(audioCode.equals("")){
            result = "best";
        }else{
            result += "+"+audioCode;
        }

        return result;
    }

    public boolean runCmd(String cmd){
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
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        }
        if(this.output.equals(""))return false;
        return true;
    }

    public void downloadVideoMP4(boolean isPlaylist) {
        System.out.print("Enter YouTube video URL: ");
        String videoUrl = scanner.nextLine();

        System.out.print("Enter resolution : 360?480?720?1080?best? ; best for faster download : ");
        String resolution = scanner.nextLine();

        downloadVideoMP4(videoUrl, resolution, isPlaylist);

    }

    private void downloadVideoMP4(String videoUrl, String resolution, boolean isPlaylist){
        String format = selectBestCode(videoUrl, resolution);

        String playlist = "--no-playlist";
        if(isPlaylist) playlist = "--yes-playlist";
        String[] command = {"youtube-dl", "-f", "mp4", "-f", format, playlist, "-o", this.configuration.getOutput(), videoUrl};

        this.output = "";
        downloadVideo(command);
    }

    private void downloadVideo(String[] command){
        boolean result = runCmd(String.join(" ", command));
        if (result) {
            System.out.println("Video downloaded successfully at :"+this.output);
        } else {
            System.out.println("Failed to download video.");
        }
    }

    public void downloadVideoMP3() {
        System.out.print("Enter YouTube video URL: ");
        String url = scanner.nextLine();
        String[] command = {"youtube-dl", "-x", "--audio-format", "mp3", url};
        output = "";
        downloadVideo(command);
    }
}

