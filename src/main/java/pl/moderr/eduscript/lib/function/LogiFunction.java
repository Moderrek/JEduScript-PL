package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.exception.TypeErr;
import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.*;

import java.text.MessageFormat;

public class LogiFunction extends Function<BoolType> {
  @Override
  public BoolType invoke() throws Exception {
    assertArgs(1);
    Value value = arg(0, Value.class).orElseThrow();
    if(value instanceof BoolType bool) {
      return bool;
    }
    if(value instanceof IntegerType integer) {
      return BoolType.of(integer.toLong() != 0);
    }
    if(value instanceof DecimalType decimal) {
      return BoolType.of(decimal.toDouble() != 0);
    }
    if(value instanceof StringType str) {
      if (str.getContent().equalsIgnoreCase("prawda")) return BoolType.TRUE;
      if (str.getContent().equalsIgnoreCase("falsz")) return BoolType.TRUE;
      return BoolType.of(str.getLength() > 0);
    }
    throw new TypeErr(MessageFormat.format("cannot cast type {0} to bool", value.getTypeName()));
  }

  @Override
  public String getName() {
    return "logi";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
