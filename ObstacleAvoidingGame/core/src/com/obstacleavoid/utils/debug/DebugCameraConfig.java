package com.obstacleavoid.utils.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraConfig {

    private static final Logger log = new Logger(DebugCameraConfig.class.getName(), Logger.DEBUG);

    //== Constants ==
    private static final int DEFAULT_LEFT_KEY = Input.Keys.A;
    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
    private static final int DEFAULT_UP_KEY = Input.Keys.W;
    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;

    private static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.COMMA;
    private static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.PERIOD;

    private static final int DEFAULT_RESET_KEY = Input.Keys.BACKSPACE;
    private static final int DEFAULT_LOG_KEY = Input.Keys.ENTER;

    private static final float DEFAULT_MOVE_SPEED = 20.0f;

    private static final float DEFAULT_ZOOM_SPEED = 2.0f;
    private static final float MAX_ZOOM_IN = 0.02f;
    private static final float MAX_ZOOM_OUT = 30.0f;

    private static final String FILE_PATH = "Debug/debugCameraConfig.json";

    private static final String MAX_ZOOM_IN_STR = "maxZoomIn";
    private static final String MAX_ZOOM_OUT_STR = "maxZoomOut";
    private static final String MOVE_SPEED_STR = "moveSpeed";
    private static final String ZOOM_SPEED_STR = "zoomSpeed";

    private static final String LEFT_KEY_STR = "leftKey";
    private static final String RIGHT_KEY_STR = "rightKey";
    private static final String UP_KEY_STR = "upKey";
    private static final String DOWN_KEY_STR = "downKey";

    private static final String ZOOM_IN_KEY_STR = "zoomInKey";
    private static final String ZOOM_OUT_KEY_STR = "zoomOutKey";

    private static final String RESET_KEY_STR = "resetKey";
    private static final String LOG_KEY_STR = "logKey";


    //== Attributes ==
    private float maxZoomIn, maxZoomOut, moveSpeed, zoomSpeed;
    private int leftKey, rightKey, upKey, downKey, zoomInKey, zoomOutKey, resetKey, logKey;

    private FileHandle fileHandle;

    public DebugCameraConfig() {
        init();
    }

    //== init ==
    private void init() {
        fileHandle = Gdx.files.internal(FILE_PATH);

        if (fileHandle.exists()) {
            load();
        } else {
            log.debug("File doesn't exist" + FILE_PATH);
            setupDefaults();
        }
    }

    private void load() {
        try {
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(fileHandle);

            maxZoomIn = root.getFloat(MAX_ZOOM_IN_STR, MAX_ZOOM_IN);
            maxZoomOut = root.getFloat(MAX_ZOOM_OUT_STR, MAX_ZOOM_OUT);
            zoomSpeed = root.getFloat(ZOOM_SPEED_STR, DEFAULT_ZOOM_SPEED);
            moveSpeed = root.getFloat(MOVE_SPEED_STR, DEFAULT_MOVE_SPEED);

            zoomInKey = getInputKeyValue(root, ZOOM_IN_KEY_STR, DEFAULT_ZOOM_IN_KEY);
            zoomOutKey = getInputKeyValue(root, ZOOM_OUT_KEY_STR, DEFAULT_ZOOM_OUT_KEY);

            leftKey = getInputKeyValue(root, LEFT_KEY_STR, DEFAULT_LEFT_KEY);
            rightKey = getInputKeyValue(root, RIGHT_KEY_STR, DEFAULT_RIGHT_KEY);
            downKey = getInputKeyValue(root, DOWN_KEY_STR, DEFAULT_DOWN_KEY);
            upKey = getInputKeyValue(root, UP_KEY_STR, DEFAULT_UP_KEY);

            resetKey = getInputKeyValue(root, RESET_KEY_STR, DEFAULT_RESET_KEY);
            logKey = getInputKeyValue(root, LOG_KEY_STR, DEFAULT_LOG_KEY);

        } catch (Exception e) {
            log.error("Error Loading " + FILE_PATH + " using defaults. ", e);
            setupDefaults();
        }
    }

    private void setupDefaults() {
        moveSpeed = DEFAULT_MOVE_SPEED;
        maxZoomIn = MAX_ZOOM_IN;
        maxZoomOut = MAX_ZOOM_OUT;
        zoomSpeed = DEFAULT_ZOOM_SPEED;

        leftKey = DEFAULT_LEFT_KEY;
        rightKey = DEFAULT_RIGHT_KEY;
        upKey = DEFAULT_UP_KEY;
        downKey = DEFAULT_DOWN_KEY;

        zoomInKey = DEFAULT_ZOOM_IN_KEY;
        zoomOutKey = DEFAULT_ZOOM_OUT_KEY;
        resetKey = DEFAULT_RESET_KEY;
        logKey = DEFAULT_LOG_KEY;
    }

    public float getMaxZoomIn() {
        return maxZoomIn;
    }

    public float getMaxZoomOut() {
        return maxZoomOut;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getZoomSpeed() {
        return zoomSpeed;
    }

    public boolean isLeftPressed() {
        return Gdx.input.isKeyPressed(leftKey);
    }

    public boolean isRightPressed() {
        return Gdx.input.isKeyPressed(rightKey);
    }

    public boolean isDownPressed() {
        return Gdx.input.isKeyPressed(downKey);
    }

    public boolean isUpPressed() {
        return Gdx.input.isKeyPressed(upKey);
    }

    public boolean isZoomInPressed() {
        return Gdx.input.isKeyPressed(zoomInKey);
    }

    public boolean isZoomOutPressed() {
        return Gdx.input.isKeyPressed(zoomOutKey);
    }

    public boolean isResetPressed() {
        return Gdx.input.isKeyPressed(resetKey);
    }

    public boolean isLogPressed() {
        return Gdx.input.isKeyPressed(logKey);
    }

    @Override
    public String toString() {
        String LS = "\n";

        return "DebugCameraConfig { " + LS +
                LS + "//=== Default Numbers ==="  + LS +
                "maxZoomIn: " + maxZoomIn + LS +
                "maxZoomOut: " + maxZoomOut + LS +
                "moveSpeed: " + moveSpeed + LS +
                "zoomSpeed: " + zoomSpeed + LS +
                LS + "//=== Keys ==="  + LS +
                "leftKey: " + getKeyByCode(leftKey) + LS +
                "rightKey: " + getKeyByCode(rightKey) + LS +
                "upKey: " + getKeyByCode(upKey) + LS +
                "downKey: " + getKeyByCode(downKey)+ LS +
                "zoomInKey: " + getKeyByCode(zoomInKey) + LS +
                "zoomOutKey: " + getKeyByCode(zoomOutKey) + LS +
                "ResetKey: " + getKeyByCode(resetKey) + LS +
                "logKey: " + getKeyByCode(logKey) + LS + " }";

    }

    // == Static Methods ==
    private static int getInputKeyValue(JsonValue root, String name, int defaultInput) {
        String keyStroke = root.getString(name, Input.Keys.toString(defaultInput));
        return Input.Keys.valueOf(keyStroke);
    }

    private static String getKeyByCode(int keyCode){
        return Input.Keys.toString(keyCode);
    }
}
