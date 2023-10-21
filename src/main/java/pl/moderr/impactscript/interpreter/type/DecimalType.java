package pl.moderr.impactscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.ImpactEnvironment;

import java.math.BigDecimal;

public class DecimalType implements DecimalValue {

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

  public static DecimalType of(BigDecimal value) {
    return new DecimalType(value);
  }

  @Override
  public Value evaluate(ImpactEnvironment scope) {
    return this;
  }

  @Override
  public String getTypeName() {
    return "float";
  }

  @Override
  public ValueType getType() {
    return ValueType.DECIMAL;
  }

  @Override
  public int toInt() {
    return value.intValue();
  }

  @Override
  public long toLong() {
    return value.longValue();
  }

  @Override
  public double toDouble() {
    return value.doubleValue();
  }

  @Override
  public BigDecimal toDecimal() {
    return value;
  }

  public boolean isIntegerValue() {
    return value.stripTrailingZeros().scale() <= 0;
  }

  @Override
  public String toString() {
    return value.stripTrailingZeros().toPlainString();
  }

}
