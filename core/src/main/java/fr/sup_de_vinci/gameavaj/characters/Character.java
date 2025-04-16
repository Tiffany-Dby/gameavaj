package fr.sup_de_vinci.gameavaj.characters;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.utils.Position;

public class Character {

  private static final int FRAME_WIDTH = 64;
  private static final int FRAME_HEIGHT = 64;
  private static final float FRAME_DURATION = 0.1f;

  private EnumMap<Direction, Animation<TextureRegion>> animations = null;
  private EnumMap<Direction, Animation<TextureRegion>> deathAnimations = null;

  private final Vector2 renderPos;
  private final CharacterController controller;
  private State currentState;
  private Texture spriteSheet = null, deathSheet = null;

  public Character(CharacterController controller, Coord initialPos, String animMovePath) {
    this(controller, initialPos, animMovePath, null);
  }

  public Character(CharacterController controller, Coord initialPos, String animMovePath, String animDeathPath) {
    this.controller = controller;
    this.renderPos = new Vector2(
        initialPos.getX() * MapManager.TILE_SIZE,
        initialPos.getY() * MapManager.TILE_SIZE);
    this.spriteSheet = new Texture(animMovePath);

    animations = loadAnims(this.spriteSheet);
    if (animDeathPath != null) {
      deathSheet = new Texture(animDeathPath);
      deathAnimations = loadAnims(deathSheet);
    }
    this.currentState = new StateIdle(animations, Direction.LEFT);
  }

  private static EnumMap<Direction, Animation<TextureRegion>> loadAnims(Texture spriteSheet) {
    EnumMap<Direction, Animation<TextureRegion>> anims = new EnumMap<>(Direction.class);

    TextureRegion[][] frames = TextureRegion.split(spriteSheet, FRAME_WIDTH, FRAME_HEIGHT);

    for (int i = 0; i < Direction.ALL.length; i++) {
      anims.put(Direction.ALL[i], new Animation<>(FRAME_DURATION, frames[i]));
    }
    return anims;
  }

  public void die() {
    if (deathAnimations == null) {
      throw new IllegalStateException("This character cannot die");
    }
    this.currentState = new StateDying(deathAnimations, this.currentState.getDirection());
  }

  public boolean isDead() {
    return this.currentState instanceof StateDead
        || this.currentState instanceof StateDying;
  }

  public boolean isCenteredOnTile() {
    float halfTile = MapManager.TILE_SIZE / 2f;

    Vector2 playerCenter = renderPos.cpy().add(halfTile, halfTile);
    Vector2 tileCenter = Position.getTileCenter(getTileCoord());

    return playerCenter.dst2(tileCenter) < 0.1f;
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
    this.currentState = currentState.update(this.controller, deltaTime, renderPos);
  }

  public void render(SpriteBatch batch) {
    this.currentState.render(batch, renderPos);
  }

  public void dispose() {
    spriteSheet.dispose();
    if (deathSheet != null) {
      deathSheet.dispose();
    }
  }
}
