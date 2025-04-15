package fr.sup_de_vinci.gameavaj.player;

import java.util.EnumMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class Player {
  private static final String SPRITE_PATH = "player-walk.png";
  private static final int FRAME_WIDTH = 64;
  private static final int FRAME_HEIGHT = 64;
  private static final float SPEED = 100f;
  private static final float FRAME_DURATION = 0.1f;
  private static final float SNAP_THRESHOLD = 1f;

  private final Texture walkSheet;
  private final EnumMap<Direction, Animation<TextureRegion>> animations = new EnumMap<>(Direction.class);
  private Animation<TextureRegion> currentAnimation;

  private Direction direction = Direction.DOWN;

  private float x, y;
  private float stateTime = 0f;
  private boolean moving = false;

  private int tileX, tileY;
  private int targetTileX, targetTileY;
  private boolean isMovingToTile = false;

  public Player(float x, float y) {
    tileX = (int) (x / MapManager.TILE_SIZE);
    tileY = (int) (y / MapManager.TILE_SIZE);
    targetTileX = tileX;
    targetTileY = tileY;

    this.x = tileX * MapManager.TILE_SIZE;
    this.y = tileY * MapManager.TILE_SIZE;

    walkSheet = new Texture(SPRITE_PATH);
    initializeAnimations();
    currentAnimation = animations.get(direction);
  }

  private void initializeAnimations() {
    TextureRegion[][] frames = TextureRegion.split(walkSheet, FRAME_WIDTH, FRAME_HEIGHT);

    animations.put(Direction.DOWN, new Animation<>(FRAME_DURATION, frames[0]));
    animations.put(Direction.UP, new Animation<>(FRAME_DURATION, frames[1]));
    animations.put(Direction.LEFT, new Animation<>(FRAME_DURATION, frames[2]));
    animations.put(Direction.RIGHT, new Animation<>(FRAME_DURATION, frames[3]));
  }

  private void handleInput() {
    if (isMovingToTile)
      return;

    Direction input = getPressedDirection();
    if (input == Direction.NONE)
      return;

    int[] offset = getDirectionOffset(input);
    int nextX = tileX + offset[0];
    int nextY = tileY + offset[1];

    if (MapManager.isWalkable(nextX, nextY)) {
      targetTileX = nextX;
      targetTileY = nextY;
      direction = input;
      isMovingToTile = true;
      moving = true;
    }
  }

  private void updateMovement(float deltaTime) {
    if (!isMovingToTile)
      return;

    float targetX = targetTileX * MapManager.TILE_SIZE;
    float targetY = targetTileY * MapManager.TILE_SIZE;

    float dx = targetX - x;
    float dy = targetY - y;

    float distance = SPEED * deltaTime;

    if (Math.abs(dx) > 0)
      x += Math.signum(dx) * Math.min(Math.abs(dx), distance);
    if (Math.abs(dy) > 0)
      y += Math.signum(dy) * Math.min(Math.abs(dy), distance);

    if (Math.abs(dx) <= SNAP_THRESHOLD && Math.abs(dy) <= SNAP_THRESHOLD)
      snapToTile(targetX, targetY);
  }

  private void snapToTile(float targetX, float targetY) {
    x = targetX;
    y = targetY;
    tileX = targetTileX;
    tileY = targetTileY;
    isMovingToTile = false;
    moving = false;
  }

  private int[] getDirectionOffset(Direction dir) {
    switch (dir) {
      case LEFT:
        return new int[] { -1, 0 };
      case RIGHT:
        return new int[] { 1, 0 };
      case UP:
        return new int[] { 0, 1 };
      case DOWN:
        return new int[] { 0, -1 };
      default:
        return new int[] { 0, 0 };
    }
  }

  private Direction getPressedDirection() {
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
      return Direction.LEFT;
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
      return Direction.RIGHT;
    if (Gdx.input.isKeyPressed(Input.Keys.UP))
      return Direction.UP;
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
      return Direction.DOWN;

    return Direction.NONE;
  }

  public void update(float deltaTime) {
    handleInput();
    updateMovement(deltaTime);

    if (moving) {
      stateTime += deltaTime;
    }

    currentAnimation = animations.get(direction);
  }

  public void render(SpriteBatch batch) {
    TextureRegion frame = moving
        ? currentAnimation.getKeyFrame(stateTime, true)
        : currentAnimation.getKeyFrame(0);

    batch.draw(frame, x, y);
  }

  public void dispose() {
    walkSheet.dispose();
  }
}
