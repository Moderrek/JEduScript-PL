package pl.moderr.eduscript;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.Interpreter;
import pl.moderr.eduscript.interpreter.exception.InterpreterError;
import pl.moderr.eduscript.interpreter.exception.LexerErr;
import pl.moderr.eduscript.interpreter.statements.Expression;
import pl.moderr.eduscript.interpreter.type.Token;
import pl.moderr.eduscript.lexer.Lexer;
import pl.moderr.eduscript.parser.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static final String NAME = "EduScript Interpreter";
  public static final String VERSION = "pre-0.5v 18JDK 21.10.2023";

  public static void main(String @NotNull [] args) {
    if (args.length == 0) {
      try {
        runConsole();
      } catch (Exception e) {
        System.err.println(e.getLocalizedMessage());
      }
    } else {
      try {
        runFile(args[0]);
      } catch (LexerErr err) {
        err.printError();
        err.printStackTrace();
      } catch (Exception e) {
        System.err.println(e.getLocalizedMessage());
        e.printStackTrace();
      }
    }
//    debugTokenize("pi()*2+pow(sqrt(64), 2-2*20+2)*0.5");
//    parseTokenized();
//    prod(args);
//    Interpreter interpreter = new Interpreter();
//    interpreter.debugMode = true;
//    ArrayList<Token> tokens = interpreter.tokenize("2+2");
//    ISParser parser = new ISParser(tokens);
//    Node node = parser.parse();
//    System.out.println(node.toString());
//    debugParse("-2 + 4 * 2");
//    parseParsed();
  }

  private static void runConsole() {
    String fileName = "<stdin>";
    System.out.println(NAME + " " + VERSION + " by Tymon Wozniak");
    System.out.println("PROTOTYPE VERSION");
    System.out.println("https://github.com/Moderrek");
    System.out.println();
    System.out.println(fileName);
    Lexer lexer = new Lexer(fileName);
    Interpreter interpreter;
    try {
      interpreter = new Interpreter();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("es >>> ");
      String line = scanner.nextLine();
      if (line.equalsIgnoreCase("wyjdz()") || line.equalsIgnoreCase("exit()") || line.equalsIgnoreCase("stop()") || line.equalsIgnoreCase("bye()") || line.equalsIgnoreCase("leave()")) break;
      ArrayList<Token> tokens;
      try {
        tokens = lexer.lex(line, false);
      } catch (Exception e) {
        System.err.println(e.getLocalizedMessage());
        System.out.println();
        continue;
      }
      Parser parser = new Parser(tokens);
      ArrayList<Expression> expressions;
      try {
        expressions = parser.parseString();
      } catch (Exception e) {
        System.err.println(e.getLocalizedMessage());
        System.out.println();
        continue;
      }
      try {
        interpreter.interpret(expressions);
      } catch (InterpreterError interpreterError) {
        interpreterError.printError();
        System.out.println();
      } catch (Exception e) {
        System.err.println(e.getLocalizedMessage());
        System.out.println();
      }
    }
    System.out.println("bye!");
  }

  public static String readFile(String fileName) throws IOException {
    return Files.readString(Path.of(fileName));
  }

  public static void runFile(String fileName) throws Exception {
    Lexer lexer = new Lexer(fileName);
    ArrayList<Token> tokens = lexer.lex(readFile(fileName), false);
    // save tokens
    saveTokens(fileName + "t", tokens);
    Parser parser = new Parser(tokens);
    ArrayList<Expression> expressions = parser.parseString();
    // save to binary
    saveBinary(fileName + "b", expressions);
    Interpreter interpreter = new Interpreter();
    interpreter.interpret(expressions);
  }

  private static void saveBinary(String fileName, ArrayList<Expression> expressions) throws IOException {
    FileOutputStream fos = new FileOutputStream(fileName);
    ObjectOutputStream out = new ObjectOutputStream(fos);
    out.writeObject(expressions);
    fos.close();
  }

  private static void saveTokens(String fileName, @NotNull ArrayList<Token> tokens) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
    for(Token token : tokens) {
      writer.append(token.toString());
    }
    writer.close();
  }

  public static void debug(String stmt) throws Exception {
    System.out.println("LEXER");
    Lexer lexer = new Lexer("debug");
    ArrayList<Token> tokens = lexer.lex(stmt, true);
    System.out.println("PARSER");
    Parser parser = new Parser(tokens);
    ArrayList<Expression> ast = parser.parseString();
    System.out.println("INTERPRETER");
    Interpreter interpreter = new Interpreter();
    System.out.println("RESULT");
    interpreter.interpret(ast);
  }

//  public static void debugTokenize(String stmt) throws Exception {
//    Interpreter interpreter = new Interpreter();
//    interpreter.debugMode = true;
//    ArrayList<Token> tokens = interpreter.tokenize(stmt);
//    FileOutputStream fos = new FileOutputStream("a.isb");
//    ObjectOutputStream out = new ObjectOutputStream(fos);
//    out.writeObject(tokens);
//    fos.close();
//  }
//
//  public static void debugParse(String stmt) throws Exception {
//    Interpreter interpreter = new Interpreter();
//    interpreter.debugMode = true;
//    ArrayList<Token> tokens = interpreter.tokenize(stmt);
//    // tokens
//    {
//      FileOutputStream fos = new FileOutputStream("a.is.tokens");
//      ObjectOutputStream out = new ObjectOutputStream(fos);
//      out.writeObject(tokens);
//      fos.close();
//    }
//    Parser parser = new Parser(tokens);
//    Expression expression = parser.parse();
//    // binary
//    {
//      FileOutputStream fos = new FileOutputStream("a.isb");
//      ObjectOutputStream out = new ObjectOutputStream(fos);
//      out.writeObject(expression);
//      fos.close();
//    }
//  }
//
//  public static void parseTokenized() throws Exception {
//    Interpreter interpreter = new Interpreter();
//    FileInputStream fis = new FileInputStream("a.isb");
//    ObjectInputStream in = new ObjectInputStream(fis);
//    ArrayList<Token> tokens = (ArrayList<Token>) in.readObject();
//    fis.close();
//    System.out.println(interpreter.interpret(tokens));
//  }
//
//  public static void parseParsed() throws Exception {
//    Interpreter interpreter = new Interpreter();
//    FileInputStream fis = new FileInputStream("a.isb");
//    ObjectInputStream in = new ObjectInputStream(fis);
//    Expression expr = (Expression) in.readObject();
//    fis.close();
//  }
//
//  public static void prod(String @NotNull [] args) throws Exception {
//    if(args.length == 0) {
//      System.out.println("Required program arg: script file");
//      return;
//    }
//    String fileName = args[0];
//    System.out.println("Debug: false");
//    System.out.println("Script name: " + fileName);
//    String fileNameOutput = args.length >= 2 ? args[1] : "result.txt";
//    System.out.println("Output name: " + fileNameOutput);
//    String statement;
//    try {
//      statement = Files.readString(Paths.get(fileName));
//    } catch (IOException e) {
//      throw new RuntimeException("failed to read");
//    }
//    System.out.println();
//    System.out.println("Executing: " + statement);
//    Interpreter interpreter = new Interpreter();
//    interpreter.debugMode = false;
//    String output;
//    try {
//      long start_token = System.currentTimeMillis();
//      ArrayList<Token> tokens = interpreter.tokenize(statement);
//      long end_token = (System.currentTimeMillis() - start_token);
//      System.out.println("- tokenized in " + end_token + "ms");
//      long start_inter = System.currentTimeMillis();
//      output = interpreter.interpret(tokens);
//      long end_inter = (System.currentTimeMillis() - start_inter);
//      System.out.println("- interpreted in " + end_inter + "ms");
//      System.out.println("- total time: " + (end_inter + end_token) + "ms");
//    } catch (Exception e) {
//      System.out.println("EXCEPTION");
//      output = e.getMessage();
//    }
//    System.out.println(output);
//    Path path = Paths.get(fileNameOutput);
//    try {
//      Files.deleteIfExists(path);
//      Files.writeString(path, output);
//    } catch (IOException e) {
//      System.out.println(e.getLocalizedMessage());
//    }
//  }
}