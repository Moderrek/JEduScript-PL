package pl.moderr.eduscript.interpreter.statements;

import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.type.Value;

public class ElseIfStatement implements Expression {

  private Expression condition;
  private ExpressionBlock block;

  @Override
  public Value evaluate(ImpactEnvironment environment) throws Exception {
    return null;
  }
}
