package uk.ac.york.student.utils;

import com.badlogic.gdx.Gdx;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@UtilityClass
public final class Wait {
    public static void sync(long time, @NotNull TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            Gdx.app.error("Wait", "Interrupted", e);
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull CompletableFuture<Void> async(long time, @NotNull TimeUnit timeUnit) {
        return CompletableFuture
            .runAsync(() -> sync(time, timeUnit))
            .exceptionally(e -> {
                Gdx.app.error("Wait", "Async Exception", e);
                return null;
            });
    }

    public static void asyncBlocking(long time, TimeUnit timeUnit) {
        async(time, timeUnit).join();
    }

}
