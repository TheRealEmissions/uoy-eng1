package uk.ac.york.student.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Player extends Actor implements PlayerScore, InputProcessor {
    private static final float SPRITE_SCALE = 7.5f;
    private final Sprite sprite;
    private final TiledMap map;
    private final TextureAtlas textureAtlas = new TextureAtlas("sprite-atlases/character-sprites.atlas");
    public Player(TiledMap map, @NotNull Vector2 startPosition) {
        super();
        this.map = map;
        sprite = textureAtlas.createSprite("char3_left");
        sprite.setPosition(startPosition.x, startPosition.y);
        sprite.setAlpha(1);
        // scale sprite
        sprite.setSize(sprite.getWidth() * SPRITE_SCALE, sprite.getHeight() * SPRITE_SCALE);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());


    }

    @Getter
    private enum Movement {
        UP, DOWN, LEFT, RIGHT, BOOST;

        private boolean is;

        void set(boolean is) {
            this.is = is;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int amount = Movement.BOOST.is ? 4 : 2;
        if (Movement.UP.is) {
            sprite.translateY(amount);
        }
        if (Movement.DOWN.is) {
            sprite.translateY(-amount);
        }
        if (Movement.LEFT.is) {
            sprite.translateX(-amount);
        }
        if (Movement.RIGHT.is) {
            sprite.translateX(amount);
        }
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
