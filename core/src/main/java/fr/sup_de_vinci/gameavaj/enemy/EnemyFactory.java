package fr.sup_de_vinci.gameavaj.enemy;

import java.util.Random;

import fr.sup_de_vinci.gameavaj.map.MapManager;

public class EnemyFactory {

  public static Enemy spawnRandomEnemy() {
    Random random = new Random();

    int mapHeight = MapManager.MAP.length;
    int mapWidth = MapManager.MAP[0].length;

    int cellX, cellY;
    do {
      cellX = random.nextInt(mapWidth);
      cellY = random.nextInt(mapHeight);
    } while (!MapManager.isWalkable(cellX, cellY));

    return new Enemy(cellX, cellY);
  }
}