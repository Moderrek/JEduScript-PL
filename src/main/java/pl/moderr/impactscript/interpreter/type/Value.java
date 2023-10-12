package pl.moderr.impactscript.interpreter.type;

import pl.moderr.impactscript.interpreter.statements.Expression;

public interface Value extends Expression {
  String getTypeName();

  ValueType getType();

  String toString();

  static <T extends Value> T cast(Value from, Class<T> to) {
    return (T) from;
  }
}
