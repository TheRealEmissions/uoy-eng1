package uk.ac.york.student.assets.skins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import uk.ac.york.student.utils.SupplierMap;

import java.util.HashMap;
import java.util.function.Supplier;

@UtilityClass
public class SkinManager {
    private static final Supplier<Skin> craftacular = () -> new Skin(Gdx.files.internal("skins/craftacular/craftacular-ui.json"));

    @Getter
    private static final SupplierMap<Skins, Skin> buttons = new SupplierMap<>();

    static {
        buttons.put(Skins.CRAFTACULAR, craftacular);
    }
}
