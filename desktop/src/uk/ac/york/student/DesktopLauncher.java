package uk.ac.york.student;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import uk.ac.york.student.GdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1920, 1080);
		config.setHdpiMode(HdpiMode.Logical); // Convert coordinates to be logical (scaled to 1920x1080)
		config.setTitle("ENG1");
		config.setIdleFPS(15); // Ensure game doesn't take up unnecessary resources when idle
		config.setResizable(true);
		config.setInitialBackgroundColor(Color.WHITE);
		new Lwjgl3Application(new GdxGame(), config);
	}
}
