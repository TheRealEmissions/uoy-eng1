package uk.ac.york.student.utils;

import java.util.EnumMap;
import java.util.function.Supplier;

public class EnumMapOfSuppliers<T extends Enum<T>, U> extends EnumMap<T, Supplier<U>> {
    public EnumMapOfSuppliers(Class<T> keyType) {
        super(keyType);
    }

    public U getResult(T key) {
        Supplier<U> uSupplier = get(key);
        return uSupplier == null ? null : uSupplier.get();
    }
}
