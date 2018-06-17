package com.obstacleavoid.config;

public class GameConfig {

    public static final float WIDTH = 480;
    public static final float HEIGHT = 800f;

    public static final float HUD_WIDTH = 480f;
    public static final float HUD_HEIGHT = 800f;

    public static final float WORLD_WIDTH = 6.0f;
    public static final float WORLD_HEIGHT = 8.0f;

    public static final float WORLD_CENTRE_X = WORLD_WIDTH / 2f;
    public static final float WORLD_CENTRE_Y = WORLD_HEIGHT / 2f;

    public static final float PLAYER_BOUNDS_RADIUS = 0.4f;
    public static final float PLAYER_SIZE = PLAYER_BOUNDS_RADIUS * 2;

    public static final float PLAYER_X_SPEED = 0.25f;
    public static final float OBSTACLE_SPAWN_TIME = 0.25f;
    public static final float SCORE_MAX_TIME = 1.25f;

    public static final int LIVES_START = 3;

    public static final float EASY_OBSTACLE_SPEED = 0.1f;
    public static final float MEDIUM_OBSTACLE_SPEED = 0.5f;
    public static final float HARD_OBSTACLE_SPEED = 0.8f;


    private GameConfig() {
    }
}
