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
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.audio.music.MusicManager;
import uk.ac.york.student.audio.sound.GameSound;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.audio.sound.Sounds;
import uk.ac.york.student.settings.GamePreferences;

import java.util.function.Supplier;

public class PreferencesScreen extends GenericScreen {
    private final Stage processor;
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
        DEBUG_SCREEN_ENABLED("Debug Screen Toggle: {0}");

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
    public PreferencesScreen(GdxGame game) {
        super(game);
        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
    }

    @Override
    public void show() {
        final TextButton musicToggleButton = new TextButton(Labels.MUSIC_ENABLED.getLabel(((GamePreferences.MusicPreferences) GamePreferences.MUSIC.getPreference()).isEnabled() ? "ON" : "OFF"), craftacularSkin);
        musicToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                GamePreferences.MusicPreferences preference = (GamePreferences.MusicPreferences) GamePreferences.MUSIC.getPreference();
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

        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, craftacularSkin);
        musicVolumeSlider.setVisualPercent(((GamePreferences.MusicPreferences) GamePreferences.MUSIC.getPreference()).getVolume());

        Label musicVolumeLabel = new Label(Labels.MUSIC_VOLUME.getLabel(Math.round(musicVolumeSlider.getValue() * 100) + "%"), craftacularSkin);

        musicVolumeSlider.addListener(event -> {
            GamePreferences.MusicPreferences preference = (GamePreferences.MusicPreferences) GamePreferences.MUSIC.getPreference();
            preference.setVolume(musicVolumeSlider.getValue());
            MusicManager.BACKGROUND_MUSIC.setVolume(musicVolumeSlider.getValue());
            musicVolumeLabel.setText(Labels.MUSIC_VOLUME.getLabel(Math.round(musicVolumeSlider.getValue() * 100) + "%"));
            return false;
        });

        final TextButton soundToggleButton = new TextButton(Labels.SOUND_ENABLED.getLabel(((GamePreferences.SoundPreferences) GamePreferences.SOUND.getPreference()).isEnabled() ? "ON" : "OFF"), craftacularSkin);
        soundToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GamePreferences.SoundPreferences preference = (GamePreferences.SoundPreferences) GamePreferences.SOUND.getPreference();
                boolean nowEnabled = !preference.isEnabled();
                preference.setEnabled(nowEnabled);
                soundToggleButton.setText(Labels.SOUND_ENABLED.getLabel(nowEnabled ? "ON" : "OFF"));
                buttonClick.play();
            }
        });

        final TextButton debugScreenToggleButton = new TextButton(Labels.DEBUG_SCREEN_ENABLED.getLabel(((GamePreferences.DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled() ? "ON" : "OFF"), craftacularSkin);
        debugScreenToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                GamePreferences.DebugScreenPreferences preference = (GamePreferences.DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference();
                boolean nowEnabled = !preference.isEnabled();
                preference.setEnabled(nowEnabled);
                game.setScreen(Screens.PREFERENCES);
            }
        });

        final TextButton backButton = new TextButton(Labels.BACK_BUTTON.getLabel(), craftacularSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                game.setScreen(Screens.MAIN_MENU);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        if (((GamePreferences.DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled()) {
            table.setDebug(true);
        }
        table.setSkin(craftacularSkin);
        processor.addActor(table);
        Cell<Label> titleCell = table.add(Labels.TITLE.getLabel()).colspan(2).pad(0, 0, 100, 0);
        titleCell.getActor().setFontScale(1.5f);
        table.row();
        table.add(musicToggleButton).fillX().uniformX();
        table.row();
        Stack stack = new Stack();
        stack.add(musicVolumeSlider);
        // centre the label
        musicVolumeLabel.setAlignment(1);
        // make it so the label is not interactable
        musicVolumeLabel.setTouchable(Touchable.disabled);
        stack.add(musicVolumeLabel);
        table.add(stack).center().fillX().uniformX().pad(0, 0, 25 ,0);
        table.row();
        table.add(soundToggleButton).fillX().uniformX().pad(0, 0, 25, 0);
        table.row();
        table.add(debugScreenToggleButton).fillX().uniformX().pad(0, 0, 25, 0);
        table.row();

        // render back button across both columns
        // add large gap
        table.row().pad(150, 0, 0, 0);
        table.add(backButton).colspan(2).fillX().uniformX();
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
        batch.draw(bottomUpBlackGradient, 0, 0, Gdx.graphics.getWidth(), bottomUpBlackGradient.getHeight());

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
