package utils;

import java.io.*;
import java.util.*;

public class FileManager {
    public static void main(String[] args) {
        // Read file
        try {
            List<String> lines = readAllLines("src/data");
            System.out.println("Read " + lines.size() + " lines:");
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<String> readAllLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}