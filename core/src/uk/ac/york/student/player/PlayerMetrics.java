package uk.ac.york.student.player;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@Getter
public final class PlayerMetrics {
    private final PlayerEnergy energy = new PlayerEnergy();
    private final PlayerHappiness happiness = new PlayerHappiness();
    private final PlayerStudyLevel studyLevel = new PlayerStudyLevel();

    @Contract(value = " -> new", pure = true)
    public @Unmodifiable List<PlayerMetric> getMetrics() {
        return List.of(energy, happiness, studyLevel);
    }

    public void dispose() {
        energy.dispose();
        happiness.dispose();
        studyLevel.dispose();
    }
}
