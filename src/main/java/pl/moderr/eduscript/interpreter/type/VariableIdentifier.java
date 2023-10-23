package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.exception.NameError;

import java.util.Objects;

public final class VariableIdentifier extends Value {
  private final String identifier;

  public VariableIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String identifier() {
    return identifier;
  }

  @Override
  public Value evaluate(@NotNull ImpactEnvironment environment) throws Exception {
    if (!environment.hasDefinedVariable(identifier))
      throw NameError.notDefined(null, null, null, identifier.length(), identifier);
    return environment.getVariable(identifier);
  }

  @Override
  public ValueType getType() {
    return ValueType.IDENTIFIER;
  }

  @Override
  public String toString() {
    return identifier;
  }

}
