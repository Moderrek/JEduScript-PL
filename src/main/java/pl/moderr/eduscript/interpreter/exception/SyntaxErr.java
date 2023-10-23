package pl.moderr.eduscript.interpreter.exception;

import org.jetbrains.annotations.NotNull;

public class SyntaxErr extends Exception {

  public SyntaxErr(@NotNull Object message) {
    super("SyntaxErr: " + String.valueOf(message).toLowerCase());
  }

}
