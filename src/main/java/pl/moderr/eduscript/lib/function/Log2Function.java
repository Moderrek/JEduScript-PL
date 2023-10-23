package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;

import static pl.moderr.eduscript.interpreter.type.ValueType.DECIMAL;

public class Log2Function extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        double base = arg(0, DecimalType.class).orElseThrow().toDouble();
        double result = Math.log(base) / Math.log(2);
        return DecimalType.of(result);
    }

    @Override
    public String getName() {
        return "log2";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
