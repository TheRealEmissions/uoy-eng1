package uk.ac.york.student.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public final class ActivityMapObject extends MapObject implements ActionMapObject {
    private final MapProperties properties;
    @Getter
    private final String str;
    @Getter
    private final String type;
    @Getter
    private final float time;
    public ActivityMapObject(@NotNull MapObject object) {
        super();
        setName(object.getName());
        setColor(object.getColor());
        setOpacity(object.getOpacity());
        setVisible(object.isVisible());
        properties = object.getProperties();
        str = properties.get("activityStr", String.class);
        type = properties.get("activityType", String.class);
        time = properties.get("activityTime", Float.class);
    }

    @Override
    public MapProperties getProperties() {
        return properties;
    }
}
