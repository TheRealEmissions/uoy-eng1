package uk.ac.york.student.player;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import org.jetbrains.annotations.Range;

public class PlayerHappiness implements PlayerMetric {
    private final ProgressBar progressBar = new ProgressBar(0, 1, 0.1f, false, skin);

    public PlayerHappiness() {
        progressBar.setWidth(200);
        progressBar.setHeight(50);
        progressBar.setAnimateDuration(0.25f);
    }

    private @Range(from=0, to=1) float happiness = 1f;
    /**
     * Get the happiness of the player as a float between 0 and 1
     * Where 0 is not happy and 1 is very happy
     * @return the happiness of the player
     */
    @Range(from=0, to=1) float getHappiness() {
        return happiness;
    }

    /**
     * Set the happiness of the player
     * @param happiness the happiness of the player as a float between 0 and 1
     */
    void setHappiness(@Range(from=0, to=1) float happiness) {
        this.happiness = Math.max(PROGRESS_BAR_MINIMUM, Math.min(1, happiness));
    }

    /**
     * Increase the happiness of the player by a given amount
     * @param amount the amount to increase the happiness by
     */
    void increaseHappiness(float amount) {
        happiness = Math.min(1, happiness + amount);
    }

    /**
     * Decrease the happiness of the player by a given amount
     * @param amount the amount to decrease the happiness by
     */
    void decreaseHappiness(float amount) {
        happiness = Math.max(PROGRESS_BAR_MINIMUM, happiness - amount);
    }

    @Override
    public ProgressBar getProgressBar() {
        progressBar.setValue(getHappiness());
        return progressBar;
    }

    @Override
    public String getLabel() {
        return "Happiness";
    }
}
