package pl.moderr.impactscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.ImpactEnvironment;

public class UnitType implements Value {

  @Contract(value = " -> new", pure = true)
  public static @NotNull UnitType empty() {
    return new UnitType();
  }

  @Override
  public Value evaluate(ImpactEnvironment scope) throws Exception {
    return this;
  }

  @Override
  public String toString() {
    return "()";
  }

  @Override
  public String getTypeName() {
    return "unit";
  }

  @Override
  public ValueType getType() {
    return ValueType.UNIT;

  }
}
