package pl.moderr.impactscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.lexer.Position;

import java.io.Serializable;

public record Token(TokenType type, String value, Position startPos, int length) implements Serializable {
  @Contract(pure = true)
  public static boolean isType(Token token, TokenType @NotNull ... types) {
    for (TokenType type : types) {
      if (token.type == type) return true;
    }
    return false;
  }
}
