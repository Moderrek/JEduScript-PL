package pl.moderr.eduscript.interpreter.statements;

import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;

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
