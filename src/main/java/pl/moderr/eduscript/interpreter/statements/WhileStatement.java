package pl.moderr.eduscript.interpreter.statements;

import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.type.BoolType;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;

public class WhileStatement implements Expression {

  private final Expression condition;
  private final ExpressionBlock block;

  public WhileStatement(Expression condition, ExpressionBlock block) {
    this.condition = condition;
    this.block = block;
  }

  @Override
  public Value evaluate(ImpactEnvironment scope) throws Exception {
    boolean execute = Value.safeEvaluateCast(condition, scope, BoolType.class).toBool();
    while (execute) {
      block.evaluate(scope);
      execute = Value.safeEvaluateCast(condition, scope, BoolType.class).toBool();
    }
    return UnitType.empty();
  }
}
