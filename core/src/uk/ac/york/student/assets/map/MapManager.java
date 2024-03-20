package uk.ac.york.student.assets.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import uk.ac.york.student.utils.MapOfSuppliers;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

@UtilityClass
public final class MapManager {
    @Getter
    private static final MapOfSuppliers<String, TiledMap> maps = new MapOfSuppliers<>();
    static {
        File mapFiles = Gdx.files.internal("map").file();
        if (!mapFiles.isDirectory()) throw new RuntimeException("map directory not found");
        TmxMapLoader.Parameters parameter = new TmxMapLoader.Parameters();
        parameter.textureMinFilter = Texture.TextureFilter.Nearest;
        parameter.textureMagFilter = Texture.TextureFilter.Nearest;
        for (File file : Objects.requireNonNull(mapFiles.listFiles())) {
            if (file.getName().endsWith(".tmx")) {
                maps.put(file.getName().replace(".tmx", ""), () -> new TmxMapLoader().load("map/" + file.getName(), parameter));
            }
        }
    }
}
