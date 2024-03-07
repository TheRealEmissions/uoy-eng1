package uk.ac.york.student;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import uk.ac.york.student.music.MusicManager;

public final class GdxGame extends ApplicationAdapter {
	
	@Override
	public void create() {
		MusicManager.onEnable();
	}


	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
	}
	
	@Override
	public void dispose() {
		MusicManager.onDisable();
	}
}
