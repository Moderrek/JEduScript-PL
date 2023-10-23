package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;

import static pl.moderr.eduscript.interpreter.type.ValueType.DECIMAL;

public class LogFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        double val = arg(0, DecimalType.class).orElseThrow().toDouble();
        double result = Math.log10(val);
        return DecimalType.of(result);
    }

    @Override
    public String getName() {
        return "log";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
