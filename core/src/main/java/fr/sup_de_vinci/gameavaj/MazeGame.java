package fr.sup_de_vinci.gameavaj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class MazeGame extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;

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
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                int x = col * cellSize;
                int y = Gdx.graphics.getHeight() - (row + 1) * cellSize;

                if (maze[row][col] == 2) {
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.rect(x, y, cellSize, cellSize);
                } else if (maze[row][col] == 1) {
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(x, y, cellSize, cellSize);

                    shapeRenderer.setColor(Color.GREEN);
                    float centerX = x + cellSize / 2f;
                    float centerY = y + cellSize / 2f;
                    float radius = cellSize / 12f;
                    shapeRenderer.circle(centerX, centerY, radius);
                }
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}