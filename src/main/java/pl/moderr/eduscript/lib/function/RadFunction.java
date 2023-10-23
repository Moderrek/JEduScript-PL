package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;

public class RadFunction extends Function<DecimalType> {
    @Override
    public DecimalType invoke() throws Exception {
        assertArgs(1);
        double degrees = arg(0, DecimalType.class).orElseThrow().toDouble();
        double pi = ((DecimalType)invokeFunction("pi")).toDouble();
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
