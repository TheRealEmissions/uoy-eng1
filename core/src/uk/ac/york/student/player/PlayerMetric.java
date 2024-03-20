package uk.ac.york.student.player;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;

public interface PlayerMetric {
    float PROGRESS_BAR_MINIMUM = 0.1f;
    Skin skin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);
    ProgressBar getProgressBar();
    String getLabel();

    default void dispose() {
        skin.dispose();
    }
}
