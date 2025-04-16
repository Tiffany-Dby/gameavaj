package fr.sup_de_vinci.gameavaj.enums;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CoordTest {

    @Test
    public void testGetters() {
        Coord coord = new Coord(2, 3);
        assertEquals(2, coord.getX());
        assertEquals(3, coord.getY());
    }

    @Test
    public void testClone() {
        Coord c1 = new Coord(4, 5);
        Coord c2 = c1.clone();
        assertNotSame(c1, c2);
        assertEquals(c1.getX(), c2.getX());
        assertEquals(c1.getY(), c2.getY());
    }

    @Test
    public void testMove() {
        Coord coord = new Coord(1, 1);
        coord.move(2, 3);
        assertEquals(3, coord.getX());
        assertEquals(4, coord.getY());
    }
}
