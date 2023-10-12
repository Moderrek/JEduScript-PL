package pl.moderr.impactscript.interpreter.function;

import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.Value;

public class ConstFunction extends Function {

  private final String name;
  private final Value value;

  public ConstFunction(String name, Value value) {
    this.name = name;
    this.value = value;
  }

  public ConstFunction(String name, double value) {
    this.name = name;
    this.value = DecimalType.of(value);
  }

  public ConstFunction(String name, int value) {
    this.name = name;
    this.value = DecimalType.of(value);
  }

  @Override
  public Value invoke() throws Exception {
    assertArgs(0);
    return value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
