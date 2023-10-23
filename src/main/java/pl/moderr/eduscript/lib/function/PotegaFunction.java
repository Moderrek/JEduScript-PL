package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.exception.TypeErr;
import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.*;

public class PotegaFunction extends Function<DecimalType> {

  @Override
  public DecimalType invoke() throws Exception {
    assertArgs(2);
    double a;
    if (arg(0, Value.class).orElseThrow() instanceof DecimalType) {
      a = arg(0, DecimalType.class).orElseThrow().toDouble();
    } else if (arg(0, Value.class).orElseThrow() instanceof IntegerType) {
      a = arg(0, IntegerType.class).orElseThrow().toLong();
    } else {
      throw new TypeErr("expected number, got " + arg(0, Value.class).orElseThrow().getTypeName());
    }

    double b;
    if (arg(1, Value.class).orElseThrow() instanceof DecimalType) {
      b = arg(1, DecimalType.class).orElseThrow().toDouble();
    } else if (arg(1, Value.class).orElseThrow() instanceof IntegerType) {
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
