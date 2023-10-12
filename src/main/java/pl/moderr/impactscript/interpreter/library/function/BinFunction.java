package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.IntegerType;
import pl.moderr.impactscript.interpreter.type.StringType;
import pl.moderr.impactscript.interpreter.type.ValueType;

public class BinFunction extends Function<StringType> {
  @Override
  public StringType invoke() throws Exception {
    assertArgs(1);
    assertArgType(0, ValueType.INTEGER);
    IntegerType integer = arg(0, IntegerType.class).orElseThrow();
    String bin = Long.toBinaryString(integer.toLong());
    return StringType.of(bin);
  }

  @Override
  public String getName() {
    return "bin";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
