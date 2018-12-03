package io.github.recursivecorruption.kuai.screens;

import com.badlogic.gdx.InputProcessor;

/**
 * Any updatable and renderable object in the world
 */
public abstract class Screen implements InputProcessor {
    public void dispose() {}

    public abstract void render(io.github.recursivecorruption.kuai.Renderer renderer);

    public abstract Screen update(io.github.recursivecorruption.kuai.KuaiApp app);

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
        return touchDown(io.github.recursivecorruption.kuai.Input.tx(screenX), io.github.recursivecorruption.kuai.Input.ty(screenY), pointer, button);
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
