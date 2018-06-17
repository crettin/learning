package com.obstacleavoid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.assets.AssetPaths;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.entity.Background;
import com.obstacleavoid.entity.Obstacle;
import com.obstacleavoid.entity.Player;
import com.obstacleavoid.utils.GdxUtils;
import com.obstacleavoid.utils.ViewportUtils;
import com.obstacleavoid.utils.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    //== Attributes ==
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    // Hud attributes;
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private DebugCameraController debugCameraController;
    private final GameController controller;

    private Texture playerTexture;
    private Texture obstacleTexture;
    private Texture backgroundTexture;

    //== Constructors ==
    public GameRenderer(GameController controller) {
        this.controller = controller;
        init();
    }

    //== Init ==
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        //Init hud attributes
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(AssetPaths.UI_FONT));

        //Create Debug Camera Controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTRE_X, GameConfig.WORLD_CENTRE_Y);

        playerTexture = new Texture(Gdx.files.internal("gameplay/player.png"));
        obstacleTexture = new Texture(Gdx.files.internal("gameplay/obstacle.png"));
        backgroundTexture = new Texture(Gdx.files.internal("gameplay/background.png"));
    }

    //== Public Methods ==
    public void render(float delta) {
        //Not wrapped by alive since this allows debug while dead
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // Check controls for android - hacky method should be handled elsewhere
        if(Gdx.input.isTouched() && !controller.isGameOver()){
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

            System.out.println("screen touch " + screenTouch);
            System.out.println("world touch " + worldTouch);

            Player player = controller.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x,0,GameConfig.WORLD_WIDTH - player.getWidth());
            player.setX(worldTouch.x);
        }


        GdxUtils.clearScreen();

        //Render Game layer
        renderGamePlay();

        //Render ui/hud
        renderUi();

        //Render Debug screen
        renderDebug();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        playerTexture.dispose();
        obstacleTexture.dispose();
        backgroundTexture.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    //== Private Methods ==
    private void renderGamePlay(){
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //Draw Background
        Background background = controller.getBackground();
        batch.draw(backgroundTexture,
                background.getX(), background.getY(),
                background.getWidth(),background.getHeight());

        //Draw player
        Player player = controller.getPlayer();
        batch.draw(playerTexture,
                player.getX(),player.getY(),
                player.getWidth(), player.getHeight());

        //Draw obstacles
        for(Obstacle obstacle : controller.getObstacles()){
            batch.draw(obstacleTexture,
                    obstacle.getX(),obstacle.getY(),
                    obstacle.getWidth(), obstacle.getHeight());
        }

        batch.end();
    }

    private void renderUi() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + controller.getLives();
        layout.setText(font, livesText);

        font.draw(batch, livesText,
                20, GameConfig.HUD_HEIGHT - layout.height);

        String scoreText = "SCORE: " + controller.getDisplayScore();
        layout.setText(font, scoreText);

        font.draw(batch, scoreText,
                GameConfig.HUD_WIDTH - layout.width - 20, GameConfig.HUD_HEIGHT - layout.height);

        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        shapeRenderer.end();

        ViewportUtils.drawGrid(viewport, shapeRenderer);
    }

    private void drawDebug() {
        Player player = controller.getPlayer();
        player.drawDebug(shapeRenderer);

        Array<Obstacle> obstacles = controller.getObstacles();

        for (Obstacle obstacle : obstacles) {
            obstacle.drawDebug(shapeRenderer);
        }
    }
}
