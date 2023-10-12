package pl.moderr.impactscript.interpreter.library.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.StringType;
import pl.moderr.impactscript.interpreter.type.Value;

public class TypeofFunction extends Function<StringType> {
    @Override
    public StringType invoke() throws Exception {
        assertArgs(1);
        String typeName = arg(0, Value.class).orElseThrow().getTypeName();
        return StringType.of(typeName);
    }

    @Override
    public String getName() {
        return "typeof";
    }

    @Override
    public boolean isNative() {
        return true;
    }
}
