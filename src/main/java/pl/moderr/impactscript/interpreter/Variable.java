package pl.moderr.impactscript.interpreter;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.type.Value;
import pl.moderr.impactscript.interpreter.type.ValueType;

public class Variable implements Value {

  private final String identifier;
  private final boolean mutable;
  private Value value;

  public Variable(String identifier, @NotNull Value value, boolean mutable) {
    this.identifier = identifier;
    this.value = value;
    this.mutable = mutable;
  }

  public String getIdentifier() {
    return identifier;
  }

  public boolean isMutable() {
    return mutable;
  }

  public Value getValue() {
    return value;
  }

  public void setValue(Value value) throws Exception {
    if (!mutable) throw new Exception("variable " + identifier + " is not mutable!");
    this.value = value;
  }

  @Override
  public Value evaluate(ImpactEnvironment environment) throws Exception {
    return this;
  }

  @Override
  public String getTypeName() {
    return value.getTypeName();
  }

  @Override
  public ValueType getType() {
    return value.getType();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
