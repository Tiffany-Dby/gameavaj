package fr.sup_de_vinci.gameavaj.player;

import java.util.EnumMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Coord;
import fr.sup_de_vinci.gameavaj.enums.Direction;
import fr.sup_de_vinci.gameavaj.map.MapManager;

public class StateIdle extends State {

  private Coord pos;

  public StateIdle(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction, Coord pos) {
    super(animations, direction);
    this.pos = pos;
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

  @Override
  public State update(float deltaTime, Vector2 pos) {
    Direction input = getPressedDirection();

    if (input != Direction.NONE) {
      Coord next = input.getNext(this.pos);

      if (MapManager.isWalkable(next.getX(), next.getY())) {
        return new StateMoving(this.getAnims(), input, next);
      }
    }
    return this;
  }

  @Override
  public void render(SpriteBatch batch, Vector2 renderPos) {
    this.render(batch, renderPos, 0, false);
  }

}
