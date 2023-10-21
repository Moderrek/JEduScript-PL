package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.DecimalValue;
import pl.moderr.impactscript.interpreter.type.IntegerType;
import pl.moderr.impactscript.interpreter.type.ValueType;

public class FacFunction extends Function<IntegerType> {

  private long fac(long n) throws Exception {
    if (n < 0) throw new Exception("Invalid number");
    if (n == 0) return 1;
    long a = 1;
    for (long i = 1; i <= n; i += 1) {
      a *= i;
    }
    return a;
  }

  @Override
  public IntegerType invoke() throws Exception {
    assertArgs(1);
    assertArgType(0, ValueType.INTEGER);
    long n = arg(0, IntegerType.class).orElseThrow().toLong();
    long result = fac(n);
    return IntegerType.of(result);
  }

  @Override
  public String getName() {
    return "fac";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
