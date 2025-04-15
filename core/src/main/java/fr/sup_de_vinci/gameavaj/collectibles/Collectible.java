package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface Collectible {
  void update(Vector2 playerPos);

  void render(SpriteBatch batch);

  boolean isCollected();

  int getValue();

  void collect();
}
