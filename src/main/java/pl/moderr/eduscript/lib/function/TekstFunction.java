package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.StringType;

public class TekstFunction extends Function<StringType> {
  @Override
  public StringType invoke() throws Exception {
    assertArgs(1);
    String value = arg(0, StringType.class).orElseThrow().toString();
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
