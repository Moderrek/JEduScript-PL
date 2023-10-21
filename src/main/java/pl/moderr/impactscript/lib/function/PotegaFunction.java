package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.*;

public class PotegaFunction extends Function<DecimalType> {

  @Override
  public DecimalType invoke() throws Exception {
    assertArgs(2);
    double a;
    if (arg(0, Value.class).orElseThrow() instanceof DecimalValue) {
      a = arg(0, DecimalValue.class).orElseThrow().toDouble();
    } else if (arg(0, Value.class).orElseThrow() instanceof IntegerValue) {
      a = arg(0, IntegerType.class).orElseThrow().toLong();
    } else {
      throw new TypeErr("expected number, got " + arg(0, Value.class).orElseThrow().getTypeName());
    }

    double b;
    if (arg(1, Value.class).orElseThrow() instanceof DecimalValue) {
      b = arg(1, DecimalValue.class).orElseThrow().toDouble();
    } else if (arg(1, Value.class).orElseThrow() instanceof IntegerValue) {
      b = arg(1, IntegerType.class).orElseThrow().toLong();
    } else {
      throw new TypeErr("expected number, got " + arg(1, Value.class).orElseThrow().getTypeName());
    }
    double result = Math.pow(a, b);
    return DecimalType.of(result);
  }

  @Override
  public String getName() {
    return "potega";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
