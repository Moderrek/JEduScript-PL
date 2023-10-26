package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;

import java.util.Optional;

/**
 * Represents <b>mutable</b> EduScript str type
 */
public final class StringType extends Value {

  private final StringBuilder content;

  public StringType(String value) {
    this.content = new StringBuilder(value);
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull StringType of(String value) {
    return new StringType(value);
  }

  @Contract(pure = true)
  @Override
  public @NotNull String toString() {
    return content.toString();
  }

  public int getLength() {
    return content.length();
  }

  @Contract(pure = true)
  public @NotNull String getContent() {
    return content.toString();
  }

  @Contract(" -> new")
  @Override
  public @NotNull IntegerType operatorLength() {
    return IntegerType.of(content.length());
  }

  @Override
  public @NotNull BoolType operatorEqual(Value right, ImpactEnvironment scope) throws Exception {
    StringType value = Value.assertType(Value.safeCast(right, scope, StringType.class), StringType.class);
    return BoolType.of(value.content.toString().contentEquals(content));
  }

  @Override
  public @NotNull Value operatorPlus(@NotNull Value right, ImpactEnvironment scope) {
    String org = content.toString();
    String append = right.toString();
    return StringType.of(org + append);
  }

  @Override
  public ValueType getType() {
    return ValueType.STRING;
  }

}
