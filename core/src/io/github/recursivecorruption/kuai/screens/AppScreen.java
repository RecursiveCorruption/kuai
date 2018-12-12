package io.github.recursivecorruption.kuai.screens;

import com.badlogic.gdx.math.Vector2;
import io.github.recursivecorruption.kuai.Button;
import io.github.recursivecorruption.kuai.KuaiApp;
import io.github.recursivecorruption.kuai.Pinyin;
import io.github.recursivecorruption.kuai.Renderer;

public class AppScreen extends PinyinInputScreen {
    private Pinyin pinyin;
    private Vector2 buttonPos = new Vector2(0.85f, 0.45f);
    private Button replayButton = new Button("Replay", buttonPos);

    public AppScreen(Pinyin pinyin) {
        this.pinyin = pinyin;
        pinyin.getSound().play();
    }

    @Override
    public void dispose() {
        if (pinyin != null) {
            pinyin.dispose();
        }
        super.dispose();
    }

    @Override
    public void render(Renderer renderer) {
        if (renderer.isOnScreen(0.5f, 0.35f - renderer.textHeight() / 2f)) {
            buttonPos.set(0.5f, 0.35f);
            renderer.text(0.5f, 0.45f, "What is the pinyin?");
        } else {
            renderer.text(0.395f, 0.45f, "What is the pinyin?");
            buttonPos.set(0.745f, 0.45f);
        }
        renderer.text(0.5f, 0.55f, getTextEntry());
        replayButton.render(renderer);
    }

    @Override
    public boolean touchDown(float sx, float sy, int pointer, int button) {
        if (replayButton.touches(sx, sy)) {
            playSound();
        } else {
            return super.touchDown(sx, sy, pointer, button);
        }
        return true;
    }

    private void playSound() {
        pinyin.getSound().play();
    }

    @Override
    public boolean keyTyped(char character) {
        if ((int) character == 18) {  // Ctrl+r
            playSound();
            return true;
        }
        return super.keyTyped(character);
    }

    @Override
    public Screen update(KuaiApp app) {
        if (shouldExit()) {
            Screen screen = new AnswerScreen(getTextEntry(), pinyin);
            pinyin = null;
            return screen;
        }
        return this;
    }
}
