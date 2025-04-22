package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.utils.Coord;

/**
 * Abstract base class representing a collectible object placed on a tile in the
 * game map
 * Provides shared logic for positioning and collection behavior
 */
public abstract class AbstractCollectible implements Collectible {
  protected final Coord tileCoord;
  protected boolean collected = false;

  /**
   * Constructs a collectible at the given tile coordinates
   *
   * @param tileX The X coordinate of the tile
   * @param tileY The Y coordinate of the tile
   */
  public AbstractCollectible(int tileX, int tileY) {
    this.tileCoord = new Coord(tileX, tileY);
  }

  /**
   * Computes the center pixel position for rendering the collectible, given a
   * sprite size
   *
   * @param spriteSize The size of the sprite to center
   * @return A Vector2 representing the centered pixel position
   */
  public Vector2 getCenteredPixelPosition(float spriteSize) {
    float offset = (MapManager.TILE_SIZE - spriteSize) / 2f;

    return new Vector2(tileCoord.getX(), tileCoord.getY())
        .scl(MapManager.TILE_SIZE)
        .add(offset, offset);
  }

  /**
   * Updates the collectible state. Marks it as collected if the player is on the
   * same tile
   *
   * @param playerCoord The current tile coordinate of the player
   */
  @Override
  public void update(Coord playerCoord) {
    if (collected)
      return;

    if (playerCoord.getX() == tileCoord.getX() && playerCoord.getY() == tileCoord.getY()) {
      collect();
    }
  }

  /** @return {@code true} if the collectible has already been collected */
  @Override
  public boolean isCollected() {
    return collected;
  }

  /**
   * Marks this collectible as collected
   */
  @Override
  public void collect() {
    collected = true;
  }
}
