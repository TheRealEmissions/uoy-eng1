package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.assets.map.ActionMapObject;
import uk.ac.york.student.assets.map.ActivityMapObject;
import uk.ac.york.student.assets.map.MapManager;
import uk.ac.york.student.assets.map.TransitionMapObject;
import uk.ac.york.student.game.GameTime;
import uk.ac.york.student.player.Player;
import uk.ac.york.student.player.PlayerMetric;
import uk.ac.york.student.player.PlayerMetrics;
import uk.ac.york.student.utils.StreamUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GameScreen extends BaseScreen implements InputProcessor {
    private static final int ACTION_KEY = Input.Keys.E;
    @Getter
    private final Stage processor;
    private final Player player;
    private final GameTime gameTime;
    private TiledMap map = MapManager.getMaps().getResult("map");
    private float mapScale;
    private TiledMapRenderer renderer;
    private final Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);
    private final Table actionTable = new Table(craftacularSkin);
    private final Table metricsTable = new Table();
    private final Table timeTable = new Table();
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
        gameTime = new GameTime(mapScale);
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
        // make the screen black slowly
        processor.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        sequenceAction.addAction(Actions.run(() -> {
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

            player.setMap(map, startingPoint);
            gameTime.updateProgressBar(mapScale);

            renderer.setView((OrthographicCamera) processor.getCamera());

            Gdx.input.setInputProcessor(processor);
        }));
        sequenceAction.addAction(Actions.fadeIn(0.5f));
        processor.getRoot().addAction(sequenceAction);
    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Batch batch = processor.getBatch();
        batch.begin();
        player.draw(batch, 1f);
        batch.end();

        actionTable.setFillParent(true);
        processor.addActor(actionTable);
        actionLabel.setVisible(false);
        actionTable.add(actionLabel);
        actionTable.bottom();
        actionTable.padBottom(10);

        metricsTable.setFillParent(true);
        metricsTable.setWidth(500);
        processor.addActor(metricsTable);
        PlayerMetrics metrics = player.getMetrics();
        List<ProgressBar> playerMetrics = metrics.getMetrics().stream().map(PlayerMetric::getProgressBar).collect(Collectors.toList());
        List<String> metricLabels = metrics.getMetrics().stream().map(PlayerMetric::getLabel).collect(Collectors.toList());
        for (int i = 0; i < playerMetrics.size(); i++) {
            ProgressBar progressBar = playerMetrics.get(i);
            String label = metricLabels.get(i);
            metricsTable.add(new Label(label, craftacularSkin)).padRight(10).padBottom(10);
            metricsTable.add(progressBar).width(200).padBottom(10);
            metricsTable.row();
        }
        metricsTable.bottom().right();
        metricsTable.padBottom(10);
        metricsTable.padRight(20);

        ProgressBar timeBar = gameTime.getProgressBar();
        timeTable.setFillParent(true);
        processor.addActor(timeTable);
        timeTable.setWidth(500);
        timeTable.add(new Label("Time", craftacularSkin));
        timeTable.row();
        timeTable.add(timeBar).width(500);
        timeTable.top();
        timeTable.padTop(10);


        processor.getViewport().update((int) width, (int) height);

    }

    private final AtomicReference<@Nullable ActionMapObject> currentActionMapObject = new AtomicReference<>(null);

    @Override
    public void render(float v) {
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        player.move();
        player.setOpacity(processor.getRoot().getColor().a);

        StreamUtils.parallelFromIterator(map.getLayers().iterator()).forEach(l -> l.setOpacity(processor.getRoot().getColor().a));

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
        actionTable.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2);
        metricsTable.setPosition(camera.position.x + camera.viewportWidth / 2 - metricsTable.getWidth(), camera.position.y - camera.viewportHeight / 2);
        timeTable.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y + camera.viewportHeight / 2 - timeTable.getHeight());

        renderer.setView(camera);
        renderer.render();

        Batch batch = processor.getBatch();
        batch.begin();

        player.draw(batch, processor.getRoot().getColor().a);
        batch.end();

        Player.Transition transitionTile = player.isInTransitionTile();
        if (transitionTile != null) {
            ActionMapObject actionMapObject = getActionMapObject(transitionTile, player.getCurrentMapObject());
            currentActionMapObject.set(actionMapObject);
            String actionText = getActionText(actionMapObject);
            actionLabel.setText(actionText);
            actionLabel.setVisible(true);
        } else {
            currentActionMapObject.set(null);
            actionLabel.setVisible(false);
        }

        processor.draw();
        processor.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
                    ActivityMapObject activityMapObject = (ActivityMapObject) actionMapObject;
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
