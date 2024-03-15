package uk.ac.york.student;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.audio.AudioManager;
import uk.ac.york.student.audio.music.MusicManager;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.screens.BaseScreen;
import uk.ac.york.student.screens.Screens;

import java.lang.reflect.InvocationTargetException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;

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
	public void setScreen(@NotNull Class<? extends BaseScreen> screen) {
		// dispose current screen
		final Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen.dispose();
		}

        BaseScreen newScreen;
		try {
            newScreen = screen.getConstructor(GdxGame.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Gdx.app.error("LetRonCooke", "Error loading screen", e);
			Gdx.app.exit();
			return;
        }
        super.setScreen(newScreen);
	}

	public void setScreen(@NotNull Class<? extends BaseScreen> screen, boolean shouldFadeIn) {
		// dispose current screen
		final Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen.dispose();
		}

		BaseScreen newScreen;
		try {
			newScreen = screen.getConstructor(GdxGame.class, boolean.class).newInstance(this, shouldFadeIn);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			Gdx.app.error("LetRonCooke", "Error loading screen", e);
			Gdx.app.exit();
			return;
		}
		super.setScreen(newScreen);
	}

	public void setScreen(@NotNull Class<? extends BaseScreen> screen, boolean shouldFadeIn, float fadeInTime) {
		// dispose current screen
		final Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen.dispose();
		}

		BaseScreen newScreen;
		try {
			newScreen = screen.getConstructor(GdxGame.class, boolean.class, float.class).newInstance(this, shouldFadeIn, fadeInTime);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			Gdx.app.error("LetRonCooke", "Error loading screen", e);
			Gdx.app.exit();
			return;
		}
		super.setScreen(newScreen);
	}

	public void transitionScreen(@NotNull Class<? extends BaseScreen> screen) {
		// dispose current screen
		final Screen currentScreen = getScreen();
		if (currentScreen == null) {
			setScreen(screen);
			return;
		}

		if (!(currentScreen instanceof BaseScreen)) {
			setScreen(screen);
			return;
		}

		final BaseScreen baseScreen = (BaseScreen) currentScreen;

		baseScreen.getProcessor().getRoot().getColor().a = 1;
		SequenceAction sequenceAction = new SequenceAction();
		sequenceAction.addAction(Actions.fadeOut(0.5f));
		sequenceAction.addAction(Actions.run(() -> setScreen(screen, true, 0.5f)));
		baseScreen.getProcessor().getRoot().addAction(sequenceAction);
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
