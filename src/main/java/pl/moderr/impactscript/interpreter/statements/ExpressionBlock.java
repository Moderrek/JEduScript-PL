package pl.moderr.impactscript.interpreter.statements;

import pl.moderr.impactscript.interpreter.ImpactEnvironment;
import pl.moderr.impactscript.interpreter.type.UnitType;
import pl.moderr.impactscript.interpreter.type.Value;

import java.util.List;

public class ExpressionBlock implements Expression{

  private List<Expression> expressions;

  public ExpressionBlock(List<Expression> expressions) {
    this.expressions = expressions;
  }

  @Override
  public Value evaluate(ImpactEnvironment environment) throws Exception {
    for (Expression expression : expressions) {
      expression.evaluate(environment);
    }
    return UnitType.empty();
  }
}
