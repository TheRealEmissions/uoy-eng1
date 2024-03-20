package uk.ac.york.student.assets.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import uk.ac.york.student.game.activities.Activity;
import uk.ac.york.student.player.Player;
import uk.ac.york.student.player.PlayerMetrics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private final Activity type;

    /**
     * Integer representing how long the activity takes (in hours)
     */
    @Getter
    private final int time;

    /**
     * An {@link Unmodifiable} {@link List} of {@link Float}s representing the amounts by which the player's metrics change when performing the activity.
     * Each {@link Float} corresponds to a specific metric, in the order they are defined in the {@link PlayerMetrics.MetricType} enum.
     * The amounts are retrieved from the "changeAmount" property of the {@link MapObject}, which is a comma-separated string of floats.
     */
    @Getter
    private final @Unmodifiable List<Float> changeAmounts;

    /**
     * Returns the change amount for the specified {@link PlayerMetrics.MetricType}.
     * This method retrieves the index of the specified {@link PlayerMetrics.MetricType} in the {@link Activity#getEffects()} of the {@link Activity}.
     * It then returns the change amount at that index in {@link Activity#getEffects()}.
     * The change amount represents the amount by which the {@link Player}'s {@link PlayerMetrics} changes when performing the {@link Activity}.
     *
     * @param metricType the {@link PlayerMetrics.MetricType} to get the change amount for
     * @return the change amount for the specified {@link PlayerMetrics.MetricType}
     */
    public float getChangeAmount(PlayerMetrics.MetricType metricType) {
        return changeAmounts.get(type.indexOf(metricType));
    }

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
        type = Activity.valueOf(properties.get("activityType", String.class).toUpperCase());
        time = properties.get("activityTime", Integer.class);
        changeAmounts = Arrays.stream(properties.get("changeAmount", String.class).split(","))
                            .map(Float::parseFloat)
                            .collect(Collectors.toUnmodifiableList());
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