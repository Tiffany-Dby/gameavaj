package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.map.MapManager;

public abstract class AbstractCollectible implements Collectible {
  protected final Vector2 tilePos;
  protected boolean collected = false;

  public AbstractCollectible(float tileX, float tileY) {
    this.tilePos = new Vector2(tileX, tileY);
  }

  protected Vector2 getCenteredPixelPosition(float spriteSize) {
    float offset = (MapManager.TILE_SIZE - spriteSize) / 2f;

    return tilePos.cpy()
        .scl(MapManager.TILE_SIZE)
        .add(offset, offset);
  }

  @Override
  public void update(Vector2 playerPos) {
    if (collected)
      return;

    float halfTileSize = MapManager.TILE_SIZE / 2f;

    Vector2 dotCenter = getCenteredPixelPosition(0);
    Vector2 playerCenter = playerPos.cpy().add(halfTileSize, halfTileSize);

    if (playerCenter.dst2(dotCenter) < 1f) {
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
