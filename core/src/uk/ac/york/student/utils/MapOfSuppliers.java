package uk.ac.york.student.utils;

import java.util.HashMap;
import java.util.function.Supplier;

public class MapOfSuppliers<T, U> extends HashMap<T, Supplier<U>> {
    public U getResult(T key) {
        Supplier<U> uSupplier = get(key);
        return uSupplier == null ? null : uSupplier.get();
    }
}
