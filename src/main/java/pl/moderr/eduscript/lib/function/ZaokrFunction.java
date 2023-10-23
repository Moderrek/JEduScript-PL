package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;
import pl.moderr.eduscript.interpreter.type.Value;

public class ZaokrFunction extends Function<Value> {

    @Override
    public Value invoke() throws Exception {
        assertArgs(1);
        DecimalType value = arg(0, DecimalType.class).orElseThrow();
        return DecimalType.of(Math.round(value.toDouble()));
    }

    @Override
    public String getName() {
        return "zaokr";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
