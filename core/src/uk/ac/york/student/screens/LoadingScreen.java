package uk.ac.york.student.screens;

import com.badlogic.gdx.Screen;
import uk.ac.york.student.GdxGame;

public class LoadingScreen extends GenericScreen {
    public LoadingScreen(GdxGame game) {
        super(game);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        game.setScreen(Screens.MAIN_MENU);
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
