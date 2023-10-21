package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.*;

public class ZaprzeczFunction extends Function<Value> {
  @Override
  public Value invoke() throws Exception {
    assertArgs(1);
    Value val = arg(0, Value.class).orElseThrow();
    if (val instanceof BoolType bool) {
      return BoolType.of(!bool.toBool());
    }
    if (val instanceof IntegerType integer) {
      return IntegerType.of(-integer.toLong());
    }
    if (val instanceof DecimalType decimal) {
      return DecimalType.of(decimal.toDecimal().negate());
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public String getName() {
    return "zaprzecz";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
