package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.DecimalValue;
import pl.moderr.impactscript.interpreter.type.IntegerType;
import pl.moderr.impactscript.interpreter.type.ValueType;

public class FibFunction extends Function<IntegerType> {

  private long fib(long n) throws Exception {
    if (n < 0) throw new Exception("Invalid number");
    if (n == 0) return 0;
    if (n == 1) return 1;
    long[] tab = {0, 1};
    for (long i = 2; i < n; i += 1) {
      long sum = tab[0] + tab[1];
      tab[0] = tab[1];
      tab[1] = sum;
    }
    return tab[0] + tab[1];
  }

  @Override
  public IntegerType invoke() throws Exception {
    assertArgs(1);
    assertArgType(0, ValueType.INTEGER);
    long n = arg(0, IntegerType.class).orElseThrow().toLong();
    long result = fib(n);
    return IntegerType.of(result);
  }

  @Override
  public String getName() {
    return "fib";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
