package uk.ac.york.student.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.utils.DrawableUtils;

@Getter
public final class GameTime {
    private static final int DAYS = 7;
    private static final int DAY_LENGTH = 16;
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

    private int currentHour = 3;

    public void incrementHour(int amount) {
        currentHour = Math.min(DAY_LENGTH, currentHour + amount);
    }

    public void resetHour() {
        currentHour = 0;
    }

    public boolean isEndOfDay() {
        return currentHour == DAY_LENGTH;
    }

    private int currentDay = 0;

    public void incrementDay() {
        incrementDay(1);
        resetHour();
    }

    public void incrementDay(int amount) {
        currentDay = Math.min(DAYS - 1, currentDay + amount);
        resetHour();
    }

    public void resetDay() {
        currentDay = 0;
        resetHour();
    }

    public boolean isEndOfDays() {
        return currentDay == DAYS - 1;
    }
}
