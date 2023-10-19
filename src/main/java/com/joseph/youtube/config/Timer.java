package com.joseph.youtube.config;

public class Timer {

    public static long startTime;
    public static long endTime;

    public static void start(){
        startTime = System.nanoTime();
    }

    public static void stop(){
        // Record the end time
        endTime = System.nanoTime();

        // Calculate the execution time in milliseconds
        long executionTimeMs = (endTime - startTime) / 1000000;

        // Convert milliseconds to hh:mm:ss:ZZZ format
        long hours = executionTimeMs / 3600000;
        long minutes = (executionTimeMs % 3600000) / 60000;
        long seconds = (executionTimeMs % 60000) / 1000;
        long milliseconds = executionTimeMs % 1000;

        System.out.println("Execution time: " + String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds));
    }

}
