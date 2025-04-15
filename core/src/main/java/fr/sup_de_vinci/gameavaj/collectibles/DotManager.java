package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class DotManager {

  private final List<Collectible> collectibles = new ArrayList<>();
  private int score = -10; // TODO: don't spawn any collectible on player spawn

  public void addDot(int x, int y) {
    collectibles.add(new Dot(x, y));
  }

  public void update(Vector2 playerPos) {
    for (Collectible collectible : collectibles) {
      if (!collectible.isCollected()) {
        collectible.update(playerPos);
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

/*
 * public class DotManager {
 * 
 * private final List<Dot> dots = new ArrayList<>();
 * private int score = 0;
 * 
 * public int getScore() {
 * return score;
 * }
 * 
 * public void addDot(int tileX, int tileY) {
 * dots.add(new Dot(tileX, tileY));
 * }
 * 
 * public void update(Vector2 playerRenderPos) {
 * for (Dot dot : dots) {
 * if (dot.shouldBeCollected(playerRenderPos)) {
 * dot.collect();
 * score += 10;
 * }
 * }
 * }
 * 
 * public void render(SpriteBatch batch) {
 * for (Dot dot : dots) {
 * dot.render(batch);
 * }
 * }
 * 
 * public void dispose() {
 * Dot.dispose();
 * }
 * 
 * public boolean allCollected() {
 * for (Dot dot : dots) {
 * if (!dot.isCollected())
 * return false;
 * }
 * return true;
 * }
 * }
 */