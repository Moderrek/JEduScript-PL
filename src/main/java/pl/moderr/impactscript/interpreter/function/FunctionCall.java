package pl.moderr.impactscript.interpreter.function;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.Parser;
import pl.moderr.impactscript.interpreter.statements.Expression;
import pl.moderr.impactscript.interpreter.type.Value;

import java.util.ArrayList;

public class FunctionCall implements Expression {

  private final Function<?> function;
  private final Parser scope;
  private final ArrayList<Expression> arguments = new ArrayList<>();

  public FunctionCall(Parser scope, @NotNull Function<?> call) {
    this.function = call;
    this.scope = scope;
  }

  public String getFunctionName() {
    return function.getName();
  }

  public void addArgument(Expression expression) {
    arguments.add(expression);
  }

  public ArrayList<Expression> getArguments() {
    return arguments;
  }

  @Override
  public Value evaluate() throws Exception {
    return function.preInvoke(scope, arguments);
  }
}
