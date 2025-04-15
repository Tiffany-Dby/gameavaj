package fr.sup_de_vinci.gameavaj.enemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.utils.Position;

public class StateIdle extends State {

  private Coord coord;

  public StateIdle(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction, Coord coord) {
    super(animations, direction);
    this.coord = coord;
  }

  @Override
  public State update(float deltaTime, Vector2 pos) {
    if (!Position.isCenteredOnTile(pos))
      return this;

    List<Direction> directions = new ArrayList<>(
        List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
    Collections.shuffle(directions);

    List<Direction> validDirections = new ArrayList<>();

    for (Direction dir : directions) {
      Coord next = dir.getNext(this.coord);
      if (MapManager.isWalkable(next.getX(), next.getY())) {
        validDirections.add(dir);
      }
    }

    Direction opposite = getDirection().getOpposite();

    if (validDirections.size() <= 2 && validDirections.contains(getDirection())) {
      Coord next = getDirection().getNext(this.coord);
      return new StateMoving(getAnims(), getDirection(), next);
    } else {
      validDirections.remove(opposite);

      if (!validDirections.isEmpty()) {
        Direction chosen = validDirections.get(MathUtils.random(validDirections.size() - 1));
        Coord next = chosen.getNext(this.coord);
        return new StateMoving(getAnims(), chosen, next);
      }
    }

    return this;
  }

  @Override
  public void render(SpriteBatch batch, Vector2 renderPos) {
    render(batch, renderPos, 0f, false);
  }
}
