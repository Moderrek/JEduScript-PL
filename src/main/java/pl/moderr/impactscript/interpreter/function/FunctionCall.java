package pl.moderr.impactscript.interpreter.function;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.ImpactEnvironment;
import pl.moderr.impactscript.interpreter.exception.LexerErr;
import pl.moderr.impactscript.interpreter.exception.NameErr;
import pl.moderr.impactscript.interpreter.type.Token;
import pl.moderr.impactscript.parser.Parser;
import pl.moderr.impactscript.interpreter.statements.Expression;
import pl.moderr.impactscript.interpreter.type.Value;

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
      return scope.functions.get(functionName).preInvoke(scope, arguments);
    }
    else throw new NameErr("unknown function '" + functionName + "'");
  }
}
