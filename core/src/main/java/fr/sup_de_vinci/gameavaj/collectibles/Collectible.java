package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.sup_de_vinci.gameavaj.enums.Coord;

/**
 * Represents a collectible item in the game world
 * Collectibles can be rendered, updated based on player position,
 * and queried for collection status and value
 */
public interface Collectible {
  /**
   * Updates the collectible's state based on the player's position
   * If the player is on the same tile, the collectible may be marked as
   * collected
   *
   * @param playerCoord The current tile coordinate of the player
   */
  void update(Coord playerCoord);

  /**
   * Renders the collectible on screen using the given SpriteBatch
   *
   * @param batch The SpriteBatch used for drawing
   */
  void render(SpriteBatch batch);

  /**
   * Checks if this collectible has already been collected
   *
   * @return {@code true} if collected; {@code false} otherwise
   */
  boolean isCollected();

  /**
   * Returns the value of this collectible, used for scoring or effects
   *
   * @return The integer value of the collectible
   */
  int getValue();

  /**
   * Marks this collectible as collected
   */
  void collect();
}
