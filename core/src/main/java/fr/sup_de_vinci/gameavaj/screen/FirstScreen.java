package fr.sup_de_vinci.gameavaj.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import fr.sup_de_vinci.gameavaj.enemy.Enemy;
import fr.sup_de_vinci.gameavaj.enemy.EnemyController;
import fr.sup_de_vinci.gameavaj.enemy.EnemyFactory;
import fr.sup_de_vinci.gameavaj.enemy.EnemyRenderer;
import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.map.Dot;
import fr.sup_de_vinci.gameavaj.player.Player;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class FirstScreen implements Screen {
    private static final int NUM_ENEMIES = 6;
    private static final int CORRIDOR_SIZE = 40;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private EnemyController[] enemyControllers;
    private EnemyRenderer[] enemyRenderers;

    private Player player;

    private List<Dot> dots;
    private BitmapFont font;
    private int score = 0;

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

        // Init enemies
        enemyControllers = new EnemyController[NUM_ENEMIES];
        enemyRenderers = new EnemyRenderer[NUM_ENEMIES];
        for (int i = 0; i < NUM_ENEMIES; i++) {
            Enemy enemy = EnemyFactory.spawnRandomEnemy();
            enemyControllers[i] = new EnemyController(enemy);
            enemyRenderers[i] = new EnemyRenderer(enemy);
        }

        // Init player
        player = new Player(10, 5);

        // Init dots
        dots = new ArrayList<>();
        for (int y = 0; y < MapManager.MAP.length; y++) {
            for (int x = 0; x < MapManager.MAP[0].length; x++) {
                if (MapManager.MAP[y][x] == 0) {
                    dots.add(new Dot(x, y));
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        clearScreen();

        for (EnemyController controller : enemyControllers) {
            controller.update(delta);
        }

        player.update(delta);
        checkDotCollision();

        if (!player.isDead()) {
            for (EnemyController controller : enemyControllers) {
                Enemy enemy = controller.getEnemy();
                if (enemy.getCellX() == player.getTileX() && enemy.getCellY() == player.getTileY()) {
                    player.die();
                    break;
                }
            }
        }

        drawMap();
        drawGameObjects(delta);
    }

    private void checkDotCollision() {
        float px = player.getX() + MapManager.TILE_SIZE / 2f;
        float py = player.getY() + MapManager.TILE_SIZE / 2f;
        float collisionDistance = MapManager.TILE_SIZE / 2f;

        Iterator<Dot> iterator = dots.iterator();
        while (iterator.hasNext()) {
            Dot dot = iterator.next();
            float dx = dot.getCenterX() - px;
            float dy = dot.getCenterY() - py;

            if (dx * dx + dy * dy < collisionDistance * collisionDistance) {
                iterator.remove();
                score += 10;
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.7f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawMap() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        int offset = (MapManager.TILE_SIZE - CORRIDOR_SIZE) / 2;
    
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    
        // 1. D'abord les murs et couloirs
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
    
        // 2. Ensuite les pastilles (dots) pour ne pas qu'elles soient recouvertes
        shapeRenderer.setColor(Color.GREEN);
        float radius = 6f;
        for (Dot dot : dots) {
            shapeRenderer.circle(dot.getCenterX(), dot.getCenterY(), radius);
        }
    
        shapeRenderer.end();
    
        // Optionnel : grille
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        for (int y = 0; y < MapManager.MAP.length; y++) {
            for (int x = 0; x < MapManager.MAP[0].length; x++) {
                shapeRenderer.rect(
                        x * MapManager.TILE_SIZE,
                        y * MapManager.TILE_SIZE,
                        MapManager.TILE_SIZE,
                        MapManager.TILE_SIZE);
            }
        }
        shapeRenderer.end();
    }
    
    private void drawGameObjects(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (EnemyRenderer renderer : enemyRenderers) {
            renderer.draw(batch, delta);
        }

        font.draw(batch, "Score: " + score,
                camera.position.x - 150,
                camera.position.y + (MapManager.MAP.length * MapManager.TILE_SIZE / 2f) + 30);

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

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        for (EnemyRenderer renderer : enemyRenderers) {
            renderer.dispose();
        }

        if (player != null) player.dispose();
        if (batch != null) batch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (font != null) font.dispose();
    }
}
