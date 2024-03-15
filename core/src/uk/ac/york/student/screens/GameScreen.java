package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import lombok.Getter;
import uk.ac.york.student.GdxGame;

public class GameScreen extends BaseScreen {
    @Getter
    private final Stage processor;
    private TiledMap map;
    private TiledMapRenderer renderer;
    public GameScreen(GdxGame game) {
        super(game);
        TmxMapLoader.Parameters parameter = new TmxMapLoader.Parameters();
        parameter.textureMinFilter = Texture.TextureFilter.Nearest;
        parameter.textureMagFilter = Texture.TextureFilter.Nearest;
        map = new TmxMapLoader().load("map/map.tmx", parameter);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        int tileHeight = layer.getTileHeight();
        float scale = Math.max(Gdx.graphics.getWidth() / (layer.getWidth() * tileWidth), Gdx.graphics.getHeight() / (layer.getHeight() * tileHeight));
        renderer = new OrthogonalTiledMapRenderer(map, scale);

        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();


        processor.getViewport().update((int) width, (int) height);

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        OrthographicCamera camera = (OrthographicCamera) processor.getCamera();
        camera.update();

        // render map and make it 2x bigger than screen size
        renderer.setView(camera);
        renderer.render();

        processor.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        processor.draw();
    }

    @Override
    public void resize(int i, int i1) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        int tileHeight = layer.getTileHeight();
        float scale = Math.max(Gdx.graphics.getWidth() / (layer.getWidth() * tileWidth), Gdx.graphics.getHeight() / (layer.getHeight() * tileHeight));
        renderer = new OrthogonalTiledMapRenderer(map, scale);

        processor.getViewport().update(i, i1, true);
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

    }
}
