package uk.ac.york.student.settings;

public class DebugScreenPreferences implements Preference {
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
