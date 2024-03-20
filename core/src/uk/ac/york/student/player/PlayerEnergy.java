package uk.ac.york.student.player;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import org.jetbrains.annotations.Range;

public class PlayerEnergy implements PlayerMetric {
    private final ProgressBar progressBar = new ProgressBar(0, 1, 0.1f, false, skin);

    public PlayerEnergy() {
        progressBar.setWidth(200);
        progressBar.setHeight(50);
        progressBar.setAnimateDuration(0.25f);
    }

    private @Range(from=0, to=1) float energy = 1f;
    /**
     * Get the hunger of the player as a float between 0 and 1
     * Where 0 is not hungry and 1 is very hungry
     * @return the hunger of the player
     */
    @Range(from=0, to=1) float getEnergy() {
        return energy;
    }

    /**
     * Set the hunger of the player
     * @param energy the hunger of the player as a float between 0 and 1
     */
    void setEnergy(@Range(from=0, to=1) float energy) {
        this.energy = Math.max(PROGRESS_BAR_MINIMUM, Math.min(1, energy));
    }

    /**
     * Increase the hunger of the player by a given amount
     * @param amount the amount to increase the hunger by
     */
    void increaseEnergy(float amount) {
        energy = Math.min(1, energy + amount);
    }

    /**
     * Decrease the hunger of the player by a given amount
     * @param amount the amount to decrease the hunger by
     */
    void decreaseEnergy(float amount) {
        energy = Math.max(PROGRESS_BAR_MINIMUM, energy - amount);
    }

    @Override
    public ProgressBar getProgressBar() {
        progressBar.setValue(getEnergy());
        return progressBar;
    }

    @Override
    public String getLabel() {
        return "Energy";
    }
}
