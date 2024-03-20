package uk.ac.york.student.player;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import org.jetbrains.annotations.Range;

/**
 * The PlayerHappiness class implements the PlayerMetric interface.
 * This class is responsible for managing the happiness level of a player in the game.
 * It includes methods to get, set, increase, and decrease the happiness level.
 * It also includes methods to get a ProgressBar representing the happiness level and a label for the happiness level.
 */
public class PlayerHappiness implements PlayerMetric {
    /**
     * The ProgressBar instance for the PlayerHappiness class.
     * This ProgressBar represents the happiness level of the player in the game.
     * The minimum value is 0 (not happy), the maximum value is 1 (very happy), and the step size is 0.1.
     * The ProgressBar does not have a vertical orientation (false), and uses the skin from {@link PlayerMetric#skin}.
     */
    private final ProgressBar progressBar = new ProgressBar(0, 1, 0.1f, false, skin);
    /**
     * Constructor for the PlayerHappiness class.
     * This constructor initializes the ProgressBar representing the player's happiness level.
     */
    public PlayerHappiness() {
        progressBar.setWidth(200);
        progressBar.setHeight(50);
        progressBar.setAnimateDuration(0.25f);
    }

    /**
     * The happiness level of the player.
     * This is a float value ranging from 0 to 1, where 0 represents not happy and 1 represents very happy.
     * It is initially set to 1, indicating that the player starts the game being very happy.
     */
    private @Range(from=0, to=1) float happiness = 1f;
    /**
     * Getter method for the happiness level of the player.
     * This method returns the current happiness level of the player.
     * The happiness level is a float value ranging from 0 to 1, where 0 represents not happy and 1 represents very happy.
     * @return the current happiness level of the player.
     */
    @Range(from=0, to=1) float getHappiness() {
        return happiness;
    }


    /**
     * Set the happiness level of the player.
     * This method takes a float value between 0 and 1 as an argument, where 0 represents not happy and 1 represents very happy.
     * The happiness level is then set to the maximum of the minimum happiness level ({@link PlayerMetric#PROGRESS_BAR_MINIMUM}) and the minimum of 1 and the provided happiness level.
     * This ensures that the happiness level is always within the valid range.
     * @param happiness the new happiness level of the player
     */
    void setHappiness(@Range(from=0, to=1) float happiness) {
        this.happiness = Math.max(PROGRESS_BAR_MINIMUM, Math.min(1, happiness));
    }


    /**
     * Increase the happiness level of the player.
     * This method takes a float value as an argument, which represents the amount to increase the happiness level by.
     * The happiness level after the increase is the minimum of 1 and the sum of the current happiness level and the provided amount.
     * This ensures that the happiness level does not exceed 1.
     * @param amount the amount to increase the happiness level by
     */
    void increaseHappiness(float amount) {
        happiness = Math.min(1, happiness + amount);
    }


    /**
     * Decrease the happiness level of the player.
     * This method takes a float value as an argument, which represents the amount to decrease the happiness level by.
     * The happiness level after the decrease is the maximum of the minimum happiness level ({@link PlayerMetric#PROGRESS_BAR_MINIMUM}) and the difference between the current happiness level and the provided amount.
     * This ensures that the happiness level does not go below the minimum happiness level.
     * @param amount the amount to decrease the happiness level by
     */
    void decreaseHappiness(float amount) {
        happiness = Math.max(PROGRESS_BAR_MINIMUM, happiness - amount);
    }

    /**
     * Get the ProgressBar representing the happiness level of the player.
     * This method sets the value of the ProgressBar to the current happiness level of the player and then returns the ProgressBar.
     * The ProgressBar represents the happiness level of the player in the game, with a minimum value of 0 (not happy) and a maximum value of 1 (very happy).
     * @return the ProgressBar representing the happiness level of the player.
     */
    @Override
    public ProgressBar getProgressBar() {
        progressBar.setValue(getHappiness());
        return progressBar;
    }

    /**
     * Get the label used to display on the screen for the player's happiness level.
     * @return the label for the player's happiness level
     */
    @Override
    public String getLabel() {
        return "Happiness";
    }
}