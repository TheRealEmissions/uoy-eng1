package uk.ac.york.student.settings;

import lombok.Getter;

@Getter
public class SoundPreferences implements Preference {
    private static final String ENABLED = "enabled";
    private static final boolean DEFAULT_ENABLED = true;
    private static final String VOLUME = "volume";
    private static final float DEFAULT_VOLUME = 1f;


    public SoundPreferences() {
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
        return "sound." + key;
    }
}
