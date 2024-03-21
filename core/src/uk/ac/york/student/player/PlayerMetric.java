package uk.ac.york.student.player;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;

/**
 * The PlayerMetric interface provides a contract for player metrics in the game.
 * It includes methods for getting a progress bar and a label, and a method for disposing resources.
 */
public interface PlayerMetric {
    /**
     * The minimum value for the progress bar to prevent visual jankyness when the value is 0.
     */
    float PROGRESS_BAR_MINIMUM = 0.1f;

    /**
     * The skin for the player metric, obtained from the SkinManager.
     */
    Skin skin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);

    /**
     * Get the progress bar for the player metric.
     * @return ProgressBar object representing the player's progress.
     */
    ProgressBar getProgressBar();

    /**
     * Get the label for the player metric to display on the screen
     * @return String representing the label of the player metric.
     */
    String getLabel();

    float get();
    void set(float value);
    void increase(float amount);
    void decrease(float amount);
    float getDefault();

    /**
     * Dispose resources when they are no longer needed.
     * By default, it disposes the skin.
     */
    default void dispose() {
        skin.dispose();
    }
}