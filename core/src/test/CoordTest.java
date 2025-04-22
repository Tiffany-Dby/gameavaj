import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.utils.Coord;

public class CoordTest {

  @Test
  public void initWithInts() {
    Coord coord = new Coord(5, 3);

    assertEquals(5, coord.getX());
    assertEquals(3, coord.getY());
  }

  @Test
  public void initWithVectorAndTileSize() {
    Vector2 pos = new Vector2(128, 64);
    Coord coord = new Coord(pos, 64);

    assertEquals(2, coord.getX());
    assertEquals(1, coord.getY());
  }

  @Test
  public void initWithVectorAndBadTileSize() {
    assertThrows(IllegalArgumentException.class, () -> new Coord(new Vector2(1, 2), 0));
  }

  @Test
  public void cloneCoord() {
    Coord base = new Coord(4, 7);
    Coord cloned = base.clone();

    assertEquals(base.getX(), cloned.getX());
    assertEquals(base.getY(), cloned.getY());
    assertNotSame(base, cloned);
  }

  @Test
  public void moveCoord() {
    Coord coord = new Coord(3, 2);
    coord.move(1, -1);

    assertEquals(4, coord.getX());
    assertEquals(1, coord.getY());
  }
}
