package pl.moderr.impactscript;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.Interpreter;
import pl.moderr.impactscript.interpreter.exception.LexerErr;
import pl.moderr.impactscript.interpreter.statements.Expression;
import pl.moderr.impactscript.interpreter.type.Token;
import pl.moderr.impactscript.lexer.Lexer;
import pl.moderr.impactscript.parser.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
  public static void main(String @NotNull [] args) {
//        debug("a = 5\na = a + 1\nprint(a)\nprint(6)");
    try {
      runFile("script.es");
    } catch (LexerErr err) {
      err.printError();
    } catch (Exception e) {
      throw new RuntimeException(e);
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

  public static String readFile(String fileName) throws IOException {
    return Files.readString(Path.of(fileName));
  }

  public static void runFile(String fileName) throws Exception {
    Lexer lexer = new Lexer(fileName);
    ArrayList<Token> tokens = lexer.lex(readFile(fileName), true);
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