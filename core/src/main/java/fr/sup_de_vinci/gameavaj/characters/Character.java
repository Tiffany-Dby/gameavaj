package fr.sup_de_vinci.gameavaj.characters;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.utils.Coord;
import fr.sup_de_vinci.gameavaj.utils.Direction;
import fr.sup_de_vinci.gameavaj.utils.Position;

/**
 * Represents a generic game character that can move, animate, and die
 * This class handles animation states and delegates movement logic to a
 * CharacterController
 */
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

  /**
   * Constructs a Character with no death animation
   * 
   * @param controller   The character's controller, which handles movement logic
   * @param initialPos   The tile coordinate of the character's spawn
   * @param animMovePath The file path for the movement sprite sheet
   */
  public Character(CharacterController controller, Coord initialPos, String animMovePath) {
    this(controller, initialPos, animMovePath, null);
  }

  /**
   * Constructs a Character with optional death animation
   * 
   * @param controller    The character's controller
   * @param initialPos    The tile coordinate of the character's spawn
   * @param animMovePath  The file path for the movement sprite sheet
   * @param animDeathPath (Optional) The file path for the death sprite sheet
   */
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

  /**
   * Loads directional animations from a sprite sheet
   *
   * @param spriteSheet The texture containing animation frames
   * @return An EnumMap associating directions with animations
   */
  private static EnumMap<Direction, Animation<TextureRegion>> loadAnims(Texture spriteSheet) {
    EnumMap<Direction, Animation<TextureRegion>> anims = new EnumMap<>(Direction.class);

    TextureRegion[][] frames = TextureRegion.split(spriteSheet, FRAME_WIDTH, FRAME_HEIGHT);

    for (int i = 0; i < Direction.ALL.length; i++) {
      anims.put(Direction.ALL[i], new Animation<>(FRAME_DURATION, frames[i]));
    }
    return anims;
  }

  /**
   * Sets the character's state to dying, triggering death animation
   * 
   * @throws IllegalStateException if death animations are not defined
   */
  public void die() {
    if (deathAnimations == null) {
      throw new IllegalStateException("This character cannot die");
    }
    this.currentState = new StateDying(deathAnimations, this.currentState.getDirection());
  }

  /**
   * Checks if the character is currently dead or dying
   * 
   * @return true if in a dead/dying state, false otherwise
   */
  public boolean isDead() {
    return this.currentState instanceof StateDead
        || this.currentState instanceof StateDying;
  }

  /**
   * Checks if the character is currently centered on a tile
   *
   * @return true if centered, false otherwise
   */
  public boolean isCenteredOnTile() {
    float halfTile = MapManager.TILE_SIZE / 2f;

    Vector2 playerCenter = renderPos.cpy().add(halfTile, halfTile);
    Vector2 tileCenter = Position.getTileCenter(getTileCoord());

    return playerCenter.dst2(tileCenter) < 0.1f;
  }

  /** @return The tile X position of the character */
  public int getTileX() {
    return (int) (this.renderPos.x / MapManager.TILE_SIZE);
  }

  /** @return The tile Y position of the character */
  public int getTileY() {
    return (int) (this.renderPos.y / MapManager.TILE_SIZE);
  }

  /** @return The Coord of the tile the character is currently on */
  public Coord getTileCoord() {
    return new Coord(getTileX(), getTileY());
  }

  /**
   * Updates the character's state based on controller logic and animation
   * 
   * @param deltaTime The time delta for this frame
   */
  public void update(float deltaTime) {
    this.currentState = currentState.update(this.controller, deltaTime, renderPos);
  }

  /**
   * Renders the character based on its current state and animation
   * 
   * @param batch The SpriteBatch used for rendering
   */
  public void render(SpriteBatch batch) {
    this.currentState.render(batch, renderPos);
  }

  /**
   * Disposes of allocated texture resources
   */
  public void dispose() {
    spriteSheet.dispose();
    if (deathSheet != null) {
      deathSheet.dispose();
    }
  }
}
