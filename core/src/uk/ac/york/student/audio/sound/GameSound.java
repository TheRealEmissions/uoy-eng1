package uk.ac.york.student.audio.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * The base class for all game sounds
 */
public abstract class GameSound implements Sound {
    /**
     * The sound object
     */
    protected final Sound sound;

    /**
     * Creates a new GameSound with the given path
     * @param path The path to the sound file (in assets folder)
     */
    protected GameSound(String path) {
        sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    /**
     * Plays the sound
     * @return The sound id
     */
    @Override
    public long play() {
        return sound.play();
    }

    /**
     * Plays the sound with the given volume
     * @param volume The volume to play the sound at
     * @return The sound id
     */
    @Override
    public long play(float volume) {
        return sound.play(volume);
    }

    /**
     * Plays the sound with the given volume, pitch and pan
     * @param volume The volume to play the sound at
     * @param pitch The pitch to play the sound at
     * @param pan The pan to play the sound at
     * @return The sound id
     */
    @Override
    public long play(float volume, float pitch, float pan) {
        return sound.play(volume, pitch, pan);
    }

    /**
     * Loops the sound
     * @return The sound id
     */
    @Override
    public long loop() {
        return sound.loop();
    }

    /**
     * Loops the sound with the given volume
     * @param volume The volume to play the sound at
     * @return The sound id
     */
    @Override
    public long loop(float volume) {
        return sound.loop(volume);
    }

    /**
     * Loops the sound with the given volume, pitch and pan
     * @param volume The volume to play the sound at
     * @param pitch The pitch to play the sound at
     * @param pan The pan to play the sound at
     * @return The sound id
     */
    @Override
    public long loop(float volume, float pitch, float pan) {
        return sound.loop(volume, pitch, pan);
    }

    /**
     * Stops the sound
     */
    @Override
    public void stop() {
        sound.stop();
    }

    /**
     * Pauses the sound
     */
    @Override
    public void pause() {
        sound.pause();
    }

    /**
     * Resumes the sound
     */
    @Override
    public void resume() {
        sound.resume();
    }

    /**
     * Disposes of the sound
     */
    @Override
    public void dispose() {
        sound.dispose();
    }

    /**
     * Sets whether the sound is looping
     * @param soundId The sound id
     * @param looping Whether the sound should loop
     */
    @Override
    public void setLooping(long soundId, boolean looping) {
        sound.setLooping(soundId, looping);
    }

    /**
     * Sets the pitch of the sound
     * @param soundId The sound id
     * @param pitch The pitch to set the sound to
     */
    @Override
    public void setPitch(long soundId, float pitch) {
        sound.setPitch(soundId, pitch);
    }

    /**
     * Sets the volume of the sound
     * @param soundId The sound id
     * @param volume The volume to set the sound to
     */
    @Override
    public void setVolume(long soundId, float volume) {
        sound.setVolume(soundId, volume);
    }

    /**
     * Sets the pan of the sound
     * @param soundId The sound id
     * @param pan The pan to set the sound to
     * @param volume The volume to set the sound to
     */
    @Override
    public void setPan(long soundId, float pan, float volume) {
        sound.setPan(soundId, pan, volume);
    }
}
