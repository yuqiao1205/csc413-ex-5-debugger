package interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CodeTable {
  private static Map<String, String> codeTable = new HashMap<>();
  private static String byteCodesTXT = "interpreter/byteCodes.txt";

  static {
    init();
  }

  public static void init() {
    mapCodeTable();
  }

  private static void mapCodeTable() {
    try {
      File byteCodesFile = new File(byteCodesTXT);
      Scanner inputFile = new Scanner(byteCodesFile);
      while (inputFile.hasNext()) {
        String nextLine = inputFile.nextLine();
        String[] readLine = nextLine.split(", ");
        codeTable.put(readLine[0], readLine[1]);
      }
      inputFile.close();
    } catch (FileNotFoundException e) {
      System.err.println("FILE NOT FOUND: " + byteCodesTXT);
    }
  }

  public static String get(String code) {
    if (codeTable.containsKey(code)) {
      return codeTable.get(code);
    } else {
      System.err.println("Error finding ByteCode" + code);
      return null;
    }
  }

  public static String getClassName(String key) {

    return codeTable.get(key);

  }
}