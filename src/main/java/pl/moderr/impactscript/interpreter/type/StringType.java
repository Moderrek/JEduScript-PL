package pl.moderr.impactscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class StringType implements StringValue {

  private final String content;

  public StringType(String value) {
    this.content = value;
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull StringType of(String value) {
    return new StringType(value);
  }

  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public String toString() {
    return content;
  }

  @Override
  public int getLength() {
    return content.length();
  }

  @Override
  public String getTypeName() {
    return "string";
  }

  @Override
  public ValueType getType() {
    return ValueType.STRING;
  }
}