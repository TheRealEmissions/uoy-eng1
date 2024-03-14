package uk.ac.york.student.audio.music;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import uk.ac.york.student.audio.AudioManager;
import uk.ac.york.student.audio.music.elements.BackgroundMusic;
import uk.ac.york.student.settings.GamePreferences;

/**
 * Singleton class that manages the music for the game
 */
public class MusicManager implements AudioManager {
    /**
     * The background music for the game
     */
    public static final BackgroundMusic BACKGROUND_MUSIC = new BackgroundMusic();

    /**
     * The instance of the music manager
     */
    @Getter
    private static final MusicManager instance = new MusicManager();

    /**
     * Private constructor to prevent instantiation
     */
    private MusicManager() {

    }
    /**
     * Called when the game is started
     */
    @Override
    public void onEnable() {
        GamePreferences.MusicPreferences musicPreferences = (GamePreferences.MusicPreferences) GamePreferences.MUSIC.getPreference();
        if (musicPreferences.isEnabled()) {
            BACKGROUND_MUSIC.setLooping(true);
            BACKGROUND_MUSIC.play();
            BACKGROUND_MUSIC.setVolume(musicPreferences.getVolume());
        }
    }

    @Override
    public void onDisable() {
        BACKGROUND_MUSIC.stop();
        BACKGROUND_MUSIC.dispose();
    }
}
