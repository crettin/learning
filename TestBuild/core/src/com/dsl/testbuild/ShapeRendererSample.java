package com.dsl.testbuild;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dsl.testbuild.common.SampleBase;
import com.dsl.testbuild.common.SampleInfo;
import com.dsl.testbuild.utils.GdxUtils;

public class ShapeRendererSample extends SampleBase {

    private static final Logger log = new Logger(ShapeRendererSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ShapeRendererSample.class);

    private static final float WORLD_WIDTH = 20f;
    private static final float WORLD_HEIGHT = 40f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private boolean drawGrid = true;
    private boolean drawCircles = true;
    private boolean drawRectangles = true;
    private boolean drawPoints = true;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        GdxUtils.clearScreen();

        renderer.setProjectionMatrix(camera.combined);

        if (drawGrid) {
            drawGrid();
        }

        if(drawCircles){
            drawCircles();
        }

        if(drawRectangles){
            drawRectangles();
        }

        if(drawPoints){
            drawPoints();
        }

    }

    private void drawGrid() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        int worldWidth = (int) WORLD_WIDTH;
        int worldHeight = (int) WORLD_HEIGHT;

        for (int x = -worldWidth; x < worldHeight; x++) {
            renderer.line(x, -worldHeight, x, worldHeight);
        }

        for (int y = -worldHeight; y < worldHeight; y++) {
            renderer.line(-worldWidth, y, worldHeight, y);
        }

        renderer.setColor(Color.RED);
        renderer.line(-worldWidth, 0.0f, worldWidth,0.0f);
        renderer.line( 0.0f,-worldHeight,0.0f, worldHeight);

        renderer.end();
    }

    private void drawCircles(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);

        renderer.circle(0,0,2, 30);
        renderer.circle(-5,-5,1);


        renderer.end();
    }

    private void drawRectangles(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);

        renderer.rect(-8,4,4,2);
        renderer.rect(-8,10,1,2);


        renderer.end();
    }

    private void drawPoints(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.MAGENTA);

        renderer.point(-5,0,0);
        renderer.point(5,-3,0);
        renderer.point(8,6,1);

        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.x(-10,0,0.25f);

        renderer.end();

    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.G){
            drawGrid = !drawGrid;
        }else if(keycode == Input.Keys.C){
            drawCircles = !drawCircles;
        }else if(keycode == Input.Keys.R){
            drawRectangles = !drawRectangles;
        }else if(keycode == Input.Keys.P){
            drawPoints = !drawPoints;
        }

        return true;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
