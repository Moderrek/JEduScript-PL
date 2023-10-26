package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;

public final class UnitType extends Value {

  @Contract(value = " -> new", pure = true)
  public static @NotNull UnitType empty() {
    return new UnitType();
  }

  @Contract(pure = true)
  @Override
  public @NotNull String toString() {
    return "()";
  }

  @Override
  public ValueType getType() {
    return ValueType.UNIT;
  }
}
