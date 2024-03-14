package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.audio.sound.GameSound;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.audio.sound.Sounds;
import uk.ac.york.student.settings.GamePreferences;
import uk.ac.york.student.utils.Wait;

import java.util.concurrent.TimeUnit;

public class MainMenuScreen extends GenericScreen {
    private final Stage processor;
    public MainMenuScreen(GdxGame game) {
        super(game);
        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        if (((GamePreferences.DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled()) {
            table.setDebug(true);
        }
        processor.addActor(table);

        Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);

        TextButton playButton = new TextButton("Let Ron Cooke", craftacularSkin);
        TextButton preferencesButton = new TextButton("Preferences", craftacularSkin);
        TextButton exitButton = new TextButton("Exit", craftacularSkin);

        table.add(playButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferencesButton).fillX().uniformX();
        table.row();
        table.add(exitButton).fillX().uniformX();

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameSound buttonClick = SoundManager.getSounds().getResult(Sounds.BUTTON_CLICK);
                buttonClick.play();
                Wait.async(400, TimeUnit.MILLISECONDS)
                    .thenRun(() -> {
                        buttonClick.dispose();
                        Gdx.app.exit();
                    });
            }
        });

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameSound buttonClick = SoundManager.getSounds().getResult(Sounds.BUTTON_CLICK);
                buttonClick.play();
                game.setScreen(Screens.GAME);
            }
        });

        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameSound buttonClick = SoundManager.getSounds().getResult(Sounds.BUTTON_CLICK);
                buttonClick.play();
                game.setScreen(Screens.PREFERENCES);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        processor.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        processor.draw();
    }

    @Override
    public void resize(int width, int height) {
        processor.getViewport().update(width, height, true);
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
        processor.dispose();
    }
}
