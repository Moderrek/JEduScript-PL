package pl.moderr.impactscript.interpreter.type;

public class UnitType implements Value {

  @Override
  public Value evaluate() throws Exception {
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
