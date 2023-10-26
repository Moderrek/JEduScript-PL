package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;

import java.math.BigDecimal;

public final class DecimalType extends Value {

  private final BigDecimal value;

  public DecimalType(BigDecimal value) {
    this.value = value;
  }

  public DecimalType(int value) {
    this.value = BigDecimal.valueOf(value);
  }

  public DecimalType(double value) {
    this.value = BigDecimal.valueOf(value);
  }

  public DecimalType(long value) {
    this.value = BigDecimal.valueOf(value);
  }

  public DecimalType(@NotNull Token token) {
    this(token.value());
  }

  public DecimalType(String value) {
    this.value = BigDecimal.valueOf(Double.parseDouble(value));
  }

  @Contract("_ -> new")
  public static @NotNull DecimalType of(Token token) {
    return new DecimalType(token);
  }

  @Contract("_ -> new")
  public static @NotNull DecimalType of(double number) {
    return new DecimalType(number);
  }

  @Contract("_ -> new")
  public static @NotNull DecimalType of(BigDecimal value) {
    return new DecimalType(value);
  }

  @Override
  public ValueType getType() {
    return ValueType.DECIMAL;
  }

  public int toInt() {
    return value.intValue();
  }

  public long toLong() {
    return value.longValue();
  }

  public double toDouble() {
    return value.doubleValue();
  }

  public BigDecimal toDecimal() {
    return value;
  }

  public boolean isIntegerValue() {
    return value.stripTrailingZeros().scale() <= 0;
  }

  @Override
  public @NotNull String toString() {
    return value.stripTrailingZeros().toPlainString();
  }

  @Override
  public <T extends Value> Value tryCast(Class<T> to) {
    if (to == BoolType.class) {
      return BoolType.of(value.compareTo(BigDecimal.ZERO) != 0);
    }
    if (to == StringType.class) {
      return StringType.of(toString());
    }
    return this;
  }

  @Override
  public BoolType operatorEqual(@NotNull Value right, ImpactEnvironment scope) throws Exception {
    Value value = right.tryCast(DecimalType.class);
    if (value.getType() != ValueType.DECIMAL) return super.operatorEqual(right, scope);
    return BoolType.of(toDouble() == ((DecimalType)value).toDouble());
  }

  @Override
  public Value operatorPlus(Value right, ImpactEnvironment scope) throws Exception {
    if (Value.isType(right, scope, DecimalType.class)) {
      DecimalType to_add = Value.safeCast(right, scope, DecimalType.class);
      return new DecimalType(toLong() + to_add.toDouble());
    }
    if (Value.isType(right, scope, IntegerType.class)) {
      IntegerType to_add = Value.safeCast(right, scope, IntegerType.class);
      return new IntegerType(toLong() + to_add.toLong());
    }
    return super.operatorPlus(right, scope);
  }

  @Override
  public Value operatorMinus(Value right, ImpactEnvironment scope) throws Exception {
    return super.operatorMinus(right, scope);
  }

  @Override
  public Value operatorMultiply(Value right, ImpactEnvironment scope) throws Exception {
    return super.operatorMultiply(right, scope);
  }

  @Override
  public Value operatorDivide(Value right, ImpactEnvironment scope) throws Exception {
    return super.operatorDivide(right, scope);
  }
}
