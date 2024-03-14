package uk.ac.york.student.settings;

import com.badlogic.gdx.Gdx;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.audio.music.MusicManager;

@Getter
public enum GamePreferences {
    MUSIC(new MusicPreferences()),
    SOUND(new SoundPreferences()),
    DEBUG_SCREEN(new DebugScreenPreferences());

    private final Preference preference;

    GamePreferences(Preference preference) {
        this.preference = preference;
    }

    public @NotNull String getName() {
        return this.name().toLowerCase();
    }

    private static final String NAME = "settings";

    public interface Preference {
        String getKey(String key);
    }

    public static class DebugScreenPreferences implements Preference {
        private static final String ENABLED = "enabled";
        private static final boolean DEFAULT_ENABLED = false;

        public DebugScreenPreferences() {
            GamePreferences.getPreferences().putBoolean(getKey(ENABLED), DEFAULT_ENABLED);
            GamePreferences.getPreferences().flush();
        }

        public boolean isEnabled() {
            return GamePreferences.getPreferences().getBoolean(getKey(ENABLED));
        }

        public void setEnabled(boolean b) {
            GamePreferences.getPreferences().putBoolean(getKey(ENABLED), b);
            GamePreferences.getPreferences().flush();
        }

        @Override
        public String getKey(String key) {
            return "debug_screen." + key;
        }
    }

    @Getter
    public static class MusicPreferences implements Preference {
        private static final String ENABLED = "enabled";
        private static final boolean DEFAULT_ENABLED = true;
        private static final String VOLUME = "volume";
        private static final float DEFAULT_VOLUME = 0.5f;

        public MusicPreferences() {
            GamePreferences.getPreferences().putBoolean(getKey(ENABLED), DEFAULT_ENABLED);
            GamePreferences.getPreferences().putFloat(getKey(VOLUME), DEFAULT_VOLUME);
            GamePreferences.getPreferences().flush();
        }

        public boolean isEnabled() {
            return GamePreferences.getPreferences().getBoolean(getKey(ENABLED));
        }

        public void setEnabled(boolean b) {
            GamePreferences.getPreferences().putBoolean(getKey(ENABLED), b);
            GamePreferences.getPreferences().flush();
        }

        public float getVolume() {
            return GamePreferences.getPreferences().getFloat(getKey(VOLUME));
        }

        public void setVolume(float vol) {
            GamePreferences.getPreferences().putFloat(getKey(VOLUME), vol);
            GamePreferences.getPreferences().flush();
        }

        @Override
        public String getKey(String key) {
            return "music." + key;
        }
    }

    @Getter
    public static class SoundPreferences implements Preference {
        private static final String ENABLED = "enabled";
        private static final boolean DEFAULT_ENABLED = true;

        public SoundPreferences() {
            GamePreferences.getPreferences().putBoolean(getKey(ENABLED), DEFAULT_ENABLED);
            GamePreferences.getPreferences().flush();
        }

        public boolean isEnabled() {
            return GamePreferences.getPreferences().getBoolean(getKey(ENABLED));
        }

        public void setEnabled(boolean b) {
            GamePreferences.getPreferences().putBoolean(getKey(ENABLED), b);
            GamePreferences.getPreferences().flush();
        }

        @Override
        public String getKey(String key) {
            return "sound." + key;
        }
    }

    private static com.badlogic.gdx.Preferences getPreferences() {
        return Gdx.app.getPreferences(NAME);
    }
}
