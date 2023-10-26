package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.ImpactEnvironment;

public final class IntegerType extends Value {
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
    @Contract("_, _ -> new")
    public static @NotNull IntegerType add(@NotNull IntegerType val, @NotNull IntegerType val2) {
        return IntegerType.of(val.toLong() + val2.toLong());
    }
    public int toInt() {
        return (int) value;
    }

    public long toLong() {
        return value;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return String.valueOf(value);
    }

    @Override
    public <T extends Value> Value tryCast(Class<T> to) {
        if (to == BoolType.class) {
            return BoolType.of(value != 0);
        }
        if (to == DecimalType.class) {
            return DecimalType.of(value);
        }
        if (to == StringType.class) {
            return StringType.of(toString());
        }
        return this;
    }
    @Override
    public ValueType getType() {
        return ValueType.INTEGER;
    }


    @Override
    public Value operatorPlus(Value right, ImpactEnvironment scope) throws Exception {
        // integer to integer priority
        if (Value.isType(right, scope, IntegerType.class)) {
            IntegerType to_add = Value.safeCast(right, scope, IntegerType.class);
            return new IntegerType(toLong() + to_add.toLong());
        }
        if (Value.isType(right, scope, DecimalType.class)) {
            DecimalType to_add = Value.safeCast(right, scope, DecimalType.class);
            return new DecimalType(toLong() + to_add.toDouble());
        }
        return super.operatorPlus(right, scope);
    }
}
