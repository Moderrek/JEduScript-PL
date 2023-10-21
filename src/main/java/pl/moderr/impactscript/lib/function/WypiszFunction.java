package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.UnitType;
import pl.moderr.impactscript.interpreter.type.Value;

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
