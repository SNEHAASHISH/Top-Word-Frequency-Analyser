package fsd.Step_12_TopKeywordAnalyser;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileUtility {
    public static boolean createFile (String fileNameWithPath) {
        File file = new File(fileNameWithPath); //'File' object created
        boolean fileCreated = false;

        try {
            String nameOfNewFile = "/Users/snehgupta/IdeaProjects/CodingMafia_JavaFSD/src/JavaFSD/Step_10"+"create-file.txt";
                //"Cmd + Shift + C" on package is shortcut for getting the file path...
            fileCreated = file.createNewFile();  //Checks if file already exists (false) or is created (true)
        } catch (IOException e)
            //When memory is full
        {
            e.printStackTrace();
        } //This is must for handling execution exception (---?)

        return fileCreated;
    }

    public static void readAndPrintFile(String fileName)
        //It is 'public' to allow access from outside
    {
        File file = new File(fileName);
        Scanner scn = null;
        try {
            scn = new Scanner(file);
            while (scn.hasNextLine()) {
                String line = scn.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scn != null) {
                scn.close(); //Closing is important to prevent memory leak
            }
        }
        //If not closed a lot of memory is leaked
    }

    public static ArrayList<String> readAndReturnFile(String fileName) {
        ArrayList<String> lines = new ArrayList<String>();
        File file = new File(fileName);
        Scanner scn = null;
        try {
            scn = new Scanner(file);
            while (scn.hasNextLine()) {
                String line = scn.nextLine();
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scn != null) {
                scn.close(); //Closing is important to prevent memory leak
            }
        }
        return lines;
    }

    public static boolean writeToFile(String fileNameWithPath, String content) {
        BufferedWriter bw = null;
        try {
            File file = new File(fileNameWithPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(content);
        } catch (IOException e) {
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean appendToFile(String fileName, String content) {
        try {
            FileWriter fileWriter = new FileWriter(fileName,true);
            fileWriter.append(content);
            fileWriter.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("This side is Sneh Aashish Gupta");
        System.out.println("Running FileUtility at "+new Date().toString());
        String nameOfNewFile = "/Users/snehgupta/IdeaProjects/CodingMafia_JavaFSD/src/JavaFSD/"+"create-file.txt";

        boolean created = createFile(nameOfNewFile); // *Create File* executes
            //createFile(nameOfNewFile); -> returns a boolean value
        System.out.println("Using createFile() \n");
        System.out.println("File Created : "+created);

        System.out.println("\n Using readAndPrintFile() \n");
        readAndPrintFile(nameOfNewFile); // *Read File*

        System.out.println("\n Using readAndReturnFile() \n");
        ArrayList<String> lines = readAndReturnFile(nameOfNewFile);
        for (String line:lines) {
            System.out.println(line);
        }
        System.out.println("The number of lines in file is: "+lines.size());

        System.out.println("\n Using writeToFile() \n");
        String nameOfWriteFile = "/Users/snehgupta/IdeaProjects/CodingMafia_JavaFSD/src/JavaFSD/"+"write-file.txt";
        boolean wroteToFile = writeToFile(nameOfWriteFile, "This file is generated using writeToFile() function\nHasta La Vista!!!");

        for (int i = 1; i <= 100; i++) {
            String data = i+"";
            appendToFile(nameOfWriteFile,data+"\n");
        }
    }
}
