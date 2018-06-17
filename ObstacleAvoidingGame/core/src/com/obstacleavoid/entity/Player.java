package com.obstacleavoid.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.obstacleavoid.config.GameConfig;

public class Player extends GameObjectBase {

    public Player() {
        super(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE,GameConfig.PLAYER_SIZE);
    }

}
