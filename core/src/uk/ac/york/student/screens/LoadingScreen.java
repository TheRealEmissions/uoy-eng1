package uk.ac.york.student.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import uk.ac.york.student.GdxGame;

public class LoadingScreen extends BaseScreen {
    @Getter
    private final Stage processor;
    public LoadingScreen(GdxGame game) {
        super(game);
        processor = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        game.setScreen(Screens.MAIN_MENU, true);
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