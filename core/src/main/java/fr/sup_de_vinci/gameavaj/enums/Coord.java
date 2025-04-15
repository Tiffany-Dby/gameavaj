package fr.sup_de_vinci.gameavaj.enums;

public class Coord {
  private int x, y;

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

  Coord move(int dx, int dy) {
    this.x += dx;
    this.y += dy;
    return this;
  }

}
