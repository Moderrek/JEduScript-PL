package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.DecimalValue;

import static pl.moderr.impactscript.interpreter.type.ValueType.DECIMAL;

public class Log2Function extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        assertArgType(0, DECIMAL);
        double base = arg(0, DecimalValue.class).orElseThrow().toDouble();
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
