package com.obstacleavoid.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase {

    private float x, y;
    private Circle bounds;
    private float width = 1, height = 1;

    public GameObjectBase(float boundRadius) {
        bounds = new Circle(x, y, boundRadius);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.x(bounds.x, bounds.y, 0.1f );
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void updateBounds() {
        float halfWidth = getWidth() /2;
        float halfHeight = getHeight() /2;
        bounds.setPosition(x + halfWidth, y + halfHeight);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
    }

    public Circle getBounds() {
        return bounds;
    }

}
