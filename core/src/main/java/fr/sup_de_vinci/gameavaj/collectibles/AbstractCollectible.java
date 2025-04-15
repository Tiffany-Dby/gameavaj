package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public abstract class AbstractCollectible implements Collectible {
  protected final Coord tileCoord;
  protected boolean collected = false;

  public AbstractCollectible(int tileX, int tileY) {
    this.tileCoord = new Coord(tileX, tileY);
  }

  public Vector2 getCenteredPixelPosition(float spriteSize) {
    float offset = (MapManager.TILE_SIZE - spriteSize) / 2f;

    return new Vector2(tileCoord.getX(), tileCoord.getY())
        .scl(MapManager.TILE_SIZE)
        .add(offset, offset);
  }

  @Override
  public void update(Coord playerCoord) {
    if (collected)
      return;

    if (playerCoord.getX() == tileCoord.getX() && playerCoord.getY() == tileCoord.getY()) {
      collect();
    }
  }

  @Override
  public boolean isCollected() {
    return collected;
  }

  @Override
  public void collect() {
    collected = true;
  }
}
