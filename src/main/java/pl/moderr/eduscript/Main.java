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
  public static final String VERSION = "pre-0.61v 18JDK 24.10.2023";

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
      Expression[] expressions;
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
    Expression[] expressions = parser.parseString();
    // save to binary
    saveBinary(fileName + "b", expressions);
    Interpreter interpreter = new Interpreter();
    interpreter.interpret(expressions, fileName);
  }

  private static void saveBinary(String fileName, Expression[] expressions) throws IOException {
    FileOutputStream fos = new FileOutputStream(fileName);
    ObjectOutputStream out = new ObjectOutputStream(fos);
    out.writeObject(expressions);
    fos.close();
  }

  private static void saveTokens(String fileName, @NotNull ArrayList<Token> tokens) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
    for(Token token : tokens) {
      writer.append(token.toString()).append(";");
    }
    writer.close();
  }

  public static void debug(String stmt) throws Exception {
    System.out.println("LEXER");
    Lexer lexer = new Lexer("debug");
    ArrayList<Token> tokens = lexer.lex(stmt, true);
    System.out.println("PARSER");
    Parser parser = new Parser(tokens);
    Expression[] ast = parser.parseString();
    System.out.println("INTERPRETER");
    Interpreter interpreter = new Interpreter();
    System.out.println("RESULT");
    interpreter.interpret(ast, "<debug>");
  }
}