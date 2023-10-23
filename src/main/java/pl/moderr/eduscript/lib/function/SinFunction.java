package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;

public class SinFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
//        assertArgType(0, DECIMAL);
        DecimalType arg = arg(0, DecimalType.class).orElseThrow();
        double radians = ((DecimalType)invokeFunction("rad", arg)).toDouble();
        double sin = Math.sin(radians);
        return DecimalType.of(sin);
    }

    @Override
    public String getName() {
        return "sin";
    }

    @Override
    public boolean isNative() {
        return true;
    }

}
