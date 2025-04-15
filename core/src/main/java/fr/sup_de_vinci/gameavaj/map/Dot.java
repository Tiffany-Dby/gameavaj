package fr.sup_de_vinci.gameavaj.map;

public class Dot {
    private final int x, y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getCenterX() {
        return x * MapManager.TILE_SIZE + MapManager.TILE_SIZE / 2f;
    }

    public float getCenterY() {
        return y * MapManager.TILE_SIZE + MapManager.TILE_SIZE / 2f;
    }

    public int getGridX() {
        return x;
    }

    public int getGridY() {
        return y;
    }
}
