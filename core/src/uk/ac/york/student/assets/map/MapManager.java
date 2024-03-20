package uk.ac.york.student.assets.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import uk.ac.york.student.utils.MapOfSuppliers;

import java.io.File;
import java.util.Objects;

/**
 * This utility class manages the loading and storage of TiledMap objects.
 * It uses a MapOfSuppliers to store the maps, allowing for lazy loading.
 */
@UtilityClass
public final class MapManager {
    /**
     * A MapOfSuppliers that maps from map names (as a string) to TiledMap objects
     * <p>
     * Use {@link uk.ac.york.student.utils.MapOfSuppliers#getResult(Object)} to get the TiledMap object for a given map name
     */
    @Getter
    private static final MapOfSuppliers<String, TiledMap> maps = new MapOfSuppliers<>();

    // Static initializer block that loads the maps from the "map" directory in the internal "assets" directory
    static {
        // Get the directory containing the map files
        File mapFiles = Gdx.files.internal("map").file();
        // Throw an exception if the directory does not exist
        if (!mapFiles.isDirectory()) throw new RuntimeException("map directory not found");

        // Create parameters for loading the maps
        TmxMapLoader.Parameters parameter = new TmxMapLoader.Parameters();
        parameter.textureMinFilter = Texture.TextureFilter.Nearest;
        parameter.textureMagFilter = Texture.TextureFilter.Nearest;

        // Load each map file in the directory (and hide the potential NullPointerException with Objects.requireNonNull)
        for (File file : Objects.requireNonNull(mapFiles.listFiles())) {
            // Only load files with the ".tmx" extension
            if (file.getName().endsWith(".tmx")) {
                // Add the map to the MapOfSuppliers, using a lambda to allow for lazy loading
                maps.put(file.getName().replace(".tmx", ""), () -> new TmxMapLoader().load("map/" + file.getName(), parameter));
            }
        }
    }
}