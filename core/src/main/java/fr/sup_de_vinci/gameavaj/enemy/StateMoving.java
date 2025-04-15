package fr.sup_de_vinci.gameavaj.enemy;

import java.util.EnumMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class StateMoving extends State {

  private static final float SPEED = 100f;
  private static final float SNAP_THRESHOLD = 1f;

  private Coord target;
  private float stateTime = 0f;

  public StateMoving(EnumMap<Direction, Animation<TextureRegion>> anims, Direction direction, Coord target) {
    super(anims, direction);
    this.target = target;
  }

  private Direction findNextDirection(Coord current, Direction preferred) {
    List<Direction> options = List.of(
        preferred, preferred.turnLeft(), preferred.turnRight(), preferred.getOpposite());

    for (Direction dir : options) {
      Coord next = dir.getNext(current);
      if (MapManager.isWalkable(next.getX(), next.getY())) {
        return dir;
      }
    }

    return Direction.NONE;
  }

  @Override
  public State update(float deltaTime, Vector2 pos) {
    stateTime += deltaTime;

    float targetX = target.getX() * MapManager.TILE_SIZE;
    float targetY = target.getY() * MapManager.TILE_SIZE;

    float dx = targetX - pos.x;
    float dy = targetY - pos.y;
    float distance = SPEED * deltaTime;

    if (Math.abs(dx) > 0)
      pos.x += Math.signum(dx) * Math.min(Math.abs(dx), distance);
    if (Math.abs(dy) > 0)
      pos.y += Math.signum(dy) * Math.min(Math.abs(dy), distance);

    if (Math.abs(dx) <= SNAP_THRESHOLD && Math.abs(dy) <= SNAP_THRESHOLD) {
      return new StateIdle(getAnims(), getDirection(), this.target);
    }

    if (Math.abs(dx) <= SNAP_THRESHOLD && Math.abs(dy) <= SNAP_THRESHOLD) {
      Coord current = new Coord(target.getX(), target.getY());
      Direction nextDir = findNextDirection(current, getDirection());
      Coord nextTarget = nextDir.getNext(current);

      return new StateMoving(getAnims(), nextDir, nextTarget);
    }

    return this;
  }

  @Override
  public void render(SpriteBatch batch, Vector2 renderPos) {
    render(batch, renderPos, stateTime, true);
  }
}
