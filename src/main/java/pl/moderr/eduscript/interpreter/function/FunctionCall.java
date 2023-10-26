package pl.moderr.eduscript.interpreter.function;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.exception.NameErr;
import pl.moderr.eduscript.interpreter.statements.Expression;
import pl.moderr.eduscript.interpreter.type.Token;
import pl.moderr.eduscript.interpreter.type.Value;

import java.util.ArrayList;

public class FunctionCall implements Expression {

  private final String functionName;
  private final ArrayList<Expression> arguments = new ArrayList<>();

  public FunctionCall(@NotNull Token token) {
    this.functionName = token.value();
  }

  public String getFunctionName() {
    return functionName;
  }

  public void addArgument(Expression expression) {
    arguments.add(expression);
  }

  public ArrayList<Expression> getArguments() {
    return arguments;
  }

  @Override
  public Value evaluate(@NotNull ImpactEnvironment scope) throws Exception {
    if (scope.hasDefinedFunction(functionName)) {
      return scope.getFunction(functionName).preInvoke(scope, arguments);
    }
    else throw new NameErr("unknown function '" + functionName + "'");
  }
}
