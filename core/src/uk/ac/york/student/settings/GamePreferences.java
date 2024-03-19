package uk.ac.york.student.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum GamePreferences {
    MUSIC(new MusicPreferences()),
    SOUND(new SoundPreferences()),
    DEBUG_SCREEN(new DebugScreenPreferences()),
    MAIN_MENU_CLOUDS(new MainMenuCloudsPreferences());

    private final Preference preference;

    GamePreferences(Preference preference) {
        this.preference = preference;
    }

    public @NotNull String getName() {
        return this.name().toLowerCase();
    }

    private static final String NAME = "settings";

    static Preferences getPreferences() {
        return Gdx.app.getPreferences(NAME);
    }
}
