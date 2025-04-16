package fr.sup_de_vinci.gameavaj.enemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.characters.CharacterController;
import fr.sup_de_vinci.gameavaj.characters.State;
import fr.sup_de_vinci.gameavaj.characters.StateMoving;
import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.utils.Position;

public class EnemyController implements CharacterController {

  @Override
  public State update(State current, Vector2 pos) {

    if (!Position.isCenteredOnTile(pos))
      return current;

    Coord coord = new Coord(pos, MapManager.TILE_SIZE);
    List<Direction> directions = Arrays.asList(Direction.ALL);
    Collections.shuffle(directions);

    List<Direction> validDirections = new ArrayList<>();

    for (Direction dir : directions) {
      Coord next = dir.getNext(coord);
      if (MapManager.isWalkable(next.getX(), next.getY())) {
        validDirections.add(dir);
      }
    }

    Direction direction = current.getDirection();

    if (validDirections.size() <= 2 && validDirections.contains(direction)) {
      Coord next = direction.getNext(coord);
      return new StateMoving(current.getAnims(), direction, next);
    } else {
      validDirections.remove(direction.getOpposite());

      if (!validDirections.isEmpty()) {
        Direction chosen = validDirections.get(MathUtils.random(validDirections.size() - 1));
        Coord next = chosen.getNext(coord);
        return new StateMoving(current.getAnims(), chosen, next);
      }
    }

    return current;
  }
}
