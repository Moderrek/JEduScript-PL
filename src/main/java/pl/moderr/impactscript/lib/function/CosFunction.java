package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.DecimalValue;

import static pl.moderr.impactscript.interpreter.type.ValueType.DECIMAL;

public class CosFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        assertArgType(0, DECIMAL);
        double radians = ((DecimalValue)invokeFunction("rad", arg(0, DecimalValue.class).orElseThrow())).toDouble();
        double cos = Math.cos(radians);
        return DecimalType.of(cos);
    }

    @Override
    public String getName() {
        return "cos";
    }

    @Override
    public boolean isNative() {
        return true;
    }

}
