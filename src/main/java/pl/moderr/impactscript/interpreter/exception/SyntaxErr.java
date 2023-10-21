package pl.moderr.impactscript.interpreter.exception;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.lexer.Position;

public class SyntaxErr extends Exception {

  public SyntaxErr(@NotNull Object message) {
    super("SyntaxErr: " + String.valueOf(message).toLowerCase());
  }

}
