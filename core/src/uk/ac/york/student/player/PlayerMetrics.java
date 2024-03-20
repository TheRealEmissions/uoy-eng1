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
     * The {@link MetricType} enum represents the types of metrics related to a player.
     * It includes {@link MetricType#ENERGY}, {@link MetricType#HAPPINESS}, and {@link MetricType#STUDY_LEVEL}.
     */
    @Getter
    public enum MetricType {
        ENERGY,       // Represents the energy level of the player
        HAPPINESS,    // Represents the happiness level of the player
        STUDY_LEVEL;  // Represents the study level of the player
    }

    /**
     * The {@link MetricEffect} enum represents the possible effects on a player's metrics.
     * It includes {@link MetricEffect#INCREASE}, {@link MetricEffect#DECREASE}, and {@link MetricEffect#RESET}.
     */
    public enum MetricEffect {
        INCREASE,    // Represents an increase in a player's metric
        DECREASE,    // Represents a decrease in a player's metric
        RESET;       // Represents resetting a player's metric to its initial value
    }

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