package uk.ac.york.student.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.screens.BaseScreen;
import uk.ac.york.student.screens.Screens;

public class TransitionMapObject extends MapObject implements ActionMapObject {
    private final MapProperties properties;
    @Getter
    private final String str;
    @Getter
    private final String type;
    public TransitionMapObject(@NotNull MapObject object) {
        super();
        setName(object.getName());
        setColor(object.getColor());
        setOpacity(object.getOpacity());
        setVisible(object.isVisible());
        properties = object.getProperties();
        type = properties.get("newMap", String.class);
        str = properties.get("newMapStr", String.class);
    }

    @Override
    public MapProperties getProperties() {
        return properties;
    }
}
