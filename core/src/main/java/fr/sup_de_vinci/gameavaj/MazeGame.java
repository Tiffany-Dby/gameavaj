package fr.sup_de_vinci.gameavaj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeGame extends ApplicationAdapter {
    SpriteBatch spriteBatch;
    Texture whiteCellTexture;

    int[][] maze = {
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2},
        {2, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2},
        {2, 1, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2},
        {2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2},
        {2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    };

    int cellSize = 40;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        whiteCellTexture = new Texture(Gdx.files.internal("path.png"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                int x = col * cellSize;
                int y = Gdx.graphics.getHeight() - (row + 1) * cellSize;

                if (maze[row][col] == 1) {
                    spriteBatch.draw(whiteCellTexture, x, y, cellSize, cellSize);
                }
                // Vous pouvez ajouter d'autres textures pour les cases noires ou vertes ici
            }
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        whiteCellTexture.dispose();
    }
}
