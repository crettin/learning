package com.dsl.testbuild.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GdxUtils {

    public static void clearScreen() {
        clearScreen(Color.BLACK);
    }

    public static void clearScreen(Color colour) {
        Gdx.gl.glClearColor(colour.r, colour.g, colour.b, colour.a);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }

    private GdxUtils(){}
}
