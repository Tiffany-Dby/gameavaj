package fr.sup_de_vinci.gameavaj.enemy;

import java.util.Random;

import fr.sup_de_vinci.gameavaj.characters.Character;
import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.utils.Coord;

/**
 * Factory class responsible for spawning enemy characters at random walkable
 * locations on the map
 */
public class EnemyFactory {

  private static final String SPRITE_PATH = "enemy-walk.png";

  /**
   * Spawns a new enemy {@link Character} at a random walkable position on the
   * map
   *
   * @return a new enemy character instance with a random starting position
   */
  public static Character spawnRandomEnemy() {
    Random random = new Random();

    int mapHeight = MapManager.MAP.length;
    int mapWidth = MapManager.MAP[0].length;

    int cellX, cellY;
    do {
      cellX = random.nextInt(mapWidth);
      cellY = random.nextInt(mapHeight);
    } while (!MapManager.isWalkable(cellX, cellY));

    return new Character(new EnemyController(), new Coord(cellX, cellY), SPRITE_PATH);
  }
}