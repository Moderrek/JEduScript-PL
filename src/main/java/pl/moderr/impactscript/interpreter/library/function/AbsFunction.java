package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.*;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class AbsFunction extends Function<Value> {
    @Override
    public Value invoke() throws Exception {
        assertArgs(1);
        Value value = arg(0, Value.class).orElseThrow();
        if (value instanceof DecimalType decimal) {
            return DecimalType.of(decimal.toDecimal().abs());
        }
        if (value instanceof IntegerType integer) {
            return IntegerType.of(Math.abs(integer.toLong()));
        }
        throw new TypeErr(MessageFormat.format("expected number, got {0}", value.getTypeName()));
    }

    @Override
    public String getName() {
        return "abs";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
