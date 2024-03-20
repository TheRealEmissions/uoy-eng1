package uk.ac.york.student.assets.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * This class extends MapObject and implements ActionMapObject.
 * It represents an activity on the map with specific properties.
 */
public final class ActivityMapObject extends MapObject implements ActionMapObject {
    /**
     * MapProperties object to store the properties of the activity
     */
    private final MapProperties properties;

    /**
     * String representing the activity to display on the screen
     */
    @Getter
    private final String str;

    /**
     * String representing the type of the activity
     */
    @Getter
    private final String type;

    /**
     * Integer representing how long the activity takes (in hours)
     */
    @Getter
    private final int time;

    /**
     * Constructor for the ActivityMapObject class.
     * It initialises the object with the properties of the given MapObject.
     * @param object The MapObject to initialize the ActivityMapObject with.
     */
    public ActivityMapObject(@NotNull MapObject object) {
        super();
        setName(object.getName());
        setColor(object.getColor());
        setOpacity(object.getOpacity());
        setVisible(object.isVisible());
        properties = object.getProperties();
        str = properties.get("activityStr", String.class);
        type = properties.get("activityType", String.class);
        time = properties.get("activityTime", Integer.class);
    }

    /**
     * Getter for the properties of the ActivityMapObject.
     * @return The properties of the ActivityMapObject.
     */
    @Override
    public MapProperties getProperties() {
        return properties;
    }
}