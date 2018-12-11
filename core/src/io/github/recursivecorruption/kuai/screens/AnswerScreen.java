package io.github.recursivecorruption.kuai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.recursivecorruption.kuai.*;

public class AnswerScreen extends Screen {
    private String guess;
    private Pinyin pinyin;
    private Button button;
    private Button replayButton = new Button("Replay", new Vector2(0.5f, 0.3f));

    public AnswerScreen(String guess, Pinyin pinyin) {
        this.guess = guess;
        this.pinyin = pinyin;
        this.button = new Button("Continue", new Vector2(0.5f, 0.61f), CommonUI.BUTTON_COLOR, CommonUI.BUTTON_PADDING);

        this.pinyin.setLabel(pinyin.getLabel().substring(0, 1).toUpperCase() + pinyin.getLabel().substring(1));
    }

    @Override
    public void render(Renderer renderer) {
        replayButton.render(renderer);
        if (guess.equalsIgnoreCase(pinyin.getLabel())) {
            renderer.text(0.5f, 0.4f, "Correct!", Color.GREEN);
            renderer.text(0.5f, 0.5f, pinyin.getLabel());
        } else {
            renderer.text(0.5f, 0.4f, "You guessed: " + guess);
            renderer.text(0.5f, 0.5f, "The correct answer is: " + pinyin.getLabel());
        }
        button.render(renderer);
    }

    @Override
    public void dispose() {
        pinyin.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int buttonId) {
        if (replayButton.touches(x, y)) {
            pinyin.getSound().play();
            return true;
        }
        return false;
    }

    @Override
    public Screen update(KuaiApp app) {
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            return app.createNextAppScreen();
        }
        if (Gdx.input.isKeyJustPressed(Keys.R)) {
            pinyin.getSound().play();
        }
        if (button.touches(Input.getX(), Input.getY())) {
            return app.createNextAppScreen();
        }
        return null;
    }
}
