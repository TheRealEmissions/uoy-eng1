package uk.ac.york.student.player;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import org.jetbrains.annotations.Range;

/**
 * The PlayerStudyLevel class represents the study level of a player in the game.
 * It implements the PlayerMetric interface, indicating that it is a type of metric used for the player.
 * The study level is represented as a float value between 0 and 1, where 0 is not studied and 1 is very studied.
 * The class also includes a ProgressBar to visually represent the study level.
 */
public class PlayerStudyLevel implements PlayerMetric {
    /**
     * The ProgressBar instance for the PlayerStudyLevel class.
     * This ProgressBar represents the study level of the player in the game.
     * The minimum value is 0 (not studied), the maximum value is 1 (very studied), and the step size is 0.1.
     * The ProgressBar does not have a vertical orientation (false), and uses the Craftacular skin from {@link PlayerMetric#skin}.
     */
    private final ProgressBar progressBar = new ProgressBar(0, 1, 0.1f, false, skin);

    /**
     * Constructor for the PlayerStudyLevel class.
     * This constructor initialises the ProgressBar that represents the player's study level.
     */
    public PlayerStudyLevel() {
        progressBar.setWidth(200); // Set the width of the ProgressBar to 200
        progressBar.setHeight(50); // Set the height of the ProgressBar to 50
        progressBar.setAnimateDuration(0.25f); // Set the animation duration of the ProgressBar to 0.25 seconds
    }

    /**
     * The study level of the player.
     * This is a float value between 0 and 1, where 0 represents not studied and 1 represents very studied.
     * By default, the study level is set to 0.1.
     */
    private @Range(from=0, to=1) float studyLevel = 0.1f;

    /**
     * Get the study level of the player.
     * This is a float value between 0 and 1, where 0 represents not studied and 1 represents very studied.
     * @return the study level of the player
     */
    @Range(from=0, to=1) float getStudyLevel() {
        return studyLevel;
    }

    /**
     * Set the study level of the player.
     * This method takes a float value between 0 and 1 as an argument, where 0 represents not studied and 1 represents very studied.
     * The study level is then set to the maximum of the minimum study level ({@link PlayerMetric#PROGRESS_BAR_MINIMUM}) and the minimum of 1 and the provided study level.
     * This ensures that the study level is always within the valid range.
     * @param studyLevel the new study level of the player
     */
    void setStudyLevel(@Range(from=0, to=1) float studyLevel) {
        this.studyLevel = Math.max(PROGRESS_BAR_MINIMUM, Math.min(1, studyLevel));
    }

    /**
     * Increase the study level of the player.
     * This method takes a float value as an argument, which represents the amount of study to be added to the player's current study level.
     * The new study level is then set to the minimum of 1 and the sum of the current study level and the provided amount.
     * This ensures that the study level does not exceed 1 (very studied).
     * @param amount the amount of study to be added to the player's current study level
     */
    void increaseStudyLevel(float amount) {
        studyLevel = Math.min(1, studyLevel + amount);
    }

    /**
     * Decrease the study level of the player.
     * This method takes a float value as an argument, which represents the amount of study to be subtracted from the player's current study level.
     * The new study level is then set to the maximum of the minimum study level ({@link PlayerMetric#PROGRESS_BAR_MINIMUM}) and the difference between the current study level and the provided amount.
     * This ensures that the study level does not go below the minimum study level.
     * @param amount the amount of study to be subtracted from the player's current study level
     */
    void decreaseStudyLevel(float amount) {
        studyLevel = Math.max(PROGRESS_BAR_MINIMUM, studyLevel - amount);
    }

    /**
     * Get the ProgressBar representing the player's study level.
     * This method first sets the value of the ProgressBar to the current study level of the player.
     * It then returns the ProgressBar.
     * @return the ProgressBar representing the player's study level
     */
    @Override
    public ProgressBar getProgressBar() {
        progressBar.setValue(getStudyLevel());
        return progressBar;
    }

    /**
     * Get the label used to display on the screen for the player's study level.
     * @return the label for the player's study level
     */
    @Override
    public String getLabel() {
        return "Study Level";
    }
}