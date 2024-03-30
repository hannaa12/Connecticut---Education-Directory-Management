package com.app.project.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileDataUtil {
    //        Refactoring Pattern Used: Extract Class
    public static String readFileData(String filePath) {
        StringBuilder fileData = new StringBuilder();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fileData.append(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileData.toString();
    }
}
