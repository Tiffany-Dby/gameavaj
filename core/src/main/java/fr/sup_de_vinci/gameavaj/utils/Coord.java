package fr.sup_de_vinci.gameavaj.utils;

import com.badlogic.gdx.math.Vector2;

public class Coord {
  private int x, y;

  public Coord(Vector2 v, float tileSize) {
    if (tileSize <= 0) {
      throw new IllegalArgumentException("Tile size can not be smaller or equal to 0.");
    }

    this.x = (int) (v.x / tileSize);
    this.y = (int) (v.y / tileSize);
  }

  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coord clone() {
    return new Coord(this.x, this.y);
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public Coord move(int dx, int dy) {
    this.x += dx;
    this.y += dy;
    return this;
  }

}
