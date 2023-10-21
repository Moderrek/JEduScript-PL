package pl.moderr.impactscript.interpreter.statements;

import pl.moderr.impactscript.interpreter.ImpactEnvironment;
import pl.moderr.impactscript.interpreter.type.BoolType;
import pl.moderr.impactscript.interpreter.type.UnitType;
import pl.moderr.impactscript.interpreter.type.Value;

import java.util.List;

public class IfStatement implements Expression {

  private Expression condition;
  private ExpressionBlock block;

  public IfStatement(Expression condition, ExpressionBlock block) {
    this.condition = condition;
    this.block = block;
  }

  @Override
  public Value evaluate(ImpactEnvironment scope) throws Exception {
    boolean execute;
    if (condition.evaluate(scope) instanceof BoolType boolType) {
      execute = boolType.toBool();
    } else {
      throw new Exception("adasdas if condifdntin invalid type");
    }
    if (execute) block.evaluate(scope);
    return UnitType.empty();
  }
}
