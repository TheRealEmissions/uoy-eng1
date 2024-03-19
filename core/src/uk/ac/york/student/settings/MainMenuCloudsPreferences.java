package uk.ac.york.student.settings;

public class MainMenuCloudsPreferences implements Preference {
    private static final String ENABLED = "enabled";
    private static final boolean DEFAULT_ENABLED = true;
    private static final String SPEED = "speed";
    private static final float DEFAULT_SPEED = 1f;

    public MainMenuCloudsPreferences() {
        GamePreferences.getPreferences().putBoolean(getKey(ENABLED), DEFAULT_ENABLED);
        GamePreferences.getPreferences().putFloat(getKey(SPEED), DEFAULT_SPEED);
        GamePreferences.getPreferences().flush();
    }

    public boolean isEnabled() {
        return GamePreferences.getPreferences().getBoolean(getKey(ENABLED));
    }

    public void setEnabled(boolean b) {
        GamePreferences.getPreferences().putBoolean(getKey(ENABLED), b);
        GamePreferences.getPreferences().flush();
    }

    public float getSpeed() {
        return GamePreferences.getPreferences().getFloat(getKey(SPEED));
    }

    public void setSpeed(float speed) {
        GamePreferences.getPreferences().putFloat(getKey(SPEED), speed);
        GamePreferences.getPreferences().flush();
    }

    @Override
    public String getKey(String key) {
        return "main_menu_clouds." + key;
    }
}
