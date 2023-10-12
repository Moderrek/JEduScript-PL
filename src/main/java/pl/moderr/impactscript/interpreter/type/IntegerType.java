package pl.moderr.impactscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class IntegerType implements IntegerValue {

    private final long value;

    public IntegerType(long value) {
        this.value = value;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull IntegerType of(long value) {
        return new IntegerType(value);
    }

    @Contract("_ -> new")
    public static @NotNull IntegerType of(@NotNull Token token) {
        return new IntegerType(Long.parseLong(token.value()));
    }

    @Override
    public Value evaluate() {
        return this;
    }

    @Override
    public int toInt() {
        return (int) value;
    }

    @Override
    public long toLong() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String getTypeName() {
        return "int";
    }

    @Override
    public ValueType getType() {
        return ValueType.INTEGER;
    }
}
