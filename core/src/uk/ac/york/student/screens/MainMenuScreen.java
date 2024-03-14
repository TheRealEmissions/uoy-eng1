package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("images/MapOverview.png"));
    private final Texture vignetteTexture = new Texture(Gdx.files.internal("images/Vignette.png"));
    private final Texture logo = new Texture(Gdx.files.internal("images/Logo.png"));
    private final Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);
    private final GameSound buttonClick = SoundManager.getSupplierSounds().getResult(Sounds.BUTTON_CLICK);
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

        TextButton playButton = new TextButton("Let Ron Cooke", craftacularSkin);
        TextButton preferencesButton = new TextButton("Preferences", craftacularSkin);
        TextButton exitButton = new TextButton("Exit", craftacularSkin);

        Image logoImage = new Image(logo);
        table.add(logoImage).fillX().uniformX();
        table.row();

        table.add(playButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferencesButton).fillX().uniformX();
        table.row();
        table.add(exitButton).fillX().uniformX();

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                game.setScreen(Screens.GAME);
            }
        });

        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                game.setScreen(Screens.PREFERENCES);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw MapOverview.png as background
        Batch batch = processor.getBatch();
        batch.begin();

        float width;
        float height;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float widthRatio = screenWidth / backgroundTexture.getWidth();
        float heightRatio = screenHeight / backgroundTexture.getHeight();

        float ratio = Math.max(widthRatio, heightRatio);

        width = backgroundTexture.getWidth() * ratio;
        height = backgroundTexture.getHeight() * ratio;

        batch.draw(backgroundTexture, 0, 0, width, height);

        batch.draw(vignetteTexture, 0, 0, screenWidth, screenHeight);

        batch.end();

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
        backgroundTexture.dispose();
        vignetteTexture.dispose();
        logo.dispose();
        craftacularSkin.dispose();
        buttonClick.dispose();
    }
}
