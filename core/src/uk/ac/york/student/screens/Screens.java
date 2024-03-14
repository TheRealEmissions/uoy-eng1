package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@UtilityClass
/**
 * A utility class to store the classes of the different screens loaded with reflection.
 */
public final class Screens {
    public static Class<MainMenuScreen> MAIN_MENU;
    public static Class<LoadingScreen> LOADING;
    public static Class<GameScreen> GAME;
    public static Class<PreferencesScreen> PREFERENCES;
    public static Class<EndScreen> END;

    static {
        Field[] fields = Screens.class.getFields();
        // using reflection, set the fields to their respective classes
        for (Field field : fields) {
            String path = field.getGenericType().getTypeName().replace("java.lang.Class<", "").replace(">", "");
            try {
                // Many linters will complain about this, but it's fine in this case
                // as it does not have an effect on the program's security,
                // performance, and it does not violate encapsulation.
                //
                // Although the fields should be final, they are not because
                // this utility class is set up with reflection and designed to emulate an enum
                field.set(null, Class.forName(path));
            } catch (ClassNotFoundException | IllegalAccessException e) {
                Gdx.app.error("Screens", "Could not find class " + path);
            }
        }
    }
}
