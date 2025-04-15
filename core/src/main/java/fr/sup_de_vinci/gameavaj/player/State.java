package fr.sup_de_vinci.gameavaj.player;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.sup_de_vinci.gameavaj.enums.Direction;

public abstract class State {
  private EnumMap<Direction, Animation<TextureRegion>> animations;
  private Direction direction;

  public State(EnumMap<Direction, Animation<TextureRegion>> animations, Direction direction) {
    this.animations = animations;
    this.direction = direction;
  }

  protected EnumMap<Direction, Animation<TextureRegion>> getAnims() {
    return this.animations;
  }

  protected Animation<TextureRegion> getCurrentAnim() {
    return this.animations.get(this.direction);
  }

  protected Direction getDirection() {
    return this.direction;
  }

  public abstract State update(float deltaTime, Vector2 pos);

  public abstract void render(SpriteBatch batch, Vector2 renderPos);

  protected void render(SpriteBatch batch, Vector2 renderPos, float stateTime, boolean looped) {
    batch.draw(this.getCurrentAnim().getKeyFrame(stateTime, looped), renderPos.x, renderPos.y);
  }
}
