package uk.ac.york.student.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import sun.font.TrueTypeFont;

@Getter
public class FontManager {
    /**
     * Singleton instance of the font manager
     */
    public static final FontManager instance = new FontManager();

    private final BitmapFont font;

    /**
     * Private constructor to prevent instantiation
     */
    private FontManager() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PixelifySans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
        generator.dispose();
    }
}
