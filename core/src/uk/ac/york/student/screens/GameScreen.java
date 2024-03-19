package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.map.ActionMapObject;
import uk.ac.york.student.map.ActivityMapObject;
import uk.ac.york.student.map.MapManager;
import uk.ac.york.student.map.TransitionMapObject;
import uk.ac.york.student.player.Player;

import java.util.concurrent.atomic.AtomicReference;

public class GameScreen extends BaseScreen implements InputProcessor {
    private static final int ACTION_KEY = Input.Keys.E;
    @Getter
    private final Stage processor;
    private final Player player;
    private TiledMap map = MapManager.getMaps().getResult("map");
    private float mapScale;
    private TiledMapRenderer renderer;
    private final Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);
    private final Table table = new Table(craftacularSkin);
    private final Label actionLabel = new Label("ENG1 Project. Super cool. (You will never see this)", craftacularSkin);
    public GameScreen(GdxGame game) {
        super(game);

        // Set up the tilemap
        // Cannot extract into a method because class variables are set as final
        //#region Load Tilemap
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        int tileHeight = layer.getTileHeight();
        mapScale = Math.max(Gdx.graphics.getWidth() / (layer.getWidth() * tileWidth), Gdx.graphics.getHeight() / (layer.getHeight() * tileHeight));
        renderer = new OrthogonalTiledMapRenderer(map, mapScale);
        //#endregion

        Vector2 startingPoint = new Vector2(25, 25);
        MapLayer gameObjectsLayer = map.getLayers().get("gameObjects");
        MapObjects objects = gameObjectsLayer.getObjects();
        for (MapObject object : objects) {
            if (!object.getName().equals("startingPoint")) continue;
            MapProperties properties = object.getProperties();
            if (!properties.containsKey("spawnpoint")) continue;
            Boolean spawnpoint = properties.get("spawnpoint", Boolean.class);
            if (spawnpoint == null || Boolean.FALSE.equals(spawnpoint)) continue;
            RectangleMapObject rectangleObject = (RectangleMapObject) object;
            Rectangle rectangle = rectangleObject.getRectangle();
            startingPoint = new Vector2(rectangle.getX() * mapScale, rectangle.getY() * mapScale);
            break;
        }


        player = new Player(map, startingPoint);

        processor = new Stage(new ScreenViewport());
        renderer.setView((OrthographicCamera) processor.getCamera());
        Gdx.input.setInputProcessor(processor);

        processor.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return GameScreen.this.keyDown(keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return GameScreen.this.keyUp(keycode);
            }
        });
    }

    public void changeMap(String mapName) {
        map.dispose();
        map = MapManager.getMaps().getResult(mapName);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        int tileHeight = layer.getTileHeight();
        mapScale = Math.max(Gdx.graphics.getWidth() / (layer.getWidth() * tileWidth), Gdx.graphics.getHeight() / (layer.getHeight() * tileHeight));
        renderer = new OrthogonalTiledMapRenderer(map, mapScale);

        Vector2 startingPoint = new Vector2(25, 25);
        MapLayer gameObjectsLayer = map.getLayers().get("gameObjects");
        MapObjects objects = gameObjectsLayer.getObjects();
        for (MapObject object : objects) {
            if (!object.getName().equals("startingPoint")) continue;
            MapProperties properties = object.getProperties();
            if (!properties.containsKey("spawnpoint")) continue;
            Boolean spawnpoint = properties.get("spawnpoint", Boolean.class);
            if (spawnpoint == null || Boolean.FALSE.equals(spawnpoint)) continue;
            RectangleMapObject rectangleObject = (RectangleMapObject) object;
            Rectangle rectangle = rectangleObject.getRectangle();
            startingPoint = new Vector2(rectangle.getX() * mapScale, rectangle.getY() * mapScale);
            break;
        }

        player.setPosition(startingPoint);
        player.setMap(map);

    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Batch batch = processor.getBatch();
        batch.begin();
        player.draw(batch, 1f);
        batch.end();

        table.setFillParent(true);
        processor.addActor(table);
        table.bottom();
        table.padBottom(10);

        processor.getViewport().update((int) width, (int) height);

    }

    private final AtomicReference<@Nullable ActionMapObject> currentActionMapObject = new AtomicReference<>(null);

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);

        player.move();

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        OrthographicCamera camera = (OrthographicCamera) processor.getCamera();
        // Calculate the player's position (center of the player's sprite)
        float playerCenterX = player.getX() + player.getWidth() / 2;
        float playerCenterY = player.getY() + player.getHeight() / 2;

        // Calculate the minimum and maximum x and y coordinates for the camera
        float cameraMinX = camera.viewportWidth / 2;
        float cameraMinY = camera.viewportHeight / 2;
        float cameraMaxX = (map.getProperties().get("width", Integer.class) * layer.getTileWidth() * mapScale) - cameraMinX;
        float cameraMaxY = (map.getProperties().get("height", Integer.class) * layer.getTileHeight() * mapScale) - cameraMinY;

        // Set the camera's position to the player's position, but constrained within the minimum and maximum x and y coordinates
        camera.position.set(Math.min(Math.max(playerCenterX, cameraMinX), cameraMaxX), Math.min(Math.max(playerCenterY, cameraMinY), cameraMaxY), 0);
        camera.update();
        table.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2);

        renderer.setView(camera);
        renderer.render();

        Batch batch = processor.getBatch();
        batch.begin();
        player.draw(batch, 1f);
        batch.end();

        Player.Transition transitionTile = player.isInTransitionTile();
        if (transitionTile != null) {
            ActionMapObject actionMapObject = getActionMapObject(transitionTile, player.getCurrentMapObject());
            currentActionMapObject.set(actionMapObject);
            String actionText = getActionText(actionMapObject);
            actionLabel.setText(actionText);
            table.add(actionLabel);
        } else {
            currentActionMapObject.set(null);
            table.clearChildren();
        }

        processor.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        processor.draw();
    }

    @NotNull
    private String getActionText(ActionMapObject actionMapObject) {
        String actionText = "Press " + Input.Keys.toString(ACTION_KEY) + " to ";
        actionText += actionMapObject.getStr();
        return actionText;
    }

    @NotNull
    private static ActionMapObject getActionMapObject(Player.@NotNull Transition transitionTile, MapObject tileObject) {
        ActionMapObject actionMapObject;
        if (transitionTile.equals(Player.Transition.ACTIVITY)) {
            actionMapObject = new ActivityMapObject(tileObject);
        } else if (transitionTile.equals(Player.Transition.NEW_MAP)) {
            actionMapObject = new TransitionMapObject(tileObject);
        } else {
            throw new IllegalStateException("Unexpected value: " + transitionTile);
        }
        return actionMapObject;
    }

    @Override
    public void resize(int screenWidth, int screenHeight) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        int tileHeight = layer.getTileHeight();
        mapScale = Math.max(Gdx.graphics.getWidth() / (float)(layer.getWidth() * tileWidth), Gdx.graphics.getHeight() / (float)(layer.getHeight() * tileHeight));
        renderer = new OrthogonalTiledMapRenderer(map, mapScale);

        OrthographicCamera camera = (OrthographicCamera) processor.getCamera();
        camera.viewportWidth = screenWidth;
        camera.viewportHeight = screenHeight;

        processor.getViewport().update(screenWidth, screenHeight, true);

        // Calculate the player's position (center of the player's sprite)
        float playerCenterX = player.getX() + player.getWidth() / 2;
        float playerCenterY = player.getY() + player.getHeight() / 2;

        // Calculate the minimum and maximum x and y coordinates for the camera
        float cameraMinX = camera.viewportWidth / 2;
        float cameraMinY = camera.viewportHeight / 2;
        float cameraMaxX = (map.getProperties().get("width", Integer.class) * layer.getTileWidth() * mapScale) - cameraMinX;
        float cameraMaxY = (map.getProperties().get("height", Integer.class) * layer.getTileHeight() * mapScale) - cameraMinY;

        // Set the camera's position to the player's position, but constrained within the minimum and maximum x and y coordinates
        camera.position.set(Math.min(Math.max(playerCenterX, cameraMinX), cameraMaxX), Math.min(Math.max(playerCenterY, cameraMinY), cameraMaxY), 0);
        camera.update();

        renderer.setView(camera);

        processor.getViewport().update(screenWidth, screenHeight, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        processor.dispose();
        craftacularSkin.dispose();
        player.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean playerKeyDown = player.keyDown(keycode);
        if (keycode == ACTION_KEY) {
            ActionMapObject actionMapObject = currentActionMapObject.get();
            if (actionMapObject != null) {
                if (actionMapObject instanceof ActivityMapObject) {

                } else if (actionMapObject instanceof TransitionMapObject) {
                    TransitionMapObject transitionMapObject = (TransitionMapObject) actionMapObject;
                    changeMap(transitionMapObject.getType());
                } else {
                    throw new IllegalStateException("Unexpected value: " + actionMapObject);
                }
            }
        }
        return playerKeyDown;
    }

    @Override
    public boolean keyUp(int keycode) {
        return player.keyUp(keycode);
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
}
