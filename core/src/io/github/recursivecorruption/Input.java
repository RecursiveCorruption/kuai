package io.github.recursivecorruption;

import com.badlogic.gdx.Gdx;

public class Input {
    public static float getX() {
        return tx(Gdx.input.getX());
    }

    public static float getY() {
        return ty(Gdx.input.getY());
    }

    public static float tx(float px) {
        return (px + (Renderer.getRenderSize() - Gdx.graphics.getWidth()) / 2f) / Renderer.getRenderSize();
    }

    public static float ty(float py) {
        return (py + (Renderer.getRenderSize() - Gdx.graphics.getHeight()) / 2f) / Renderer.getRenderSize();
    }

    public static boolean isTouched() {
        return Gdx.input.isTouched();
    }
}
