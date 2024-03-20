package uk.ac.york.student.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.utils.DrawableUtils;

@Getter
public final class GameTime {
    private static final int DAY_LENGTH = 12;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 5;

    private final ProgressBar progressBar;

    public GameTime(float scale) {
        System.out.println(scale);
        final int scaledWidth = (int) (WIDTH * scale);
        final int scaledHeight = (int) (HEIGHT * scale);

        progressBar = getProgressBar(scaledWidth, scaledHeight);
    }

    @NotNull
    private ProgressBar getProgressBar(int scaledWidth, int scaledHeight) {
        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        final ProgressBar progressBar = new ProgressBar(0, 12, 1, false, style);
        ProgressBar.ProgressBarStyle barStyle = progressBar.getStyle();
        barStyle.background = DrawableUtils.getColouredDrawable(scaledWidth, scaledHeight, Color.GRAY);
        barStyle.knobBefore = DrawableUtils.getColouredDrawable(scaledWidth, scaledHeight, Color.GREEN);
        barStyle.knob = DrawableUtils.getColouredDrawable(0, scaledHeight, Color.GREEN);

        progressBar.setStyle(barStyle);
        progressBar.setWidth(scaledWidth);
        progressBar.setHeight(scaledHeight);

        progressBar.setAnimateDuration(0.25f);
        progressBar.setValue(0);
        return progressBar;
    }

    private @NotNull ProgressBar getProgressBar(float scale) {
        return getProgressBar((int) (WIDTH * scale), (int) (HEIGHT * scale));
    }

    public void updateProgressBar(float scale) {
        getProgressBar(scale);
    }

    private int currentHour = 0;

    public void incrementHour() {
        incrementHour(1);
    }

    public void incrementHour(int amount) {
        currentHour = Math.min(DAY_LENGTH - 1, currentHour + amount);
    }

    public void reset() {
        currentHour = 0;
    }

    public boolean isEndOfDay() {
        return currentHour == DAY_LENGTH - 1;
    }
}
