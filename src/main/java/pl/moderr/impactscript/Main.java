package pl.moderr.impactscript;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.Interpreter;
import pl.moderr.impactscript.interpreter.type.Token;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
  public static void main(String @NotNull [] args) throws Exception {
//        debug("");
//    debugTokenize("pi()*2+pow(sqrt(64), 2-2*20+2)*0.5");
//    parseTokenized();
    prod(args);
  }

  public static void debug(String stmt) {
    System.out.println("DEBUG MODE");
    Interpreter interpreter = new Interpreter();
    interpreter.debugMode = true;
    try {
      String result = interpreter.interpret(interpreter.tokenize(stmt));
      System.out.println(result);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static void debugTokenize(String stmt) throws Exception {
    Interpreter interpreter = new Interpreter();
    interpreter.debugMode = true;
    ArrayList<Token> tokens = interpreter.tokenize(stmt);
    FileOutputStream fos = new FileOutputStream("a.isb");
    ObjectOutputStream out = new ObjectOutputStream(fos);
    out.writeObject(tokens);
    fos.close();
  }

  public static void parseTokenized() throws Exception {
    Interpreter interpreter = new Interpreter();
    FileInputStream fis = new FileInputStream("a.isb");
    ObjectInputStream in = new ObjectInputStream(fis);
    ArrayList<Token> tokens = (ArrayList<Token>) in.readObject();
    fis.close();
    System.out.println(interpreter.interpret(tokens));
  }

  public static void prod(String @NotNull [] args) {
    if(args.length == 0) {
      System.out.println("Required program arg: script file");
      return;
    }
    String fileName = args[0];
    System.out.println("Debug: false");
    System.out.println("Script name: " + fileName);
    String fileNameOutput = args.length >= 2 ? args[1] : "result.txt";
    System.out.println("Output name: " + fileNameOutput);
    String statement;
    try {
      statement = Files.readString(Paths.get(fileName));
    } catch (IOException e) {
      throw new RuntimeException("failed to read");
    }
    System.out.println();
    System.out.println("Executing: " + statement);
    Interpreter interpreter = new Interpreter();
    interpreter.debugMode = false;
    String output;
    try {
      long start_token = System.currentTimeMillis();
      ArrayList<Token> tokens = interpreter.tokenize(statement);
      long end_token = (System.currentTimeMillis() - start_token);
      System.out.println("- tokenized in " + end_token + "ms");
      long start_inter = System.currentTimeMillis();
      output = interpreter.interpret(tokens);
      long end_inter = (System.currentTimeMillis() - start_inter);
      System.out.println("- interpreted in " + end_inter + "ms");
      System.out.println("- total time: " + (end_inter + end_token) + "ms");
    } catch (Exception e) {
      System.out.println("EXCEPTION");
      output = e.getMessage();
    }
    System.out.println(output);
    Path path = Paths.get(fileNameOutput);
    try {
      Files.deleteIfExists(path);
      Files.writeString(path, output);
    } catch (IOException e) {
      System.out.println(e.getLocalizedMessage());
    }
  }
}