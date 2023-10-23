package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;

import static pl.moderr.eduscript.interpreter.type.ValueType.DECIMAL;

public class TanFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        double radians = ((DecimalType)invokeFunction("rad", arg(0, DecimalType.class).orElseThrow())).toDouble();
        double tan = Math.tan(radians);
        return DecimalType.of(tan);
    }

    @Override
    public String getName() {
        return "tan";
    }

    @Override
    public boolean isNative() {
        return true;
    }

}
