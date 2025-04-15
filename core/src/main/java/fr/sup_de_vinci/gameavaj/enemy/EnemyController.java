// package fr.sup_de_vinci.gameavaj.enemy;

// import fr.sup_de_vinci.gameavaj.enums.Direction;
// import fr.sup_de_vinci.gameavaj.map.MapManager;

// import java.util.*;

// public class EnemyController {
// private final Enemy enemy;
// private final float speed = 100f;

// public EnemyController(Enemy enemy) {
// this.enemy = enemy;
// randomDirection();
// }

// public Enemy getEnemy() {
// return enemy;
// }

// public void update(float deltaTime) {
// if (enemy.isCenteredOnTile()) {
// chooseNewDirection();
// }

// if (!willHitWall(enemy.getDirection())) {
// float dx = getDX(enemy.getDirection()) * speed * deltaTime;
// float dy = getDY(enemy.getDirection()) * speed * deltaTime;
// enemy.move(dx, dy);
// } else {
// enemy.snapToGrid();
// enemy.setDirection(enemy.getDirection().getOpposite());
// }
// }

// private void chooseNewDirection() {
// List<Direction> options = new ArrayList<>();

// for (Direction dir : Direction.values()) {
// if (dir == enemy.getDirection().getOpposite())
// continue;

// int nx = enemy.getCellX() + (int) getDX(dir);
// int ny = enemy.getCellY() + (int) getDY(dir);

// if (MapManager.isWalkable(nx, ny)) {
// options.add(dir);
// }
// }

// if (!options.isEmpty()) {
// Collections.shuffle(options);
// enemy.setDirection(options.get(0));
// } else {
// enemy.setDirection(enemy.getDirection().getOpposite());
// }
// }

// private boolean willHitWall(Direction dir) {
// int nextX = (int) ((enemy.getX() + getDX(dir) * MapManager.TILE_SIZE) /
// MapManager.TILE_SIZE);
// int nextY = (int) ((enemy.getY() + getDY(dir) * MapManager.TILE_SIZE) /
// MapManager.TILE_SIZE);
// return !MapManager.isWalkable(nextX, nextY);
// }

// private float getDX(Direction dir) {
// return dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0;
// }

// private float getDY(Direction dir) {
// return dir == Direction.UP ? 1 : dir == Direction.DOWN ? -1 : 0;
// }

// private void randomDirection() {
// List<Direction> dirs = Arrays.asList(Direction.values());
// Collections.shuffle(dirs);
// enemy.setDirection(dirs.get(0));
// }

// public class Dot {
// public final int x, y;

// public Dot(int x, int y) {
// this.x = x;
// this.y = y;
// }

// public float getCenterX() {
// return x * MapManager.TILE_SIZE + MapManager.TILE_SIZE / 2f;
// }

// public float getCenterY() {
// return y * MapManager.TILE_SIZE + MapManager.TILE_SIZE / 2f;
// }
// }
// }
