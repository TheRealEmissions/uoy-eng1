package uk.ac.york.student;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import uk.ac.york.student.GdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60); // Vsynced to 60fps (no need to go above/below this)
		config.setWindowedMode(1920, 1080);
		config.setHdpiMode(HdpiMode.Logical); // Convert coordinates to be logical (scaled to 1920x1080)
		config.setTitle("ENG1");
		config.setIdleFPS(15); // Ensure game doesn't take up unnecessary resources when idle
		config.setResizable(false); // Disabled until resizing is properly implemented
		config.setInitialBackgroundColor(Color.WHITE);
		new Lwjgl3Application(new GdxGame(), config);
	}
}
