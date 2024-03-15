package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.audio.sound.GameSound;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.audio.sound.Sounds;
import uk.ac.york.student.settings.GamePreferences;
import uk.ac.york.student.utils.Wait;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class MainMenuScreen extends BaseScreen {
    @Getter
    private final Stage processor;
    private final boolean shouldFadeIn;
    private final float fadeInTime;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("images/MapOverview.png"));
    private final Texture vignetteTexture = new Texture(Gdx.files.internal("images/Vignette.png"));
    private final Texture cookeLogo = new Texture(Gdx.files.internal("images/logo/b/logo.png"));
    private final Texture clouds = new Texture(Gdx.files.internal("images/CloudsFormatted.png"));
    private final Image cloudsImage = new Image(new TextureRegionDrawable(new TextureRegion(clouds)));
    private final Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);
    private final GameSound buttonClick = SoundManager.getSupplierSounds().getResult(Sounds.BUTTON_CLICK);

    private final boolean cloudsEnabled = ((GamePreferences.MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference()).isEnabled();
    private final float cloudsSpeed = ((GamePreferences.MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference()).getSpeed();
    public MainMenuScreen(GdxGame game) {
        this(game, false);
    }

    public MainMenuScreen(GdxGame game, boolean shouldFadeIn) {
        this(game, shouldFadeIn, 0.75f);
    }

    public MainMenuScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime) {
        super(game);
        this.shouldFadeIn = shouldFadeIn;
        this.fadeInTime = fadeInTime;
        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public void zoomAndMove(@NotNull Actor actor, @NotNull Direction direction) {
        Vector2 vector = new Vector2();
        float scale = 2;
        int duration = 1;
        int distance = 800;
        switch (direction) {
            case UP:
                vector.set(0, distance);
                break;
            case DOWN:
                vector.set(0, -distance);
                break;
            case LEFT:
                vector.set(-distance, 0);
                break;
            case RIGHT:
                vector.set(distance, 0);
                break;
        }
        actor.setOrigin(Align.center);
        actor.addAction(Actions.parallel(
            Actions.scaleTo(scale, scale, duration),
            Actions.moveBy(vector.x, vector.y, duration)
        ));
    }

    private final AtomicReference<Float> alpha = new AtomicReference<>(1f);
    private final ScheduledExecutorService executorService;

    public void fadeOut() {
        int duration = 1;
        long totalReductions = (long) (1/0.01);
        long period = (duration * 1000) / totalReductions;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(() -> {
            alpha.updateAndGet(v -> v <= 0 ? 0 : v - 0.01f);
        }, 0, period, TimeUnit.MILLISECONDS);

        executorService.schedule(() -> {
            scheduledFuture.cancel(true);
        }, duration, timeUnit);
    }

    @Override
    public void show() {
        if (shouldFadeIn) {
            processor.getRoot().getColor().a = 0;
            processor.getRoot().addAction(fadeIn(fadeInTime));
        }

        Table table = new Table();
        table.setFillParent(true);
        if (((GamePreferences.DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled()) {
            table.setDebug(true);
        }
        processor.addActor(table);

        TextButton playButton = new TextButton("Let Ron Cooke", craftacularSkin);
        TextButton preferencesButton = new TextButton("Settings", craftacularSkin);
        TextButton exitButton = new TextButton("Exit", craftacularSkin);

        Image cookeLogoImage = new Image(cookeLogo);


/*        table.add(letRonLogoImage).fillX().uniformX().pad(0, 0, 10, 0);
        table.row();*/
        table.add(cookeLogoImage).fillX().uniformX().pad(0, 0, 150, 0);
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

                // move all elements zoomAndMove()
                zoomAndMove(playButton, Direction.DOWN);
                zoomAndMove(preferencesButton, Direction.DOWN);
                zoomAndMove(exitButton, Direction.DOWN);
                zoomAndMove(cookeLogoImage, Direction.UP);
                fadeOut();

                Wait.async(1500, TimeUnit.MILLISECONDS)
                    .thenRun(() -> Gdx.app.postRunnable(() -> game.setScreen(Screens.GAME)));
            }
        });

        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                game.transitionScreen(Screens.PREFERENCES);
            }
        });

        float width;
        float height;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float widthRatio = screenWidth / backgroundTexture.getWidth();
        float heightRatio = screenHeight / backgroundTexture.getHeight();

        float ratio = Math.max(widthRatio, heightRatio);

        width = backgroundTexture.getWidth() * ratio;
        height = backgroundTexture.getHeight() * ratio;

        cloudsImage.setSize(width, height);
    }

    private float cycle = 0;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

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


        if (cloudsEnabled) {
            if (cycle > width) {
                cycle = 0;
            } else cycle += cloudsSpeed;

            // render cloudsImage
            cloudsImage.setPosition(cycle, 0);
            // draw with respect to fade out alpha
            cloudsImage.draw(batch, alpha.get());

            // render 2nd clouds Image
            cloudsImage.setPosition(cycle - width, 0);
            cloudsImage.draw(batch, alpha.get());
        }



        batch.draw(vignetteTexture, 0, 0, screenWidth, screenHeight);

        batch.end();

        processor.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        processor.draw();
    }

    @Override
    public void resize(int width, int height) {
        processor.getViewport().update(width, height, true);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float widthRatio = screenWidth / backgroundTexture.getWidth();
        float heightRatio = screenHeight / backgroundTexture.getHeight();

        float ratio = Math.max(widthRatio, heightRatio);

        float newWidth = backgroundTexture.getWidth() * ratio;
        float newHeight = backgroundTexture.getHeight() * ratio;

        cloudsImage.setSize(newWidth, newHeight);
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
        craftacularSkin.dispose();
        cookeLogo.dispose();
        clouds.dispose();
        buttonClick.dispose();
        executorService.shutdown();
    }
}
