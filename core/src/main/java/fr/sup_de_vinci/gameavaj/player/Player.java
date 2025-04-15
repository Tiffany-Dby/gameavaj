package fr.sup_de_vinci.gameavaj.player;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class Player {
  private static final String WALK_SPRITE_PATH = "player-walk.png";
  private static final String DEATH_SPRITE_PATH = "player-death.png";
  private static final int FRAME_WIDTH = 64;
  private static final int FRAME_HEIGHT = 64;
  private static final float FRAME_DURATION = 0.1f;

  private final Texture walkSheet;
  private final Texture deathSheet;
  private final EnumMap<Direction, Animation<TextureRegion>> animations = new EnumMap<>(Direction.class);
  private final EnumMap<Direction, Animation<TextureRegion>> deathAnimations = new EnumMap<>(Direction.class);

  private Vector2 renderPos;

  private State currentState;
  private float x, y;

  public Player(int x, int y) {
    if (!MapManager.isWalkable(x, y)) {
      throw new IllegalArgumentException("Invalid starting tile — not walkable.");
    }

    this.renderPos = new Vector2(
        x * MapManager.TILE_SIZE,
        y * MapManager.TILE_SIZE);

    walkSheet = new Texture(WALK_SPRITE_PATH);
    deathSheet = new Texture(DEATH_SPRITE_PATH);
    initializeAnimations();
    this.currentState = new StateIdle(animations, Direction.DOWN, new Coord(x, y));
  }

  private void initializeAnimations() {
    TextureRegion[][] frames = TextureRegion.split(walkSheet, FRAME_WIDTH, FRAME_HEIGHT);
    animations.put(Direction.DOWN, new Animation<>(FRAME_DURATION, frames[0]));
    animations.put(Direction.UP, new Animation<>(FRAME_DURATION, frames[1]));
    animations.put(Direction.LEFT, new Animation<>(FRAME_DURATION, frames[2]));
    animations.put(Direction.RIGHT, new Animation<>(FRAME_DURATION, frames[3]));

    TextureRegion[][] deathFrames = TextureRegion.split(deathSheet, FRAME_WIDTH, FRAME_HEIGHT);
    deathAnimations.put(Direction.DOWN, new Animation<>(FRAME_DURATION, deathFrames[0]));
    deathAnimations.put(Direction.UP, new Animation<>(FRAME_DURATION, deathFrames[1]));
    deathAnimations.put(Direction.LEFT, new Animation<>(FRAME_DURATION, deathFrames[2]));
    deathAnimations.put(Direction.RIGHT, new Animation<>(FRAME_DURATION, deathFrames[3]));

    for (Animation<TextureRegion> anim : deathAnimations.values()) {
      anim.setPlayMode(Animation.PlayMode.NORMAL);
    }
  }

  public boolean isDead() {
    return this.currentState instanceof StateDead
        || this.currentState instanceof StateDying;
  }

  public int getTileX() {
    return (int) (this.renderPos.x / MapManager.TILE_SIZE);
  }

  public int getTileY() {
    return (int) (this.renderPos.y / MapManager.TILE_SIZE);
  }

  public Vector2 getRenderPos() {
    return renderPos;
  }

  public void die() {
    this.currentState = new StateDying(deathAnimations, this.currentState.getDirection());
  }

  public void update(float deltaTime) {
    this.currentState = this.currentState.update(deltaTime, this.renderPos);
  }

  public void render(SpriteBatch batch) {
    this.currentState.render(batch, this.renderPos);
  }

  public void dispose() {
    walkSheet.dispose();
    deathSheet.dispose();
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }
}
