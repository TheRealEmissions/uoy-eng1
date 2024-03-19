package uk.ac.york.student.audio.sound.elements;

import uk.ac.york.student.audio.sound.GameSound;
import uk.ac.york.student.settings.GamePreferences;
import uk.ac.york.student.settings.SoundPreferences;

public class ButtonClickSound extends GameSound {
    /**
     * Creates a new GameSound with the default path
     */
    public ButtonClickSound() {
        super("audio/sounds/mixkit-classic-click.mp3");
    }

    @Override
    public long play() {
        SoundPreferences soundPreferences = (SoundPreferences) GamePreferences.SOUND.getPreference();
        if (soundPreferences.isEnabled()) {
            return super.play();
        } else {
            return -1;
        }
    }

    @Override
    public void stop(long soundId) {
        sound.stop(soundId);
    }

    @Override
    public void pause(long soundId) {
        sound.pause(soundId);
    }

    @Override
    public void resume(long soundId) {
        sound.resume(soundId);
    }
}
