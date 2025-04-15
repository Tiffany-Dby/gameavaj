package fr.sup_de_vinci.gameavaj.enemy;

import fr.sup_de_vinci.gameavaj.enums.Direction;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class State {
  private final EnumMap<Direction, Animation<TextureRegion>> animations;
  private final Direction direction;

  public State(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction) {
    this.animations = animations;
    this.direction = direction;
  }

  protected EnumMap<Direction, Animation<TextureRegion>> getAnims() {
    return this.animations;
  }

  protected Animation<TextureRegion> getCurrentAnim() {
    return animations.get(direction);
  }

  protected Direction getDirection() {
    return direction;
  }

  public abstract State update(float deltaTime, Vector2 renderPos);

  public abstract void render(SpriteBatch batch, Vector2 renderPos);

  protected void render(SpriteBatch batch, Vector2 renderPos, float stateTime, boolean looped) {
    batch.draw(getCurrentAnim().getKeyFrame(stateTime, looped), renderPos.x, renderPos.y);
  }
}
