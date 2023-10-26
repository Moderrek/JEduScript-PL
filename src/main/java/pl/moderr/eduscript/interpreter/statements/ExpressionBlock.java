package pl.moderr.eduscript.interpreter.statements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;

import java.util.List;

public final class ExpressionBlock implements Expression{

  private final Expression[] expressions;

  public ExpressionBlock(Expression[] expressions) {
    this.expressions = expressions;
  }

  @Override
  public @NotNull @Unmodifiable Value evaluate(@NotNull ImpactEnvironment environment) throws Exception {
    environment.getHeap().push("code block");
    for (Expression expression : expressions) {
      expression.evaluate(environment);
    }
    environment.getHeap().pop();
    return UnitType.empty();
  }
}
