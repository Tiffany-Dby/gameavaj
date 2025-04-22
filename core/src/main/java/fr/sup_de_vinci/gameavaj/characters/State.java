package fr.sup_de_vinci.gameavaj.characters;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Direction;

/**
 * Abstract base class representing a visual and logical state of a character
 * Handles rendering and updating animation frames according to direction and
 * time
 */
public abstract class State {
  private EnumMap<Direction, Animation<TextureRegion>> animations;
  private Direction direction;

  /**
   * Constructs a new state with the provided directional animations and initial
   * direction
   *
   * @param animations Map of animations per direction
   * @param direction  The current facing direction of the character
   */
  public State(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction) {
    this.animations = animations;
    this.direction = direction;
  }

  /**
   * Returns the map of animations used by this state
   *
   * @return The directional animation map
   */
  public EnumMap<Direction, Animation<TextureRegion>> getAnims() {
    return this.animations;
  }

  /**
   * Retrieves the animation matching the current direction
   *
   * @return Animation for the current direction
   */
  protected Animation<TextureRegion> getCurrentAnim() {
    return this.animations.get(this.direction);
  }

  /**
   * Gets the direction associated with this state
   *
   * @return The direction of the character
   */
  public Direction getDirection() {
    return this.direction;
  }

  /**
   * Updates the state based on logic, such as user input or AI
   *
   * @param controller Logic controller determining transitions
   * @param deltaTime  Time since the last frame (in seconds)
   * @param pos        The current render position of the character
   * @return A potentially new state to transition to
   */
  public abstract State update(CharacterController controller, float deltaTime, Vector2 pos);

  /**
   * Renders the current animation frame for this state
   *
   * @param batch     SpriteBatch used for rendering
   * @param renderPos Render position in pixels
   */
  public abstract void render(SpriteBatch batch, Vector2 renderPos);

  /**
   * Utility method to render a specific frame of the current animation
   *
   * @param batch     SpriteBatch for rendering
   * @param renderPos Position in map coordinates
   * @param stateTime Accumulated time in current state (used for animation
   *                  timing)
   * @param looped    Whether the animation should loop
   */
  protected void render(SpriteBatch batch, Vector2 renderPos, float stateTime, boolean looped) {
    batch.draw(this.getCurrentAnim().getKeyFrame(stateTime, looped), renderPos.x, renderPos.y);
  }
}
