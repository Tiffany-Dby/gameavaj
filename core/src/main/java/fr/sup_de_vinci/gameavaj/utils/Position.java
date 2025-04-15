package fr.sup_de_vinci.gameavaj.utils;

import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class Position {

  public static Vector2 getTileCenter(Coord tile) {
    float halfTile = MapManager.TILE_SIZE / 2f;

    return new Vector2(tile.getX(), tile.getY())
        .scl(MapManager.TILE_SIZE)
        .add(halfTile, halfTile);
  }

  public static boolean isCenteredOnTile(Vector2 renderPos) {
    float half = MapManager.TILE_SIZE / 2f;
    float xCenter = ((int) (renderPos.x / MapManager.TILE_SIZE)) * MapManager.TILE_SIZE + half;
    float yCenter = ((int) (renderPos.y / MapManager.TILE_SIZE)) * MapManager.TILE_SIZE + half;
    return renderPos.dst2(xCenter - half, yCenter - half) < 0.1f;
  }
}
