package com.dsl.testbuild;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dsl.testbuild.common.SampleBase;
import com.dsl.testbuild.common.SampleInfo;
import com.dsl.testbuild.utils.GdxUtils;

public class ViewportSample extends SampleBase {

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ViewportSample.class);
    private static final Logger log = new Logger(ViewportSample.class.getName(), Logger.DEBUG);

    private static final float WORLD_WIDTH = 800.0f;
    private static final float WORLD_HEIGHT = 600.0f;

    private static final float CAMERA_SPEED = 2.0f;
    private static final float CAMERA_ZOOM_SPEED = 2.0f;

    private OrthographicCamera camera;
    private Viewport currentViewport;
    private SpriteBatch batch;

    private Texture texture;
    private BitmapFont font;

    private ArrayMap<String, Viewport> viewports;

    private int currentViewportIndex;
    private String currentViewportName;

    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        camera.update();

        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("raw/level-bg-small.png"));
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));

        createViewports();
        selectNextViewport();

        Gdx.input.setInputProcessor(this);
    }

    private void createViewports() {
        viewports = new ArrayMap<String, Viewport>();

        //Fill Virtual Screen size but will stretch images
        viewports.put(StretchViewport.class.getSimpleName(), new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        //Fills Virtual Screen size but maintains aspect ratio RECOMMENDED
        viewports.put(FitViewport.class.getSimpleName(), new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        //Keeps aspect ratio and fills screen but will cut parts of the world off
        viewports.put(FillViewport.class.getSimpleName(), new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        //Doesn't use world dimensions so small screens will see less of the world
        viewports.put(ScreenViewport.class.getSimpleName(), new ScreenViewport(camera));

        //World scaled to fit in viewport shortest dimension is scaled to fit viewport
        viewports.put(ExtendViewport.class.getSimpleName(), new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        currentViewportIndex = -1;
    }

    private void selectNextViewport() {
        currentViewportIndex = (currentViewportIndex + 1) % viewports.size;

        currentViewport = viewports.getValueAt(currentViewportIndex);
        currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        currentViewportName = viewports.getKeyAt(currentViewportIndex);

        log.debug("Selected viewport " + currentViewportName);
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
        batch.draw(texture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        font.draw(batch, currentViewportName, 50, 100);
    }

    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectNextViewport();
        return true;
    }
}
