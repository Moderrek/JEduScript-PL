package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.exception.TypeErr;
import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.*;

public class PierwiastekFunction extends Function<DecimalType> {

  @Override
  public DecimalType invoke() throws Exception {
    assertArgs(1);
    double a;
    if (arg(0, Value.class).orElseThrow() instanceof DecimalType) {
      a = arg(0, DecimalType.class).orElseThrow().toDouble();
    } else if (arg(0, Value.class).orElseThrow() instanceof IntegerType) {
      a = arg(0, IntegerType.class).orElseThrow().toLong();
    } else {
      throw new TypeErr("expected number, got " + arg(0, Value.class).orElseThrow().getTypeName());
    }
    double sqrt = Math.sqrt(a);
    return DecimalType.of(sqrt);
  }

  @Override
  public String getName() {
    return "pierwiastek";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
