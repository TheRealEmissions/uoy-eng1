package uk.ac.york.student.assets.skins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import uk.ac.york.student.utils.EnumMapOfSuppliers;

import java.util.function.Supplier;

@UtilityClass
public class SkinManager {
    private static final Supplier<Skin> craftacular = () -> new Skin(Gdx.files.internal("skins/craftacular/skin/craftacular-ui.json"));

    @Getter
    private static final EnumMapOfSuppliers<Skins, Skin> skins = new EnumMapOfSuppliers<>(Skins.class);

    static {
        skins.put(Skins.CRAFTACULAR, craftacular);
    }
}
