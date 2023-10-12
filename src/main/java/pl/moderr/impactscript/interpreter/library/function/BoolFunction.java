package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.exception.FunctionError;
import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.*;

import java.text.MessageFormat;

public class BoolFunction extends Function<BoolType> {
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
      return BoolType.of(str.getLength() > 0);
    }
    throw new TypeErr(MessageFormat.format("cannot cast type {0} to bool", value.getTypeName()));
  }

  @Override
  public String getName() {
    return "bool";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
