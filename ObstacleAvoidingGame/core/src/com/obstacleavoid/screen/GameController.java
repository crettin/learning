package com.obstacleavoid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.obstacleavoid.config.DifficultyLevel;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.entity.Background;
import com.obstacleavoid.entity.Obstacle;
import com.obstacleavoid.entity.Player;

public class GameController {

    //== Constants ==   
    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    //== Attributes ==
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private float obstacleTimer;
    private float scoreTimer;
    private int lives = GameConfig.LIVES_START;
    //Actual score of the player
    private int score;
    //Displayed score of the player
    private int displayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

    private Background background;

    private Pool<Obstacle> obstaclePool;

    float startPlayerX = GameConfig.WORLD_CENTRE_X - GameConfig.PLAYER_SIZE / 2f;
    float startPlayerY = 1 - GameConfig.PLAYER_SIZE / 2f;

    // == Constructors ==
    public GameController() {
        init();
    }

    //== Init ==
    private void init() {
        // Create Player
        player = new Player();


        //Position Player
        player.setPosition(startPlayerX, startPlayerY);

        //Create Obstacle Pool
        obstaclePool = Pools.get(Obstacle.class, 40);

        //Create background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
    }

    //== Public Methods ==
    public void update(float delta) {
        if (isGameOver()) {
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if (isPlayerCollidingWithObstacle()) {
            lives--;

            if (isGameOver()) {
                log.debug("Game Over");
            } else {
                restart();
            }
        }
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getLives() {
        return lives;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Background getBackground() {
        return background;
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    //== Private Methods
    private void restart() {
        obstaclePool.freeAll(obstacles);
        obstacles.clear();
        player.setPosition(startPlayerX, startPlayerY);
    }


    private boolean isPlayerCollidingWithObstacle() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isNotHit() && obstacle.isPlayerColliding(player)) {
                return true;
            }
        }
        return false;
    }

    private void updatePlayer() {

        float xSpeed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xSpeed = GameConfig.PLAYER_X_SPEED;

        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -GameConfig.PLAYER_X_SPEED;
        }

        player.setX(player.getX() + xSpeed);


        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerRadius = player.getWidth() / 2f;
        float playerX = MathUtils.clamp(player.getX(), 0, GameConfig.WORLD_WIDTH - player.getWidth());
        player.setPosition(playerX, player.getY());
    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }
        createNewObstacle(delta);
        removePassedObstacles();
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            Obstacle obstacle = obstaclePool.obtain();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());

            float min = 0;
            float max = GameConfig.WORLD_WIDTH - obstacle.getWidth();

            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void removePassedObstacles() {
        if (obstacles.size > 0) {
            //Throws exception if array is empty
            Obstacle first = obstacles.first();
            float minObstacleY = -first.getWidth();

            if (first.getY() < minObstacleY) {
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }
        }
    }

    private void updateScore(float delta) {
        scoreTimer += delta;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            //60 here is the target fps of libGDX
            displayScore = Math.min(score, displayScore + (int) (60 * delta));
        }
    }

}
