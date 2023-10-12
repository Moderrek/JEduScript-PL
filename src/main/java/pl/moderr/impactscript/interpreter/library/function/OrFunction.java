package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.BoolType;
import pl.moderr.impactscript.interpreter.type.LogicValue;
import pl.moderr.impactscript.interpreter.type.ValueType;

public class OrFunction extends Function<BoolType> {
  @Override
  public BoolType invoke() throws Exception {
    assertArgs(2);
    assertArgType(0, ValueType.BOOLEAN);
    assertArgType(1, ValueType.BOOLEAN);
    boolean a = arg(0, LogicValue.class).orElseThrow().toBool();
    boolean b = arg(1, LogicValue.class).orElseThrow().toBool();
    boolean c = a || b;
    return BoolType.of(c);
  }

  @Override
  public String getName() {
    return "or";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
