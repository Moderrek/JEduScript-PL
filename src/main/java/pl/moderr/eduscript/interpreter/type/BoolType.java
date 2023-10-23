package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;

import java.util.Optional;

public class BoolType extends Value {

  public static BoolType TRUE = BoolType.of(true);
  public static BoolType FALSE = BoolType.of(false);

  private final boolean bool;

  public BoolType(boolean bool) {
    this.bool = bool;
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull BoolType of(boolean bool) {
    return new BoolType(bool);
  }

  @Contract("_ -> new")
  public static @NotNull BoolType negate(@NotNull BoolType value) {
    return new BoolType(!value.bool);
  }

  @Override
  public String toString() {
    return bool ? "prawda" : "falsz";
  }

  @Override
  public BoolType operatorEqual(@NotNull Value right, ImpactEnvironment scope) throws Exception {
    Value value = right.tryCast(BoolType.class);
    if (value.getType() != ValueType.BOOLEAN) return super.operatorEqual(right, scope);
    return BoolType.of(bool == ((BoolType)value).bool);
  }

  @Override
  public BoolType operatorAnd(@NotNull Value right, ImpactEnvironment scope) throws Exception {
    Value value = right.tryCast(BoolType.class);
    if (value.getType() != ValueType.BOOLEAN) return super.operatorEqual(right, scope);
    return BoolType.of(bool == ((BoolType)value).bool);
  }

  @Override
  public BoolType operatorOr(@NotNull Value right, ImpactEnvironment scope) throws Exception {
    Value value = right.tryCast(BoolType.class);
    if (value.getType() != ValueType.BOOLEAN) return super.operatorEqual(right, scope);
    return BoolType.of(bool || ((BoolType)value).bool);
  }

  @Override
  public <T extends Value> Value tryCast(Class<T> to) {
    if (to == DecimalType.class) {
      return DecimalType.of(bool ? 1 : 0);
    }
    if (to == IntegerType.class) {
      return IntegerType.of(bool ? 1 : 0);
    }
    if (to == StringType.class) {
      return StringType.of(toString());
    }
    return this;
  }

  @Override
  public ValueType getType() {
    return ValueType.BOOLEAN;
  }

  public boolean toBool() {
    return bool;
  }
}
