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

/*
 * public class Enemy {
 * 
 * private float x, y;
 * private Direction direction;
 * private float speed;
 * 
 * public Enemy(float x, float y, Direction direction, float speed) {
 * this.x = x;
 * this.y = y;
 * this.direction = direction;
 * this.speed = speed;
 * }
 * 
 * public int getCellX() {
 * return (int) (x / MapManager.TILE_SIZE);
 * }
 * 
 * public int getCellY() {
 * return (int) (y / MapManager.TILE_SIZE);
 * }
 * 
 * public void snapToGrid() {
 * this.x = Math.round(x / MapManager.TILE_SIZE) * MapManager.TILE_SIZE;
 * this.y = Math.round(y / MapManager.TILE_SIZE) * MapManager.TILE_SIZE;
 * }
 * 
 * public boolean isCenteredOnTile() {
 * float tolerance = 1f;
 * float modX = x % MapManager.TILE_SIZE;
 * float modY = y % MapManager.TILE_SIZE;
 * return Math.abs(modX) < tolerance && Math.abs(modY) < tolerance;
 * }
 * 
 * public void move(float dx, float dy) {
 * this.x += dx;
 * this.y += dy;
 * }
 * 
 * public float getX() {
 * return x;
 * }
 * 
 * public float getY() {
 * return y;
 * }
 * 
 * public float getSpeed() {
 * return speed;
 * }
 * 
 * public Direction getDirection() {
 * return direction;
 * }
 * 
 * public void setDirection(Direction direction) {
 * this.direction = direction;
 * }
 * }
 */