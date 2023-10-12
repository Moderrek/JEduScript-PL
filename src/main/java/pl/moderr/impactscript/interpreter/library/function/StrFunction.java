package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.StringType;
import pl.moderr.impactscript.interpreter.type.Value;

public class StrFunction extends Function<StringType> {
  @Override
  public StringType invoke() throws Exception {
    assertArgs(1);
    String value = arg(0, Value.class).toString();
    return StringType.of(value);
  }

  @Override
  public String getName() {
    return "str";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
