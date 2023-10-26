package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.exception.TypeErr;
import pl.moderr.eduscript.interpreter.statements.Expression;

import javax.naming.OperationNotSupportedException;

public abstract class Value implements Expression {
  public abstract ValueType getType();
  public abstract String toString();

  @Override
  public Value evaluate(ImpactEnvironment environment) throws Exception {
    return this;
  }

  public String getTypeName() {
    return ValueType.TypeToName(getType());
  }

  public IntegerType operatorLength() throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }
  public BoolType operatorEqual(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }
  public Value operatorPlus(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }
  public Value operatorMinus(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }
  public Value operatorMultiply(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }
  public Value operatorDivide(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }
  public BoolType operatorAnd(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }
  public BoolType operatorOr(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }
  public BoolType operatorContains(Value right, ImpactEnvironment scope) throws Exception {
    throw new OperationNotSupportedException();
  }

  public <T extends Value> Value tryCast(Class<T> to) {
    return this;
  }

  public static <T extends Value> @NotNull T safeEvaluateCast(@NotNull Expression expression, @NotNull ImpactEnvironment scope, @NotNull Class<T> type) throws Exception {
    Value value = expression.evaluate(scope);
    if (!value.getClass().equals(type)) {
      // try cast
      Value casted = value.tryCast(type);
      if (value == casted) throw new TypeErr("expected " + type.getSimpleName() + ", got " + value.getTypeName());
      return (T) casted;
    }
    return (T) value;
  }

  public static <T extends Value> @NotNull T safeCast(@NotNull Value value, @NotNull ImpactEnvironment scope, @NotNull Class<T> type) throws Exception {
    if (type == Value.class) return (T) value;
    if (!value.getClass().equals(type)) {
      // try cast
      Value casted = value.tryCast(type);
      if (value == casted) throw new TypeErr("expected " + type.getSimpleName() + ", got " + value.getTypeName());
      return (T) casted;
    }
    return (T) value;
  }

  public static <T extends Value> @NotNull boolean isType(@NotNull Value value, @NotNull ImpactEnvironment scope, @NotNull Class<T> type) throws Exception {
    if (type == Value.class) return true;
    if (!value.getClass().equals(type)) {
      // try cast
      Value casted = value.tryCast(type);
      return value != casted;
    }
    return true;
  }

  public static <T extends Value> T assertType(Value value, Class<T> type) throws TypeErr {
    if (type == Value.class) return (T) value;
    if (!value.getClass().equals(type)) throw new TypeErr("expected " + type.getSimpleName() + ", got " + value.getTypeName());
    return (T) value;
  }

}
