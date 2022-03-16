package adventure.menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TextReader {

  public static final String FILE_PATH = "./text files/";

  public static void loadText(String filename) {

    try {
      File dataFile = new File(FILE_PATH + filename);
      Scanner myReader = new Scanner(dataFile);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();

    } catch (FileNotFoundException e) {
      System.out.println("File can't be loaded.");
    }
  }

  public static String[] loadDescription(String filename) {
    String[] descriptionList = {};
    String descriptionBlock = "";
    try {
      File dataFile = new File(FILE_PATH + filename);
      Scanner myReader = new Scanner(dataFile);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        descriptionBlock += data + "\n";
      }
      descriptionList = descriptionBlock.split(";");
      myReader.close();

    } catch (FileNotFoundException e) {
      System.out.println("File can't be loaded.");
    }

    return descriptionList;
  }
}
