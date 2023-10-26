package pl.moderr.eduscript.interpreter.function;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.Variable;
import pl.moderr.eduscript.interpreter.exception.FunctionError;
import pl.moderr.eduscript.interpreter.exception.TypeErr;
import pl.moderr.eduscript.interpreter.statements.Expression;
import pl.moderr.eduscript.interpreter.type.Value;
import pl.moderr.eduscript.interpreter.type.ValueType;
import pl.moderr.eduscript.interpreter.type.VariableIdentifier;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Function<R extends Value> {

  protected ImpactEnvironment scope;
  protected ArrayList<Expression> arguments;
  protected HashMap<Expression, Value> argsValueMap;

  public R preInvoke(@NotNull ImpactEnvironment scope, ArrayList<Expression> arguments) throws Exception {
    this.scope = scope;
    this.arguments = arguments;
    this.argsValueMap = new HashMap<>();
    scope.getHeap().push(getName() + "()");
    R val = invoke();
    scope.getHeap().pop();
    return val;
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
    Value value;
    if(arguments.get(index) instanceof VariableIdentifier var) {
      value = Value.safeCast(((Variable)var.evaluate(scope)).getValue(), scope, type);
    } else {
      value = Value.safeCast(arguments.get(index).evaluate(scope), scope, type);
    }
    argsValueMap.put(expression, value);
    return Optional.of((T) value);
  }

  protected Value invokeFunction(String name) throws Exception {
    return scope.getFunction(name).preInvoke(scope, new ArrayList<>());
  }
  protected Value invokeFunction(String name, Expression... args) throws Exception {
    ArrayList<Expression> arguments = Arrays.stream(args).collect(Collectors.toCollection(ArrayList::new));
    return scope.getFunction(name).preInvoke(scope, arguments);
  }

}
