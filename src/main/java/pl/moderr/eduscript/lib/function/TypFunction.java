package pl.moderr.eduscript.lib.function;

import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.StringType;
import pl.moderr.eduscript.interpreter.type.Value;

public class TypFunction extends Function<StringType> {
    @Override
    public StringType invoke() throws Exception {
        assertArgs(1);
        String typeName = arg(0, Value.class).orElseThrow().getTypeName();
        return StringType.of(typeName);
    }

    @Override
    public String getName() {
        return "typ";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
