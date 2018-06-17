package com.obstacleavoid.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportUtils {

    private static final Logger log = new Logger(ViewportUtils.class.getName(), Logger.DEBUG);

    private static final int DEFAULT_CELL_SIZE = 1;
    private static final Color DEFAULT_GRID_COLOUR = Color.WHITE;
    private static final Color DEFAULT_AXIS_COLOUR = Color.RED;
    private static final Color DEFAULT_WORLD_BOUNDARY_COLOUR = Color.GREEN;

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer) {
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE, DEFAULT_GRID_COLOUR, DEFAULT_AXIS_COLOUR, DEFAULT_WORLD_BOUNDARY_COLOUR);
    }


    public static void drawGrid(Viewport viewport, ShapeRenderer renderer, int cellSize, Color gridColor, Color axisColor, Color boundaryColour) {
        //Validate Paramas
        if (viewport == null)
            throw new IllegalArgumentException("Viewport param is required");

        if (renderer == null)
            throw new IllegalArgumentException("Renderer param is required");

        if (cellSize < DEFAULT_CELL_SIZE) {
            cellSize = DEFAULT_CELL_SIZE;
            log.debug("Cell Size was " + cellSize + " minimum is " + DEFAULT_CELL_SIZE);
        }

        //Copy old Colour
        Color oldColor = new Color(renderer.getColor());

        int worldWidth = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(gridColor);

        // draw vertical lines
        for (int x = -doubleWorldWidth; x < doubleWorldWidth; x += cellSize) {
            renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }

        // draw horizontal lines
        for (int y = -doubleWorldHeight; y <= doubleWorldHeight; y += cellSize) {
            renderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }

        //Draw x-y axis lines
        renderer.setColor(axisColor);
        renderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        renderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);

        renderer.setColor(boundaryColour);
        renderer.line(0, worldHeight, worldWidth, worldHeight);
        renderer.line(worldWidth, 0, worldWidth, worldHeight);

        renderer.end();

        renderer.setColor(oldColor);
    }

    public static void debugPixelPerUnit(Viewport viewport) {
        if (viewport == null)
            throw new IllegalArgumentException("Viewport param is required");

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //PPU -> Pixels per World Unit
        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("X PPU " + xPPU + " y PPU " + yPPU);
    }

    private ViewportUtils() {
    }
}
