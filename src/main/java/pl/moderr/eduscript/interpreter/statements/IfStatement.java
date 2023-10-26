package pl.moderr.eduscript.interpreter.statements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.type.BoolType;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;

public final class IfStatement implements Expression {

  private final Expression condition;
  private final Expression block;

  public IfStatement(Expression condition, Expression block) {
    this.condition = condition;
    this.block = block;
  }

  @Override
  public @NotNull @Unmodifiable Value evaluate(@NotNull ImpactEnvironment scope) throws Exception {
    scope.getHeap().push("if");
    boolean execute = Value.safeEvaluateCast(condition, scope, BoolType.class).toBool();
    if (execute) block.evaluate(scope);
    scope.getHeap().pop();
    return UnitType.empty();
  }
}
