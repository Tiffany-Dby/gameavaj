package fr.sup_de_vinci.gameavaj.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.characters.CharacterController;
import fr.sup_de_vinci.gameavaj.characters.State;
import fr.sup_de_vinci.gameavaj.characters.StateMoving;
import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class PlayerController implements CharacterController {

  @Override
  public State update(State current, Vector2 pos) {
    Direction input = getPressedDirection();
    Coord tilePos = new Coord(pos, MapManager.TILE_SIZE);

    if (input != Direction.NONE) {
      Coord next = input.getNext(tilePos);

      if (MapManager.isWalkable(next.getX(), next.getY())) {
        return new StateMoving(current.getAnims(), input, next);
      }
    }
    return current;
  }

  private Direction getPressedDirection() {
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
      return Direction.LEFT;
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
      return Direction.RIGHT;
    if (Gdx.input.isKeyPressed(Input.Keys.UP))
      return Direction.UP;
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
      return Direction.DOWN;
    return Direction.NONE;
  }

}
