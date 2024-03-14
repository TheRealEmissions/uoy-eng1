package uk.ac.york.student.screens;

import com.badlogic.gdx.Screen;
import uk.ac.york.student.GdxGame;

public abstract class GenericScreen implements Screen {
    protected final GdxGame game;

    protected GenericScreen(GdxGame game) {
        this.game = game;
    }
}
