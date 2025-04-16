package fr.sup_de_vinci.gameavaj.characters;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Direction;

public class StateDead extends State {

  private final float stateTime;

  public StateDead(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction, float stateTime) {
    super(animations, direction);
    this.stateTime = stateTime;
  }

  @Override
  public State update(CharacterController controller, float deltaTime, Vector2 pos) {
    return this;
  }

  @Override
  public void render(SpriteBatch batch, Vector2 renderPos) {
    this.render(batch, renderPos, stateTime, false);
  }

}
