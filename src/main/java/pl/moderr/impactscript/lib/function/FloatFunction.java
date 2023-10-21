package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.IntegerType;
import pl.moderr.impactscript.interpreter.type.Value;

public class FloatFunction extends Function<DecimalType> {
  @Override
  public DecimalType invoke() throws Exception {
    assertArgs(1);
    Value value = arg(0, Value.class).orElseThrow();
    if (value instanceof IntegerType integer) {
      return DecimalType.of(integer.toLong());
    }
    if (value instanceof DecimalType decimal) {
      return decimal;
    }
    throw new TypeErr("expected number, got " + value.getTypeName());
  }

  @Override
  public String getName() {
    return "float";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
