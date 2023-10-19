package com.joseph.youtube.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {


    public static void write(ArrayList<String> values, String fp){
        File file=new File(fp);
        try{
            FileWriter fw=new FileWriter(fp);
            for(int i=0; i<values.size();i++){
                fw.write(values.get(i));
                if(!(i==values.size()-1)){
                    fw.write("\n");
                }
            }
            fw.close();
        }
        catch(IOException ignored){}
    }

    /**
     * This method reads the contents of a file.
     * @param fp                    Complete filepath of the file to read.
     * @return ArrayList<String>    String arraylist representing all of the contents of the file.
     */
    public static ArrayList<String> read(String fp){
        File file=new File(fp);
        ArrayList<String> contents=new ArrayList<String>();
        try{
            Scanner sc=new Scanner(file);
            while(sc.hasNextLine()){
                contents.add(sc.nextLine());
            }
            sc.close();
        }
        catch(FileNotFoundException ignored){
            System.out.println("file not found : "+fp);
        }
        catch(IOException ignored){
            System.out.println("cannot read from file : "+fp);
        }
        return contents;
    }

    public static void append(String cmd, String fp){
        ArrayList<String> contents = read(fp);
        contents.add(cmd);
        write(contents,fp);
    }

}
