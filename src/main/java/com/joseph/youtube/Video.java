package com.joseph.youtube;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Video {

    private String id;
    private String folder;
    private String quality;

    // Constructor
    public Video(String id, String folder, String quality) {
        this.id = id;
        this.folder = folder;
        this.quality = quality;
    }

    public static void main(String[] args) {
        String url = "https://www.youtube.com/watch?v=vmZ83FRwzpY;q=720";
        Video video = Video.fromURL(url);
        System.out.println(video);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getFolder() {
        return folder;
    }

    public String getQuality() {
        return quality;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", folder='" + folder + '\'' +
                ", quality='" + quality + '\'' +
                '}';
    }

    public static Video fromURL(String url) {
        String[] arr = url.split("\\?");
        Map<String, String> params = getParams(arr[1]);
        String folder = params.get("f");
        String quality = params.get("q");
        String id = params.get("v");

        return new Video(id, folder, quality);
    }

    // Helper method to get query parameters from URI
    public static Map<String, String> getParams(String p) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = p.split(";|&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }

}
