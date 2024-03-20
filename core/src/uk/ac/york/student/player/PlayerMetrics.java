package uk.ac.york.student.player;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * The PlayerMetrics class encapsulates the metrics related to a player.
 * It includes metrics such as energy, happiness, and study level.
 * It also provides methods to get these metrics and to dispose them when they are no longer needed.
 */
@Getter
public final class PlayerMetrics {
    /**
     * The energy metric of the player.
     */
    private final PlayerEnergy energy = new PlayerEnergy();

    /**
     * The happiness metric of the player.
     */
    private final PlayerHappiness happiness = new PlayerHappiness();

    /**
     * The study level metric of the player.
     */
    private final PlayerStudyLevel studyLevel = new PlayerStudyLevel();

    /**
     * Get the list of all player metrics.
     * @return An unmodifiable list of PlayerMetric objects.
     */
    @Contract(value = " -> new", pure = true)
    public @Unmodifiable List<PlayerMetric> getMetrics() {
        return List.of(energy, happiness, studyLevel);
    }

    /**
     * Dispose the resources related to the player metrics when they are no longer needed.
     */
    public void dispose() {
        energy.dispose();
        happiness.dispose();
        studyLevel.dispose();
    }
}