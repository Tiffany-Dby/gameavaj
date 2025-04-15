package fr.sup_de_vinci.gameavaj.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import fr.sup_de_vinci.gameavaj.collectibles.DotManager;
import fr.sup_de_vinci.gameavaj.enemy.Enemy;
// import fr.sup_de_vinci.gameavaj.enemy.EnemyController;
import fr.sup_de_vinci.gameavaj.enemy.EnemyFactory;
// import fr.sup_de_vinci.gameavaj.enemy.EnemyRenderer;
import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.player.Player;

public class FirstScreen implements Screen {
    private static final int NUM_ENEMIES = 6;
    private static final int CORRIDOR_SIZE = 40;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    // private EnemyController[] enemyControllers;
    // private EnemyRenderer[] enemyRenderers;
    private List<Enemy> enemies;

    private Player player;

    private DotManager dotManager;

    private BitmapFont font;

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.zoom = 2f;
        viewport = new FitViewport(800, 600, camera);
        centerCameraOnMap();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2.5f);

        // Init dots
        dotManager = new DotManager();
        for (int y = 0; y < MapManager.MAP.length; y++) {
            for (int x = 0; x < MapManager.MAP[0].length; x++) {
                if (MapManager.MAP[y][x] == 0) {
                    dotManager.addDot(x, y);
                }
            }
        }

        // Init enemies
        // enemyControllers = new EnemyController[NUM_ENEMIES];
        // enemyRenderers = new EnemyRenderer[NUM_ENEMIES];
        // for (int i = 0; i < NUM_ENEMIES; i++) {
        // Enemy enemy = EnemyFactory.spawnRandomEnemy();
        // enemyControllers[i] = new EnemyController(enemy);
        // enemyRenderers[i] = new EnemyRenderer(enemy);
        // }
        enemies = new ArrayList<>();
        for (int i = 0; i < NUM_ENEMIES; i++) {
            enemies.add(EnemyFactory.spawnRandomEnemy());
        }

        // Init player
        player = new Player(10, 5);
    }

    @Override
    public void render(float delta) {
        clearScreen();

        dotManager.update(player.getTileCoord(), player.isCenteredOnTile());

        // for (EnemyController controller : enemyControllers) {
        // controller.update(delta);
        // }
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }

        player.update(delta);

        if (!player.isDead()) {
            // for (EnemyController controller : enemyControllers) {
            // Enemy enemy = controller.getEnemy();
            // if (enemy.getCellX() == player.getTileX() && enemy.getCellY() ==
            // player.getTileY()) {
            // player.die();
            // break;
            // }
            // }
            for (Enemy enemy : enemies) {
                if (enemy.getTileX() == player.getTileX() && enemy.getTileY() == player.getTileY()) {
                    player.die();
                }
            }
        }

        drawMap();
        drawGameObjects(delta);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.7f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawMap() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        int offset = (MapManager.TILE_SIZE - CORRIDOR_SIZE) / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int y = 0; y < MapManager.MAP.length; y++) {
            for (int x = 0; x < MapManager.MAP[0].length; x++) {
                boolean isWall = MapManager.MAP[y][x] == 1;
                shapeRenderer.setColor(isWall ? Color.BLACK : Color.WHITE);
                shapeRenderer.rect(
                        x * MapManager.TILE_SIZE + (isWall ? 0 : offset),
                        y * MapManager.TILE_SIZE + (isWall ? 0 : offset),
                        isWall ? MapManager.TILE_SIZE : CORRIDOR_SIZE,
                        isWall ? MapManager.TILE_SIZE : CORRIDOR_SIZE);
            }
        }
        shapeRenderer.end();
    }

    private void drawGameObjects(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        dotManager.render(batch);

        // for (EnemyRenderer renderer : enemyRenderers) {
        // renderer.draw(batch, delta);
        // }
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        font.draw(batch, "Score: " + dotManager.getScore(),
                camera.position.x - 150,
                camera.position.y + (MapManager.MAP.length * MapManager.TILE_SIZE / 2f) + 50);

        player.render(batch);

        batch.end();
    }

    private void centerCameraOnMap() {
        int mapWidth = MapManager.MAP[0].length * MapManager.TILE_SIZE;
        int mapHeight = MapManager.MAP.length * MapManager.TILE_SIZE;
        camera.position.set(mapWidth / 2f, mapHeight / 2f, 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        centerCameraOnMap();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // for (EnemyRenderer renderer : enemyRenderers) {
        // renderer.dispose();
        // }
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }

        if (player != null)
            player.dispose();
        if (batch != null)
            batch.dispose();
        if (shapeRenderer != null)
            shapeRenderer.dispose();
        if (font != null)
            font.dispose();

        dotManager.dispose();
    }
}
