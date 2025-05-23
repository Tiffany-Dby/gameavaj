package fr.sup_de_vinci.gameavaj.characters;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.utils.Direction;

public class StateDying extends State {

  private float stateTime = 0f;

  public StateDying(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction) {
    super(animations, direction);
  }

  @Override
  public State update(CharacterController controller, float deltaTime, Vector2 pos) {
    this.stateTime += deltaTime;
    return this.getCurrentAnim().isAnimationFinished(stateTime)
        ? new StateDead(getAnims(), getDirection(), stateTime)
        : this;
  }

  @Override
  public void render(SpriteBatch batch, Vector2 renderPos) {
    render(batch, renderPos, this.stateTime, false);
  }
}
