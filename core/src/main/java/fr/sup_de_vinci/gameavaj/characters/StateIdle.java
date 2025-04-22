package fr.sup_de_vinci.gameavaj.characters;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.utils.Direction;

public class StateIdle extends State {

  public StateIdle(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction) {
    super(animations, direction);
  }

  @Override
  public State update(CharacterController controller, float deltaTime, Vector2 pos) {
    return controller.update(this, pos);
  }

  @Override
  public void render(SpriteBatch batch, Vector2 renderPos) {
    this.render(batch, renderPos, 0, false);
  }

}
