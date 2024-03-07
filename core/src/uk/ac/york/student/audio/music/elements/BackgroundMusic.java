package uk.ac.york.student.audio.music.elements;

import com.badlogic.gdx.audio.Music;
import lombok.Getter;
import uk.ac.york.student.audio.music.GameMusic;

/**
 * The background music for the game
 */
@Getter
public class BackgroundMusic extends GameMusic implements Music {

    /**
     * Creates a new background music with the default path
     */
    public BackgroundMusic() {
        super("music/background.mp3");
    }

    /**
     * Creates a new background music with the given path
     * @param path The path to the music file (in assets folder)
     */
    public BackgroundMusic(String path) {
        super(path);
    }
}
