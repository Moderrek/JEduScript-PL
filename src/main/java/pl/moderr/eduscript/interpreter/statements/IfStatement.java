package pl.moderr.eduscript.interpreter.statements;

import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.type.BoolType;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;

public class IfStatement implements Expression {

  private Expression condition;
  private ExpressionBlock block;

  public IfStatement(Expression condition, ExpressionBlock block, ExpressionBlock blockElse) {
    this.condition = condition;
    this.block = block;
  }

  @Override
  public Value evaluate(ImpactEnvironment scope) throws Exception {
//    boolean execute;
//    if (condition.evaluate(scope) instanceof BoolType boolType) {
//      execute = boolType.toBool();
//    } else {
//      throw new Exception("adasdas if condifdntin invalid type");
//    }
    boolean execute = Value.safeEvaluateCast(condition, scope, BoolType.class).toBool();
    if (execute) block.evaluate(scope);
    return UnitType.empty();
  }
}
