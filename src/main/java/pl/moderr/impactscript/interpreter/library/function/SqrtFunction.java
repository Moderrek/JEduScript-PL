package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.*;

public class SqrtFunction extends Function<DecimalType> {

  @Override
  public DecimalType invoke() throws Exception {
    assertArgs(1);
    double a;
    if (arg(0, Value.class).orElseThrow() instanceof DecimalValue) {
      a = arg(0, DecimalValue.class).orElseThrow().toDouble();
    } else if (arg(0, Value.class).orElseThrow() instanceof IntegerValue) {
      a = arg(0, IntegerType.class).orElseThrow().toLong();
    } else {
      throw new TypeErr("expected number, got " + arg(0, Value.class).orElseThrow().getTypeName());
    }
    double sqrt = Math.sqrt(a);
    return DecimalType.of(sqrt);
  }

  @Override
  public String getName() {
    return "sqrt";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
