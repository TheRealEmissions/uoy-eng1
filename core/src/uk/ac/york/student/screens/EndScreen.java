package uk.ac.york.student.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.player.Player;

@Getter
public class EndScreen extends BaseScreen {
    private final Stage processor;
    private final Player player;
    public EndScreen(GdxGame game) {
        super(game);
        throw new UnsupportedOperationException("This constructor is not supported (must pass in object args!)");
    }

    public EndScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime, Object @NotNull [] args) {
        super(game);
        processor = new Stage(new ScreenViewport());
        player = (Player) args[0];
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
