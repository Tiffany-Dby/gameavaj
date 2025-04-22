package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.sup_de_vinci.gameavaj.utils.Coord;

import java.util.ArrayList;
import java.util.List;

public class DotManager {

  private final List<Collectible> collectibles = new ArrayList<>();
  private int score = -10; // TODO: don't spawn any collectible on player spawn

  public void addDot(int x, int y) {
    collectibles.add(new Dot(x, y));
  }

  public void update(Coord playerCoord, boolean isPlayerCentered) {
    if (!isPlayerCentered)
      return;

    for (Collectible collectible : collectibles) {
      if (!collectible.isCollected()) {
        collectible.update(playerCoord);
        if (collectible.isCollected()) {
          score += collectible.getValue();
        }
      }
    }
  }

  public void render(SpriteBatch batch) {
    for (Collectible collectible : collectibles) {
      collectible.render(batch);
    }
  }

  public boolean allCollected() {
    for (Collectible collectible : collectibles) {
      if (!collectible.isCollected())
        return false;
    }
    return true;
  }

  public int getScore() {
    return score;
  }

  public void dispose() {
    Dot.dispose();
  }
}