package fr.sup_de_vinci.gameavaj.enemy;

import java.util.Random;

import fr.sup_de_vinci.gameavaj.characters.Character;
import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class EnemyFactory {

  private static final String SPRITE_PATH = "enemy-walk.png";

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