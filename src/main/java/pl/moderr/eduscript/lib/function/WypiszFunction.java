package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;

public class WypiszFunction extends Function<UnitType> {
  @Override
  public UnitType invoke() throws Exception {
    assertArgs(1);
    System.out.println(arg(0, Value.class).orElseThrow());
    return UnitType.empty();
  }

  @Override
  public String getName() {
    return "wypisz";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
