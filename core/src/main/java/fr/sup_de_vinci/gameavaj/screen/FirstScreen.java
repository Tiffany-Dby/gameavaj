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
import fr.sup_de_vinci.gameavaj.characters.Character;
import fr.sup_de_vinci.gameavaj.enemy.EnemyFactory;
import fr.sup_de_vinci.gameavaj.map.MapManager;
import fr.sup_de_vinci.gameavaj.player.PlayerController;
import fr.sup_de_vinci.gameavaj.utils.Coord;

public class FirstScreen implements Screen {
    private static final int NUM_ENEMIES = 6;
    private static final int CORRIDOR_SIZE = 40;
    private static final String WALK_SPRITE_PATH = "player-walk.png";
    private static final String DEATH_SPRITE_PATH = "player-death.png";

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private List<Character> enemies;

    private Character player;

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

        dotManager = new DotManager();
        for (int y = 0; y < MapManager.MAP.length; y++) {
            for (int x = 0; x < MapManager.MAP[0].length; x++) {
                if (MapManager.MAP[y][x] == 0) {
                    dotManager.addDot(x, y);
                }
            }
        }

        enemies = new ArrayList<>();
        for (int i = 0; i < NUM_ENEMIES; i++) {
            enemies.add(EnemyFactory.spawnRandomEnemy());
        }
        player = new Character(
                new PlayerController(),
                new Coord(10, 5),
                WALK_SPRITE_PATH,
                DEATH_SPRITE_PATH);
    }

    @Override
    public void render(float delta) {
        clearScreen();

        dotManager.update(player.getTileCoord(), player.isCenteredOnTile());

        for (Character enemy : enemies) {
            enemy.update(delta);
        }

        player.update(delta);

        if (!player.isDead()) {
            for (Character enemy : enemies) {
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

        for (Character enemy : enemies) {
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
        for (Character enemy : enemies) {
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
