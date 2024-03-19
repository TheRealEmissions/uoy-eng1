package uk.ac.york.student.audio.sound;

import lombok.Getter;
import uk.ac.york.student.audio.AudioManager;
import uk.ac.york.student.audio.sound.elements.ButtonClickSound;
import uk.ac.york.student.utils.EnumMapOfSuppliers;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * Singleton class that manages the sound for the game
 */
public class SoundManager implements AudioManager {
    /**
     * The instance of the sound manager
     */
    @Getter
    private static final SoundManager instance = new SoundManager();

    private static final Supplier<GameSound> buttonClickSound = ButtonClickSound::new;
    @Getter
    private static final EnumMap<Sounds, GameSound> sounds = new EnumMap<>(Sounds.class);
    @Getter
    private static final EnumMapOfSuppliers<Sounds, GameSound> supplierSounds = new EnumMapOfSuppliers<>(Sounds.class);
    static {
        sounds.put(Sounds.BUTTON_CLICK, buttonClickSound.get());
        supplierSounds.put(Sounds.BUTTON_CLICK, buttonClickSound);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private SoundManager() {

    }
    /**
     * Called when the game is started
     */
    @Override
    public void onEnable() {

    }

    /**
     * Called when the game is stopped
     */
    @Override
    public void onDisable() {
        for (GameSound sound : sounds.values()) {
            sound.dispose();
        }
    }
}
