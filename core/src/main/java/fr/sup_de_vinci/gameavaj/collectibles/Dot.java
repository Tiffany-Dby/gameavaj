package fr.sup_de_vinci.gameavaj.collectibles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Dot extends AbstractCollectible {

    private static final Texture texture = new Texture("dot.png");
    private static final int SIZE = 40;

    public Dot(float x, float y) {
        super(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!collected) {
            Vector2 pos = getCenteredPixelPosition(SIZE);
            batch.draw(texture, pos.x, pos.y, SIZE, SIZE);
        }
    }

    @Override
    public int getValue() {
        return 10;
    }

    public static void dispose() {
        texture.dispose();
    }
}