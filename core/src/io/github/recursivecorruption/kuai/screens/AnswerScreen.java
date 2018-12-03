package io.github.recursivecorruption.kuai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.recursivecorruption.kuai.Input;

public class AnswerScreen extends io.github.recursivecorruption.kuai.screens.Screen {
    private String guess, answer;
    private Sound sound;
    private io.github.recursivecorruption.kuai.Button button;
    private io.github.recursivecorruption.kuai.Button replayButton = new io.github.recursivecorruption.kuai.Button("Replay", new Vector2(0.5f, 0.3f));

    public AnswerScreen(String guess, String answer, Sound sound) {
        this.guess = guess;
        if (answer != null && answer.length() > 0) {
            answer = answer.substring(0, 1).toUpperCase() + answer.substring(1);
        }
        this.answer = answer;
        this.sound = sound;
        this.button = new io.github.recursivecorruption.kuai.Button("Continue", new Vector2(0.5f, 0.61f), io.github.recursivecorruption.kuai.CommonUI.BUTTON_COLOR, io.github.recursivecorruption.kuai.CommonUI.BUTTON_PADDING);
    }

    @Override
    public void render(io.github.recursivecorruption.kuai.Renderer renderer) {
        replayButton.render(renderer);
        if (guess.equalsIgnoreCase(answer)) {
            renderer.text(0.5f, 0.4f, "Correct!", Color.GREEN);
            renderer.text(0.5f, 0.5f, answer);
        } else {
            renderer.text(0.5f, 0.4f, "You guessed: " + guess);
            renderer.text(0.5f, 0.5f, "The correct answer is: " + answer);
        }
        button.render(renderer);
    }

    @Override
    public void dispose() {
        if (sound != null) {
            sound.dispose();
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int buttonId) {
        if (replayButton.touches(x, y)) {
            if (sound != null) {
                sound.play();
            }
            return true;
        }
        return false;
    }

    @Override
    public io.github.recursivecorruption.kuai.screens.Screen update(io.github.recursivecorruption.kuai.KuaiApp app) {
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            return new io.github.recursivecorruption.kuai.screens.AppScreen();
        }
        if (Gdx.input.isKeyJustPressed(Keys.R)) {
            if (sound != null) {
                sound.play();
            }
        }
        if (button.touches(io.github.recursivecorruption.kuai.Input.getX(), Input.getY())) {
            return new io.github.recursivecorruption.kuai.screens.AppScreen();
        }
        return null;
    }
}
