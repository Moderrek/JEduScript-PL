package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.DecimalValue;

import static pl.moderr.impactscript.interpreter.type.ValueType.DECIMAL;

public class TanFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        assertArgType(0, DECIMAL);
        double radians = ((DecimalValue)invokeFunction("rad", arg(0, DecimalValue.class).orElseThrow())).toDouble();
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
