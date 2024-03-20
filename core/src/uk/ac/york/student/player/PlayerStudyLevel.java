package uk.ac.york.student.player;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import org.jetbrains.annotations.Range;

public class PlayerStudyLevel implements PlayerMetric {
    private final ProgressBar progressBar = new ProgressBar(0, 1, 0.1f, false, skin);

    public PlayerStudyLevel() {
        progressBar.setWidth(200);
        progressBar.setHeight(50);
        progressBar.setAnimateDuration(0.25f);
    }

    private @Range(from=0, to=1) float studyLevel = 0.1f;
    /**
     * Get the study level of the player as a float between 0 and 1
     * Where 0 is not studied and 1 is very studied
     * @return the study level of the player
     */
    @Range(from=0, to=1) float getStudyLevel() {
        return studyLevel;
    }

    /**
     * Set the study level of the player
     * @param studyLevel the study level of the player as a float between 0 and 1
     */
    void setStudyLevel(@Range(from=0, to=1) float studyLevel) {
        this.studyLevel = Math.max(PROGRESS_BAR_MINIMUM, Math.min(1, studyLevel));
    }

    /**
     * Increase the study level of the player by a given amount
     * @param amount the amount to increase the study level by
     */
    void increaseStudyLevel(float amount) {
        studyLevel = Math.min(1, studyLevel + amount);
    }

    /**
     * Decrease the study level of the player by a given amount
     * @param amount the amount to decrease the study level by
     */
    void decreaseStudyLevel(float amount) {
        studyLevel = Math.max(PROGRESS_BAR_MINIMUM, studyLevel - amount);
    }

    @Override
    public ProgressBar getProgressBar() {
        progressBar.setValue(getStudyLevel());
        return progressBar;
    }

    @Override
    public String getLabel() {
        return "Study Level";
    }
}
