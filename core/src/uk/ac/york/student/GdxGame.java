package uk.ac.york.student;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import uk.ac.york.student.audio.AudioManager;
import uk.ac.york.student.audio.music.MusicManager;
import uk.ac.york.student.audio.sound.SoundManager;

public final class GdxGame extends ApplicationAdapter {
	
	@Override
	public void create() {
		AudioManager musicManager = MusicManager.getInstance();
		musicManager.onEnable();

		AudioManager soundManager = SoundManager.getInstance();
		soundManager.onEnable();
	}


	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
	}
	
	@Override
	public void dispose() {
		AudioManager musicManager = MusicManager.getInstance();
		musicManager.onDisable();

		AudioManager soundManager = SoundManager.getInstance();
		soundManager.onDisable();
	}
}
