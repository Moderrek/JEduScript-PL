package pl.moderr.eduscript.interpreter.statements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.Variable;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;
import pl.moderr.eduscript.interpreter.type.VariableIdentifier;

public final class VariableAssign implements Expression {

  private final VariableIdentifier identifier;
  private final Expression value;
  private final boolean mutable;

  public VariableAssign(VariableIdentifier value, Expression op, boolean mutable) {
    this.identifier = value;
    this.value = op;
    this.mutable = mutable;
  }

  @Override
  public @NotNull @Unmodifiable Value evaluate(@NotNull ImpactEnvironment scope) throws Exception {
    Variable variable_left;
    if (!scope.hasDefinedVariable(identifier.identifier())) {
      Value e_right = value.evaluate(scope);
      Value init;
      if (e_right instanceof Variable variable_right) {
        init = variable_right.getValue();
      } else {
        init = e_right;
      }
      scope.defineVariable(identifier.identifier(), mutable, init);
    } else {
      variable_left = (Variable) identifier.evaluate(scope);
      Value e_right = value.evaluate(scope);
      if (e_right instanceof Variable variable_right) {
        variable_left.setValue(variable_right.getValue());
      } else {
        variable_left.setValue(e_right);
      }
    }

    return UnitType.empty();
  }
}
