package uk.ac.york.student.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.fonts.FontManager;

public class ExitScreen extends GenericScreen {
    public ExitScreen(GdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        BitmapFont font = FontManager.getInstance().getFont();
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
