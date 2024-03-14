package uk.ac.york.student;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.audio.AudioManager;
import uk.ac.york.student.audio.music.MusicManager;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.screens.GenericScreen;
import uk.ac.york.student.screens.Screens;

import java.lang.reflect.InvocationTargetException;

public final class GdxGame extends Game {

	public GdxGame() {
		super();
	}
	
	@Override
	public void create() {
		final AudioManager musicManager = MusicManager.getInstance();
		musicManager.onEnable();

		final AudioManager soundManager = SoundManager.getInstance();
		soundManager.onEnable();

		setScreen(Screens.LOADING);
	}

	/**
	 * Set the current screen
	 * @param screen - the screen to set (use Screens class)
	 */
	public void setScreen(@NotNull Class<? extends GenericScreen> screen) {
        GenericScreen newScreen;
		try {
            newScreen = screen.getConstructor(Game.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Gdx.app.error("LetRonCooke", "Error loading screen", e);
			Gdx.app.exit();
			return;
        }
        super.setScreen(newScreen);
	}


	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		final AudioManager musicManager = MusicManager.getInstance();
		musicManager.onDisable();

		final AudioManager soundManager = SoundManager.getInstance();
		soundManager.onDisable();
	}
}
