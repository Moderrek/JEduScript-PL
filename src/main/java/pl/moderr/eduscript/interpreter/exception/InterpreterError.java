package pl.moderr.eduscript.interpreter.exception;

public abstract class InterpreterError extends ESException{
  public InterpreterError(String s) {
    super(s);
  }

  public abstract void printError();
}
