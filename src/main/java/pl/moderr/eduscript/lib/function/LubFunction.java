package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.BoolType;
import pl.moderr.eduscript.interpreter.type.ValueType;

public class LubFunction extends Function<BoolType> {
  @Override
  public BoolType invoke() throws Exception {
    assertArgs(2);
    boolean a = arg(0, BoolType.class).orElseThrow().toBool();
    boolean b = arg(1, BoolType.class).orElseThrow().toBool();
    boolean c = a || b;
    return BoolType.of(c);
  }

  @Override
  public String getName() {
    return "lub";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
