package uk.ac.york.student.music;

import lombok.experimental.UtilityClass;

/**
 * Manages the music for the game
 */
@UtilityClass
public class MusicManager {
    /**
     * The background music for the game
     */
    public static final BackgroundMusic BACKGROUND_MUSIC = new BackgroundMusic();

    /**
     * Called when the game is started
     */
    public static void onEnable() {
        BACKGROUND_MUSIC.setLooping(true);
        BACKGROUND_MUSIC.play();
    }

    public static void onDisable() {
        BACKGROUND_MUSIC.stop();
        BACKGROUND_MUSIC.dispose();
    }
}
