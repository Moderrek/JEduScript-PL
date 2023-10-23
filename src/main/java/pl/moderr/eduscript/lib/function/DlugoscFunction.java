package pl.moderr.eduscript.lib.function;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.*;

public final class DlugoscFunction extends Function<IntegerType> {

  @Override
  public IntegerType invoke() throws Exception {
    assertArgs(1);
    // TODO exception value doesnt have length method
    return arg(0, Value.class).orElseThrow().operatorLength();
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getName() {
    return "dlugosc";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
