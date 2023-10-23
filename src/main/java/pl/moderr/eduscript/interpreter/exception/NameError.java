package pl.moderr.eduscript.interpreter.exception;

import pl.moderr.eduscript.lexer.Position;

public class NameError extends InterpreterError {

  private final String fileName;
  private final String line;
  private final Position position;
  private final int length;

  public NameError(String fileName, String line, Position position, int length, String message) {
    super("NameErr: " + message);
    this.fileName = fileName;
    this.line = line;
    this.position = position;
    this.length = length;
  }

  public static NameError notDefined(String fileName, String line, Position position, int length, String identifier) {
    return new NameError(fileName, line, position, length, "name '" + identifier + "' is not defined.");
  }

  public void printUnderline(Position position, int length) {
    StringBuilder buffer = new StringBuilder();
    buffer.append(" ".repeat(Math.max(0, position.getCol())));
    buffer.append("^".repeat(Math.max(0, length)));
    System.err.println(buffer.toString());
  }

  @Override
  public void printError() {
    System.err.println("Traceback");
    if (line != null) {
      System.err.println("File: \"" + fileName + "\" line " + (position.getRow()+1));
      System.err.println(line);
      printUnderline(position, length);
    }
    System.err.println(getMessage());
  }

}
