package fr.sup_de_vinci.gameavaj.enemy;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class Enemy {
    private static final String SPRITE_PATH = "enemy-walk.png";
    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 64;
    private static final float FRAME_DURATION = 0.1f;

    private final Texture spriteSheet;
    private final EnumMap<Direction, Animation<TextureRegion>> animations = new EnumMap<>(Direction.class);

    private Vector2 renderPos;
    private State currentState;

    public Enemy(int x, int y) {
        this.renderPos = new Vector2(x * MapManager.TILE_SIZE, y * MapManager.TILE_SIZE);
        this.spriteSheet = new Texture(SPRITE_PATH);

        initializeAnimations();
        this.currentState = new StateIdle(animations, Direction.LEFT, new Coord(x, y));
    }

    private void initializeAnimations() {
        TextureRegion[][] frames = TextureRegion.split(spriteSheet, FRAME_WIDTH, FRAME_HEIGHT);
        animations.put(Direction.DOWN, new Animation<>(FRAME_DURATION, frames[0]));
        animations.put(Direction.UP, new Animation<>(FRAME_DURATION, frames[1]));
        animations.put(Direction.LEFT, new Animation<>(FRAME_DURATION, frames[2]));
        animations.put(Direction.RIGHT, new Animation<>(FRAME_DURATION, frames[3]));
    }

    public int getTileX() {
        return (int) (this.renderPos.x / MapManager.TILE_SIZE);
    }

    public int getTileY() {
        return (int) (this.renderPos.y / MapManager.TILE_SIZE);
    }

    public Coord getTileCoord() {
        return new Coord(getTileX(), getTileY());
    }

    public void update(float deltaTime) {
        this.currentState = currentState.update(deltaTime, renderPos);
    }

    public void render(SpriteBatch batch) {
        this.currentState.render(batch, renderPos);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}