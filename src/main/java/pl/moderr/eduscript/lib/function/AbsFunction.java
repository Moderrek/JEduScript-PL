package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.exception.TypeErr;
import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.DecimalType;
import pl.moderr.eduscript.interpreter.type.IntegerType;
import pl.moderr.eduscript.interpreter.type.Value;

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
