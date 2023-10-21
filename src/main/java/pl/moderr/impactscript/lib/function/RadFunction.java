package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.DecimalType;
import pl.moderr.impactscript.interpreter.type.DecimalValue;
import pl.moderr.impactscript.interpreter.type.ValueType;

public class RadFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        assertArgType(0, ValueType.DECIMAL);
        double degrees = arg(0, DecimalValue.class).orElseThrow().toDouble();
        double pi = ((DecimalValue)invokeFunction("pi")).toDouble();
        double radians = degrees * pi / 180;
        return DecimalType.of(radians);
    }

    @Override
    public String getName() {
        return "rad";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
