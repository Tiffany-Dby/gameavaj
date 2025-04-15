package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.sup_de_vinci.gameavaj.enums.Coord;

public interface Collectible {
  void update(Coord playerCoord);

  void render(SpriteBatch batch);

  boolean isCollected();

  int getValue();

  void collect();
}
