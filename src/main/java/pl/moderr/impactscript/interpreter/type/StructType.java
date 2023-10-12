package pl.moderr.impactscript.interpreter.type;

public class StructType implements StructValue {
  @Override
  public Value evaluate() throws Exception {
    return this;
  }

  @Override
  public String getTypeName() {
    return "struct";
  }

  @Override
  public ValueType getType() {
    return ValueType.STRUCT;
  }

  @Override
  public String toString() {
    return "struct";
  }
}
