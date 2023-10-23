package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.exception.FunctionError;
import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.*;

public class CalkFunction extends Function<IntegerType> {
  @Override
  public IntegerType invoke() throws Exception {
    assertArgs(1);
    Value value = arg(0, Value.class).orElseThrow();
    if(value instanceof BoolType bool) {
      return bool.toBool() ? IntegerType.of(1) : IntegerType.of(0);
    }
    if(value instanceof IntegerType integer) {
      return integer;
    }
    if(value instanceof DecimalType decimal) {
      return IntegerType.of(decimal.toLong());
    }
    if(value instanceof StringType str) {
      return IntegerType.of(Long.parseLong(str.toString()));
    }
    throw new FunctionError("unknown value");
  }

  @Override
  public String getName() {
    return "calk";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
