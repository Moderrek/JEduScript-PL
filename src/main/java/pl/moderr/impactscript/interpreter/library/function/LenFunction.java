package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.StringType;
import pl.moderr.impactscript.interpreter.type.ValueType;

public class LenFunction extends Function<DecimalType> {
  @Override
  public DecimalType invoke() throws Exception {
    assertArgs(1);
    assertArgType(0, ValueType.STRING);
    int length = arg(0, StringType.class).toString().length();
    return DecimalType.of(length);
  }

  @Override
  public String getName() {
    return "len";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
