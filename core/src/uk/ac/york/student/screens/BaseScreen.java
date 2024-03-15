package uk.ac.york.student.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import uk.ac.york.student.GdxGame;

public abstract class BaseScreen implements Screen {
    protected final GdxGame game;

    protected BaseScreen(GdxGame game) {
        this.game = game;
    }

    public abstract Stage getProcessor();
}
