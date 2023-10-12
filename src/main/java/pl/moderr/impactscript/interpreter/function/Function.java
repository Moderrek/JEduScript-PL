package pl.moderr.impactscript.interpreter.function;

import pl.moderr.impactscript.interpreter.Parser;
import pl.moderr.impactscript.interpreter.exception.FunctionError;
import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.statements.Expression;
import pl.moderr.impactscript.interpreter.type.Value;
import pl.moderr.impactscript.interpreter.type.ValueType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public abstract class Function<R extends Value> {

  protected Parser scope;
  protected ArrayList<Expression> arguments;
  protected HashMap<Expression, Value> argsValueMap;

  public R preInvoke(Parser scope, ArrayList<Expression> arguments) throws Exception {
    this.scope = scope;
    this.arguments = arguments;
    this.argsValueMap = new HashMap<>();
    return invoke();
  }
  public abstract R invoke() throws Exception;

  public abstract String getName();

  public abstract boolean isNative();

  protected void assertArgs(int argCount) {
    int givenArguments = arguments.size();
    if (argCount == 0 && givenArguments != argCount) throw new FunctionError(MessageFormat.format("{0} expected {1} args, got {2}", getName(), argCount, givenArguments));
    if (givenArguments < argCount) throw new FunctionError(MessageFormat.format("{0} expected {1} args, got {2}", getName(), argCount, givenArguments));
    if (givenArguments > argCount) throw new FunctionError(MessageFormat.format("{0} expected {1} args, got {2}", getName(), argCount, givenArguments));
  }

  protected void assertArgType(int index, ValueType expected) throws Exception {
    ValueType got = arg(index, Value.class).orElseThrow().getType();
    if (expected != got) throw new TypeErr(MessageFormat.format("expected {0}, got {1}", expected.toString(), got.toString()));
  }

  protected <T extends Value> Optional<T> arg(int index, Class<T> type) throws Exception {
    if (index >= arguments.size()) return Optional.empty();
    Expression expression = arguments.get(index);
    if(argsValueMap.containsKey(expression)) {
      Value value = argsValueMap.get(expression);
      try {
        return Optional.of((T) value);
      } catch (ClassCastException exception) {
        throw new TypeErr("expected " + type.toString() + ", got " + value.getClass().toString());
      }
    }
    Value value = arguments.get(index).evaluate();
    argsValueMap.put(expression, value);
    return Optional.of((T) value);
  }

  protected Value invokeFunction(String name) throws Exception {
    return scope.callFunction(name);
  }
  protected Value invokeFunction(String name, Expression... args) throws Exception {
    return scope.callFunction(name, args);
  }

}
