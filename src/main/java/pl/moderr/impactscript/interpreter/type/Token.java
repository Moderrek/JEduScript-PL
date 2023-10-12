package pl.moderr.impactscript.interpreter.type;

import java.io.Serializable;

public record Token(TokenType type, String value, int startPos, int length) implements Serializable {

}
