package pl.moderr.impactscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BoolType implements LogicValue {

  public static BoolType TRUE = BoolType.of(true);
  public static BoolType FALSE = BoolType.of(false);

  private final boolean bool;

  public BoolType(boolean bool) {
    this.bool = bool;
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull BoolType of(boolean bool) {
    return new BoolType(bool);
  }

  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public String toString() {
    return bool ? "true" : "false";
  }

  @Override
  public String getTypeName() {
    return "bool";
  }

  @Override
  public ValueType getType() {
    return ValueType.BOOLEAN;
  }

  @Override
  public boolean toBool() {
    return bool;
  }
}
