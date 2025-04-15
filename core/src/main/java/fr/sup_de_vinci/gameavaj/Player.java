package fr.sup_de_vinci.gameavaj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
  private Texture walkSheet;
  private Animation<TextureRegion> walkDownAnimation;
  private Animation<TextureRegion> walkUpAnimation;
  private Animation<TextureRegion> walkLeftAnimation;
  private Animation<TextureRegion> walkRightAnimation;
  private Animation<TextureRegion> currentAnimation;

  private final static String PLAYER_WALK_SPRITE = "player-walk.png";
  private final static int PLAYER_TILE_WIDTH = 64;
  private final static int PLAYER_TILE_HEIGHT = 64;

  private float x, y;
  private float stateTime;
  private boolean moving;

  public Player(float x, float y) {
    this.x = x;
    this.y = y;
    walkSheet = new Texture(PLAYER_WALK_SPRITE);

    TextureRegion[][] tmpFrames = TextureRegion.split(walkSheet, PLAYER_TILE_WIDTH, PLAYER_TILE_HEIGHT);

    walkDownAnimation = new Animation<TextureRegion>(0.1f, tmpFrames[0]);
    walkUpAnimation = new Animation<TextureRegion>(0.1f, tmpFrames[1]);
    walkLeftAnimation = new Animation<TextureRegion>(0.1f, tmpFrames[2]);
    walkRightAnimation = new Animation<TextureRegion>(0.1f, tmpFrames[3]);

    currentAnimation = walkDownAnimation;
    stateTime = 0f;
  }

  public void update(float deltaTime) {
    moving = false;

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      x -= 200 * deltaTime;
      moving = true;
      currentAnimation = walkLeftAnimation;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      x += 200 * deltaTime;
      moving = true;
      currentAnimation = walkRightAnimation;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      y += 200 * deltaTime;
      moving = true;
      currentAnimation = walkUpAnimation;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      y -= 200 * deltaTime;
      moving = true;
      currentAnimation = walkDownAnimation;
    }

    if (moving) {
      stateTime += deltaTime;
    }
  }

  public void render(SpriteBatch batch) {
    TextureRegion currentFrame;
    if (moving) {
      currentFrame = currentAnimation.getKeyFrame(stateTime, true);
    } else {
      currentFrame = currentAnimation.getKeyFrame(0);
    }

    batch.draw(currentFrame, x, y);
  }

  public void dispose() {
    walkSheet.dispose();
  }
}
