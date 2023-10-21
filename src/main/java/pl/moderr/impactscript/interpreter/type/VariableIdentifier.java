package pl.moderr.impactscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.ImpactEnvironment;

public record VariableIdentifier(String identifier) implements Value {

  @Override
  public Value evaluate(@NotNull ImpactEnvironment environment) throws Exception {
    if (!environment.hasDefinedVariable(identifier)) throw new Exception("Not defined variable " + identifier);
    return environment.getVariable(identifier);
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getTypeName() {
    return "identifier";
  }

  @Override
  public ValueType getType() {
    return ValueType.IDENTIFIER;
  }

}
