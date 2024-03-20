package uk.ac.york.student.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class StreamUtils {
    @Contract("_ -> new")
    public static <T> @NotNull Stream<T> fromIterator(@NotNull Iterator<T> iterable) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable, 0), false);
    }

    @Contract("_ -> new")
    public static <T> @NotNull Stream<T> parallelFromIterator(@NotNull Iterator<T> iterable) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable, 0), true);
    }
}
