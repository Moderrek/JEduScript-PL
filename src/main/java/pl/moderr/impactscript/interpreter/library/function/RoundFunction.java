package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.*;

import java.math.MathContext;

import static pl.moderr.impactscript.interpreter.type.ValueType.DECIMAL;

public class RoundFunction extends Function<Value> {
    @Override
    public Value invoke() throws Exception {
        assertArgs(1);
        Value value = arg(0, Value.class).orElseThrow();
        if (value instanceof DecimalType decimal) {
//            IntegerType precision = arg(1, IntegerType.class).orElse(IntegerType.of(0));
            return DecimalType.of(Math.round(decimal.toDouble()));
        }
        if (value instanceof IntegerValue integer) {
            return integer;
        }
        throw new TypeErr("expected number, got " + value.getTypeName());
    }

    @Override
    public String getName() {
        return "round";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
