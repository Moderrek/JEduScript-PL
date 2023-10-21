package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.StringType;
import pl.moderr.impactscript.interpreter.type.Value;

public class TekstFunction extends Function<StringType> {
  @Override
  public StringType invoke() throws Exception {
    assertArgs(1);
    String value = arg(0, Value.class).toString();
    return StringType.of(value);
  }

  @Override
  public String getName() {
    return "tekst";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
