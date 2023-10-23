package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;

import static pl.moderr.eduscript.interpreter.type.ValueType.DECIMAL;

public class CosFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        assertArgType(0, DECIMAL);
        double radians = ((DecimalType)invokeFunction("rad", arg(0, DecimalType.class).orElseThrow())).toDouble();
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
