package com.dsl.testbuild;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dsl.testbuild.common.SampleBase;
import com.dsl.testbuild.common.SampleInfo;
import com.dsl.testbuild.utils.GdxUtils;

public class SpriteBatchSample extends SampleBase {

    private static final Logger log = new Logger(SpriteBatchSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(SpriteBatchSample.class);

    private static float WORLD_WIDTH = 10.8f, WORLD_HEIGHT = 7.2f; //World units

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texture;
    private Color oldColor;

    private int width = 1, height = 1;//World units

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        oldColor = new Color();
        texture = new Texture(Gdx.files.internal("raw/character.png"));
    }

    @Override
    public void render() {
        GdxUtils.clearScreen();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {

        batch.draw(texture,
                1, 1,
                width / 2f, height / 2f,
                width, height,                          //width, height
                1.0f, 1.0f,
                0,
                0, 0,
                texture.getWidth(), texture.getHeight(),//srcWidth, srcHeight
                false, false);

        //Render scaled char
        batch.draw(texture,
                4, 2,
                width / 2f, height / 2f,
                width, height,
                2.0f, 2.0f,
                0,
                0, 0,
                texture.getWidth(), texture.getWidth(),
                false, false);

        //save old color
        oldColor.set(batch.getColor());

        //Set colour of sprite batch
        batch.setColor(Color.GREEN);

        //Render green character
        batch.draw(texture,
                8, 1,
                width / 2f, height / 2f,
                width, height,
                1.0f, 1.0f,
                0,
                0, 0,
                texture.getWidth(), texture.getWidth(),
                false, false);

        batch.setColor(oldColor);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
