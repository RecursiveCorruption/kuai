package io.github.recursivecorruption;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.recursivecorruption.screens.AppScreen;
import io.github.recursivecorruption.screens.IntroScreen;
import io.github.recursivecorruption.screens.Screen;

public class KuaiApp extends ApplicationAdapter {
    private Renderer renderer;
    private Preferences preferences;
    private Screen screen;

    @Override
    public void create() {
        renderer = new Renderer();
        preferences = Gdx.app.getPreferences("data");
        if (!preferences.getBoolean("hasShownIntro", false)) {
            setScreen(new IntroScreen(preferences));
        } else {
            setScreen(new AppScreen());
        }
    }

    private void setScreen(Screen newScreen) {
        if (newScreen == null || newScreen == screen) {
            return;
        }
        if (screen != null) {
            screen.dispose();
        }
        screen = newScreen;
        Gdx.input.setInputProcessor(screen);
    }

    private void update() {
        setScreen(screen.update(this));
    }

    @Override
    public void render() {
        update();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        screen.render(renderer);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void dispose() {
        screen.dispose();
        renderer.dispose();
    }
}
