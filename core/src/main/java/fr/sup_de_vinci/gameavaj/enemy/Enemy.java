package fr.sup_de_vinci.gameavaj.enemy;

import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class Enemy {

    private float x, y;
    private Direction direction;
    private float speed;

    public Enemy(float x, float y, Direction direction, float speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    public int getCellX() {
        return (int) (x / MapManager.TILE_SIZE);
    }

    public int getCellY() {
        return (int) (y / MapManager.TILE_SIZE);
    }

    public void snapToGrid() {
        this.x = Math.round(x / MapManager.TILE_SIZE) * MapManager.TILE_SIZE;
        this.y = Math.round(y / MapManager.TILE_SIZE) * MapManager.TILE_SIZE;
    }

    public boolean isCenteredOnTile() {
        float tolerance = 1f;
        float modX = x % MapManager.TILE_SIZE;
        float modY = y % MapManager.TILE_SIZE;
        return Math.abs(modX) < tolerance && Math.abs(modY) < tolerance;
    }

    public void move(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
