package uk.ac.york.student.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Player extends Actor implements PlayerScore, InputProcessor {
    private float mapScale;
    private final Sprite sprite;
    private final TiledMap map;
    private final TextureAtlas textureAtlas = new TextureAtlas("sprite-atlases/character-sprites.atlas");
    public Player(@NotNull TiledMap map, @NotNull Vector2 startPosition) {
        super();
        this.map = map;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        final int maxHeight = layer.getHeight() * layer.getTileHeight();
        final int maxWidth = layer.getWidth() * layer.getTileWidth();
        mapScale = Math.max(Gdx.graphics.getWidth() / maxWidth, Gdx.graphics.getHeight() / maxHeight);
        sprite = textureAtlas.createSprite("char3_left");
        sprite.setPosition(startPosition.x, startPosition.y);
        sprite.setAlpha(1);
        // scale sprite
        sprite.setSize(sprite.getWidth() * mapScale, sprite.getHeight() * mapScale);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        loadMapObjectBoundingBoxes();
    }

    @Getter
    private enum Movement {
        UP, DOWN, LEFT, RIGHT, BOOST;

        private boolean is;

        void set(boolean is) {
            this.is = is;
        }
    }

    /**
     * todo: fix this
     * @param scale the scale to resize the sprite to
     */
    public void resize(float scale) {
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition(sprite.getX() * scale, sprite.getY() * scale);
        mapScale = scale;
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void move() {
        final TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        final int maxHeight = layer.getHeight() * layer.getTileHeight();
        final int maxWidth = layer.getWidth() * layer.getTileWidth();
        final float maxHeightScaled = maxHeight * mapScale;
        final float maxWidthScaled = maxWidth * mapScale;

        final int amount = Movement.BOOST.is ? 4 : 2;
        if (Movement.UP.is && (sprite.getY() + sprite.getHeight() < maxHeightScaled)) {
            sprite.translateY(amount);
            setY(sprite.getY());
        }
        if (Movement.DOWN.is && (sprite.getY() > 0)) {
            sprite.translateY(-amount);
            setY(sprite.getY());
        }
        if (Movement.LEFT.is && (sprite.getX() > 0)) {
            sprite.translateX(-amount);
            setX(sprite.getX());
        }
        if (Movement.RIGHT.is && (sprite.getX() + sprite.getWidth() < maxWidthScaled)) {
            sprite.translateX(amount);
            setX(sprite.getX());
        }
    }

    public enum Transition {
        NEW_MAP, ACTIVITY
    }

    private final HashMap<MapObject, BoundingBox> tileObjectBoundingBoxes = new HashMap<>();

    public BoundingBox getTileObjectBoundingBox(@NotNull MapObject object) {
        float x = object.getProperties().get("x", Float.class);
        float y = object.getProperties().get("y", Float.class);
        float width = object.getProperties().get("width", Float.class);
        float height = object.getProperties().get("height", Float.class);

        float xScaled = x * mapScale;
        float yScaled = y * mapScale;
        float widthScaled = width * mapScale;
        float heightScaled = height * mapScale;

        Vector3 pos1 = new Vector3(xScaled, yScaled, 0);
        Vector3 pos2 = new Vector3(xScaled + widthScaled, yScaled + heightScaled, 0);
        return new BoundingBox(pos1, pos2);
    }

    public MapObjects getMapObjects() {
        MapLayer gameObjects = map.getLayers().get("gameObjects");
        return gameObjects.getObjects();
    }

    public void loadMapObjectBoundingBoxes() {
        MapObjects objects = getMapObjects();
        for (MapObject object : objects) {
            BoundingBox boundingBox = getTileObjectBoundingBox(object);
            tileObjectBoundingBoxes.put(object, boundingBox);
        }
    }

    public @Nullable MapObject getCurrentMapObject() {
        for (Map.Entry<MapObject, BoundingBox> entry : tileObjectBoundingBoxes.entrySet()) {
            if (entry.getValue().contains(new Vector3(sprite.getX(), sprite.getY(), 0))) {
                return entry.getKey();
            }
        }
        return null;
    }

    public @Nullable Transition isInTransitionTile() {
        // if tile has property "isNewMap" or "isActivity" set to true, return true
        MapObject tileObject = getCurrentMapObject();
        if (tileObject == null) return null;
        System.out.println(tileObject.getProperties());
        if (Boolean.TRUE.equals(tileObject.getProperties().get("isNewMap", Boolean.class))) {
            return Transition.NEW_MAP;
        } else if (Boolean.TRUE.equals(tileObject.getProperties().get("isActivity", Boolean.class))) {
            return Transition.ACTIVITY;
        }
        return null;
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        sprite.draw(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                Movement.UP.set(true);
                break;
            case Input.Keys.S:
                Movement.DOWN.set(true);
                break;
            case Input.Keys.A:
                Movement.LEFT.set(true);
                break;
            case Input.Keys.D:
                Movement.RIGHT.set(true);
                break;
            case Input.Keys.CONTROL_LEFT:
                Movement.BOOST.set(true);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                Movement.UP.set(false);
                break;
            case Input.Keys.S:
                Movement.DOWN.set(false);
                break;
            case Input.Keys.A:
                Movement.LEFT.set(false);
                break;
            case Input.Keys.D:
                Movement.RIGHT.set(false);
                break;
            case Input.Keys.CONTROL_LEFT:
                Movement.BOOST.set(false);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void dispose() {
        textureAtlas.dispose();
    }
}
