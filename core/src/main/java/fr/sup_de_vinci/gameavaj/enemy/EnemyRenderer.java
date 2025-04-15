// package fr.sup_de_vinci.gameavaj.enemy;

// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.Animation;
// import com.badlogic.gdx.graphics.g2d.TextureRegion;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// public class EnemyRenderer {

// private Enemy enemy;
// private float stateTime = 0f;

// private Texture enemySheet;
// private Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;

// public EnemyRenderer(Enemy enemy) {
// this.enemy = enemy;

// enemySheet = new Texture("enemy-walk.png");
// TextureRegion[][] tmpFrames = TextureRegion.split(enemySheet, 64, 64);
// walkDown = new Animation<>(0.1f, tmpFrames[0]);
// walkUp = new Animation<>(0.1f, tmpFrames[1]);
// walkLeft = new Animation<>(0.1f, tmpFrames[2]);
// walkRight = new Animation<>(0.1f, tmpFrames[3]);
// }

// public void draw(SpriteBatch batch, float deltaTime) {
// stateTime += deltaTime;

// Animation<TextureRegion> currentAnim = walkDown;

// switch (enemy.getDirection()) {
// case UP:
// currentAnim = walkUp;
// break;
// case DOWN:
// currentAnim = walkDown;
// break;
// case LEFT:
// currentAnim = walkLeft;
// break;
// case RIGHT:
// currentAnim = walkRight;
// break;
// case NONE:
// break;
// }

// TextureRegion currentFrame = currentAnim.getKeyFrame(stateTime, true);
// batch.draw(currentFrame, enemy.getX(), enemy.getY());
// }

// public void dispose() {
// enemySheet.dispose();
// }
// }
