package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.audio.music.MusicManager;
import uk.ac.york.student.audio.sound.GameSound;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.audio.sound.Sounds;
import uk.ac.york.student.settings.*;

import java.util.function.Supplier;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class PreferencesScreen extends BaseScreen {
    @Getter
    private final Stage processor;
    private final boolean shouldFadeIn;
    private final float fadeInTime;
    private final ScreenData screenData = new ScreenData();
    private final Table table = new Table();
    private final GameSound buttonClick = SoundManager.getSupplierSounds().getResult(Sounds.BUTTON_CLICK);
    private final Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);
    private final Texture stoneWallTexture = new Texture(Gdx.files.internal("images/StoneWall.png"));
    private final Texture bottomUpBlackGradient = new Texture(Gdx.files.internal("images/BottomUpBlackGradient.png"));
    private enum Labels {
        BACK_BUTTON("Head Back"),
        TITLE("Settings"),
        MUSIC_VOLUME("Music Volume {0}"),
        MUSIC_ENABLED("Music Toggle: {0}"),
        SOUND_ENABLED("Sound Toggle: {0}"),
        SOUND_VOLUME("Sound Volume {0}"),
        DEBUG_SCREEN_ENABLED("Debug Screen Toggle: {0}"),
        MAIN_MENU_CLOUDS_ENABLED("Main Menu Clouds Toggle: {0}"),
        MAIN_MENU_CLOUDS_SPEED("Clouds Speed {0}");

        private final Supplier<String> label;

        public String getLabel() {
            return label.get();
        }

        public String getLabel(String @NotNull ... placeholders) {
            String localLabel = this.label.get();
            for (int i = 0; i < placeholders.length; i++) {
                localLabel = localLabel.replace("{" + i + "}", placeholders[i]);
            }
            return localLabel;
        }

        Labels(String label) {
            this.label = () -> label;
        }
    }
    // todo: think of a better name for this class
    private static class ScreenData {
        private TextButton musicToggleButton;
        private Slider musicVolumeSlider;
        private Label musicVolumeLabel;
        private TextButton soundToggleButton;
        private Slider soundVolumeSlider;
        private Label soundVolumeLabel;
        private TextButton debugScreenToggleButton;
        private TextButton cloudsToggleButton;
        private Slider cloudsSpeedSlider;
        private Label cloudsSpeedLabel;
        private TextButton backButton;
    }

    public PreferencesScreen(GdxGame game) {
        this(game, false);
    }

    public PreferencesScreen(GdxGame game, boolean shouldFadeIn) {
        this(game, shouldFadeIn, 0.75f);
    }

    public PreferencesScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime) {
        super(game);
        this.shouldFadeIn = shouldFadeIn;
        this.fadeInTime = fadeInTime;
        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
    }

    @Override
    public void show() {
        if (shouldFadeIn) {
            processor.getRoot().getColor().a = 0;
            processor.getRoot().addAction(fadeIn(fadeInTime));
        }

        createMusicToggleButton();
        listenMusicToggle();
        createMusicVolumeSlider();
        createMusicVolumeLabel();
        listenMusicVolume();

        createSoundToggleButton();
        listenSoundToggle();
        createSoundVolumeSlider();
        createSoundVolumeLabel();
        listenSoundVolume();


        createDebugScreenToggleButton();
        listenDebugScreenToggle();

        createCloudsToggleButton();
        listenCloudsToggle();
        createCloudsSpeedSlider();
        createCloudsSpeedLabel();
        listenCloudsSpeed();

        createBackButton();
        listenBackButton();

        setupTable();
    }

    private void listenCloudsSpeed() {
        Slider cloudsSpeedSlider = screenData.cloudsSpeedSlider;
        Label cloudsSpeedLabel = screenData.cloudsSpeedLabel;
        cloudsSpeedSlider.addListener(event -> {
            MainMenuCloudsPreferences preference = (MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference();
            preference.setSpeed(cloudsSpeedSlider.getValue());
            cloudsSpeedLabel.setText(Labels.MAIN_MENU_CLOUDS_SPEED.getLabel(Math.round(cloudsSpeedSlider.getValue() * 100) + "%"));
            return false;
        });
    }

    private void createCloudsSpeedLabel() {
        screenData.cloudsSpeedLabel = new Label(Labels.MAIN_MENU_CLOUDS_SPEED.getLabel(Math.round(screenData.cloudsSpeedSlider.getValue() * 100) + "%"), craftacularSkin);
    }

    private void createCloudsSpeedSlider() {
        screenData.cloudsSpeedSlider = new Slider(0f, 3f, 0.01f, false, craftacularSkin);
        screenData.cloudsSpeedSlider.setVisualPercent(((MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference()).getSpeed());
        screenData.cloudsSpeedSlider.setValue(((MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference()).getSpeed());
    }

    private void listenCloudsToggle() {
        TextButton cloudsToggleButton = screenData.cloudsToggleButton;
        cloudsToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                MainMenuCloudsPreferences preference = (MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference();
                boolean nowEnabled = !preference.isEnabled();
                preference.setEnabled(nowEnabled);
                cloudsToggleButton.setText(Labels.MAIN_MENU_CLOUDS_ENABLED.getLabel(nowEnabled ? "ON" : "OFF"));
            }
        });
    }

    private void createCloudsToggleButton() {
        screenData.cloudsToggleButton = new TextButton(Labels.MAIN_MENU_CLOUDS_ENABLED.getLabel(((MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference()).isEnabled() ? "ON" : "OFF"), craftacularSkin);
    }

    private void createBackButton() {
        screenData.backButton = new TextButton(Labels.BACK_BUTTON.getLabel(), craftacularSkin);
    }

    private void createDebugScreenToggleButton() {
        screenData.debugScreenToggleButton = new TextButton(Labels.DEBUG_SCREEN_ENABLED.getLabel(((DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled() ? "ON" : "OFF"), craftacularSkin);
    }

    private void createSoundVolumeLabel() {
        screenData.soundVolumeLabel = new Label(Labels.SOUND_VOLUME.getLabel(Math.round(screenData.soundVolumeSlider.getValue() * 100) + "%"), craftacularSkin);
    }

    private void createSoundVolumeSlider() {
        screenData.soundVolumeSlider = new Slider(0f, 1f, 0.01f, false, craftacularSkin);
        screenData.soundVolumeSlider.setVisualPercent(((SoundPreferences) GamePreferences.SOUND.getPreference()).getVolume());
    }

    private void createSoundToggleButton() {
        screenData.soundToggleButton = new TextButton(Labels.SOUND_ENABLED.getLabel(((SoundPreferences) GamePreferences.SOUND.getPreference()).isEnabled() ? "ON" : "OFF"), craftacularSkin);
    }

    private void createMusicVolumeLabel() {
        screenData.musicVolumeLabel = new Label(Labels.MUSIC_VOLUME.getLabel(Math.round(screenData.musicVolumeSlider.getValue() * 100) + "%"), craftacularSkin);
    }

    private void createMusicVolumeSlider() {
        screenData.musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, craftacularSkin);
        screenData.musicVolumeSlider.setVisualPercent(((MusicPreferences) GamePreferences.MUSIC.getPreference()).getVolume());
    }

    private void createMusicToggleButton() {
        screenData.musicToggleButton = new TextButton(Labels.MUSIC_ENABLED.getLabel(((MusicPreferences) GamePreferences.MUSIC.getPreference()).isEnabled() ? "ON" : "OFF"), craftacularSkin);
    }

    private void listenBackButton() {
        TextButton backButton = screenData.backButton;
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                game.transitionScreen(Screens.MAIN_MENU);
            }
        });
    }

    private void setupTable() {
        TextButton musicToggleButton = screenData.musicToggleButton;
        Slider musicVolumeSlider = screenData.musicVolumeSlider;
        Label musicVolumeLabel = screenData.musicVolumeLabel;
        TextButton soundToggleButton = screenData.soundToggleButton;
        Slider soundVolumeSlider = screenData.soundVolumeSlider;
        Label soundVolumeLabel = screenData.soundVolumeLabel;
        TextButton debugScreenToggleButton = screenData.debugScreenToggleButton;
        TextButton backButton = screenData.backButton;
        TextButton cloudsToggleButton = screenData.cloudsToggleButton;
        Slider cloudsSpeedSlider = screenData.cloudsSpeedSlider;
        Label cloudsSpeedLabel = screenData.cloudsSpeedLabel;

        table.setFillParent(true);
        if (((DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled()) {
            table.setDebug(true);
        }
        table.setSkin(craftacularSkin);
        processor.addActor(table);
        Cell<Label> titleCell = table.add(Labels.TITLE.getLabel()).colspan(2).pad(0, 0, 100, 0);
        titleCell.getActor().setFontScale(1.5f);
        table.row();
        table.add(musicToggleButton).fillX().uniformX();
        // .minWidth() sets the width of the button to 1.1 * default width.
        // This ensures that if the text changes it fits in the original wdith.
        // Except for the back button, all other buttons will be the same size.
        table.add(cloudsToggleButton).fillX().minWidth(cloudsToggleButton.getWidth()*1.1f).uniformX().pad(0, 50, 0, 0);
        table.row();

        Stack stack = new Stack();
        stack.add(musicVolumeSlider);
        // centre the label
        musicVolumeLabel.setAlignment(1);
        // make it so the label is not interactable
        musicVolumeLabel.setTouchable(Touchable.disabled);
        stack.add(musicVolumeLabel);
        table.add(stack).center().fillX().uniformX().pad(0, 0, 25 ,0);

        stack = new Stack();
        stack.add(cloudsSpeedSlider);
        // centre the label
        cloudsSpeedLabel.setAlignment(1);
        // make it so the label is not interactable
        cloudsSpeedLabel.setTouchable(Touchable.disabled);
        stack.add(cloudsSpeedLabel);
        table.add(stack).center().fillX().uniformX().pad(0, 50, 25, 0);

        table.row();
        table.add(soundToggleButton).fillX().uniformX();
        table.row();

        stack = new Stack();
        stack.add(soundVolumeSlider);
        // centre the label
        soundVolumeLabel.setAlignment(1);
        // make it so the label is not interactable
        soundVolumeLabel.setTouchable(Touchable.disabled);
        stack.add(soundVolumeLabel);
        table.add(stack).center().fillX().uniformX().pad(0, 0, 25, 0);

        table.row();
        table.add(debugScreenToggleButton).fillX().uniformX().pad(0, 0, 25, 0);
        table.row();

        // render back button across both columns
        // add large gap
        table.row().pad(150, 0, 0, 0);
        table.add(backButton).colspan(2).fillX().uniformX();
    }

    private void listenDebugScreenToggle() {
        TextButton debugScreenToggleButton = screenData.debugScreenToggleButton;
        debugScreenToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                DebugScreenPreferences preference = (DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference();
                boolean nowEnabled = !preference.isEnabled();
                preference.setEnabled(nowEnabled);
                game.setScreen(Screens.PREFERENCES);
            }
        });
    }

    private void listenSoundVolume() {
        Slider soundVolumeSlider = screenData.soundVolumeSlider;
        Label soundVolumeLabel = screenData.soundVolumeLabel;
        soundVolumeSlider.addListener(event -> {
            SoundPreferences preference = (SoundPreferences) GamePreferences.SOUND.getPreference();
            preference.setVolume(soundVolumeSlider.getValue());
            soundVolumeLabel.setText(Labels.SOUND_VOLUME.getLabel(Math.round(soundVolumeSlider.getValue() * 100) + "%"));
            return false;
        });
    }

    private void listenSoundToggle() {
        TextButton soundToggleButton = screenData.soundToggleButton;
        soundToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundPreferences preference = (SoundPreferences) GamePreferences.SOUND.getPreference();
                boolean nowEnabled = !preference.isEnabled();
                preference.setEnabled(nowEnabled);
                soundToggleButton.setText(Labels.SOUND_ENABLED.getLabel(nowEnabled ? "ON" : "OFF"));
                buttonClick.play();
            }
        });
    }

    private void listenMusicVolume() {
        Slider musicVolumeSlider = screenData.musicVolumeSlider;
        Label musicVolumeLabel = screenData.musicVolumeLabel;
        musicVolumeSlider.addListener(event -> {
            MusicPreferences preference = (MusicPreferences) GamePreferences.MUSIC.getPreference();
            preference.setVolume(musicVolumeSlider.getValue());
            MusicManager.BACKGROUND_MUSIC.setVolume(musicVolumeSlider.getValue());
            musicVolumeLabel.setText(Labels.MUSIC_VOLUME.getLabel(Math.round(musicVolumeSlider.getValue() * 100) + "%"));
            return false;
        });
    }

    private void listenMusicToggle() {
        TextButton musicToggleButton = screenData.musicToggleButton;
        musicToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                MusicPreferences preference = (MusicPreferences) GamePreferences.MUSIC.getPreference();
                boolean nowEnabled = !preference.isEnabled();
                preference.setEnabled(nowEnabled);
                musicToggleButton.setText(Labels.MUSIC_ENABLED.getLabel(nowEnabled ? "ON" : "OFF"));
                if (nowEnabled) {
                    MusicManager.getInstance().onEnable();
                } else {
                    MusicManager.getInstance().onDisable();
                }
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Batch batch = processor.getBatch();
        batch.begin();

        // draw in a grid pattern
        int width = stoneWallTexture.getWidth() / 6;
        int height = stoneWallTexture.getHeight() / 6;
        for (int x = 0; x < Gdx.graphics.getWidth(); x += width) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += height) {
                batch.draw(stoneWallTexture, x, y, width, height);
            }
        }

        // draw the gradient at the bottom
        batch.draw(bottomUpBlackGradient, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        craftacularSkin.dispose();
        buttonClick.dispose();
    }
}
