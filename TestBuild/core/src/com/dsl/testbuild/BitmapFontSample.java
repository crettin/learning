package com.dsl.testbuild;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dsl.testbuild.common.SampleBase;
import com.dsl.testbuild.common.SampleInfo;

public class BitmapFontSample extends SampleBase {

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(BitmapFontSample.class);

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    BitmapFont testFont;
    private GlyphLayout glyphLayout = new GlyphLayout();

    private static final float WIDTH = 1080f, HEIGHT = 720;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();
        testFont = new BitmapFont(Gdx.files.internal("fonts/testfont.fnt"));
        testFont.getData().markupEnabled = true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {

        String text2 = "[#FF0000]Using [GREEN]Bitmap [BLUE]font";
        glyphLayout.setText(testFont, text2);
        testFont.draw(batch, text2, 30, (WIDTH / 2) + glyphLayout.height, 120, 20, true);

        String text1 = "Using Bitmap font";
        glyphLayout.setText(testFont, text1);
        testFont.draw(batch, text1,
                (WIDTH - glyphLayout.width) / 2,
                (HEIGHT - glyphLayout.height) / 2);
    }

    @Override
    public void dispose() {
        batch.dispose();
        testFont.dispose();
    }
}


