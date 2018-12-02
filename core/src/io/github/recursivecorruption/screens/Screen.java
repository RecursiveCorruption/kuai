package io.github.recursivecorruption.screens;

import com.badlogic.gdx.InputProcessor;
import io.github.recursivecorruption.Input;
import io.github.recursivecorruption.KuaiApp;
import io.github.recursivecorruption.Renderer;

/**
 * Any updatable and renderable object in the world
 */
public abstract class Screen implements InputProcessor {
    public void dispose() {}

    public abstract void render(Renderer renderer);

    public abstract Screen update(KuaiApp app);

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return touchDown(Input.tx(screenX), Input.ty(screenY), pointer, button);
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public final boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public final boolean scrolled(int amount) {
        return false;
    }
}
