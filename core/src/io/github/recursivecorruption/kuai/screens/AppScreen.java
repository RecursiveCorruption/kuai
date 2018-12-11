package io.github.recursivecorruption.kuai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import io.github.recursivecorruption.kuai.*;

public class AppScreen extends Screen {
    private Pinyin pinyin;
    private String textEntry = "";
    private int savedAccent = -1;
    private Vector2 buttonPos = new Vector2(0.85f, 0.45f);
    private Button replayButton = new Button("Replay", buttonPos);
    private boolean exit = false;

    public AppScreen(Pinyin pinyin) {
        this.pinyin = pinyin;
        pinyin.getSound().play();
    }

    @Override
    public void dispose() {
        if (pinyin != null) {
            pinyin.dispose();
        }
        Gdx.input.setOnscreenKeyboardVisible(false);
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
        renderer.text(0.5f, 0.55f, textEntry);
        replayButton.render(renderer);
    }

    private static String adjustedAccent(String textEntry, int accentNum) {
        if (textEntry.length() < 1) {
            return null;
        }
        String rawBehind = ("" + textEntry.charAt(textEntry.length() - 1));
        String behind = rawBehind.toLowerCase();
        boolean isLower = behind.equals(rawBehind);
        for (String accents : Constants.ACCENT_GROUPS) {
            if (accents.contains(behind) && accentNum < accents.length()) {
                String accent = "" + accents.charAt(accentNum);
                if (!isLower) {
                    accent = accent.toUpperCase();
                }
                return textEntry.substring(0, textEntry.length() - 1) + accent;
            }
        }
        return null;
    }

    @Override
    public boolean touchDown(float sx, float sy, int pointer, int button) {
        if (replayButton.touches(sx, sy)) {
            playSound();
        } else {
            Gdx.input.setOnscreenKeyboardVisible(true);
        }
        return true;
    }

    private void playSound() {
        pinyin.getSound().play();
    }

    @Override
    public boolean keyTyped(char character) {
        String s = "" + character;
        switch ((int) character) {
            case 18:  // Ctrl+r
                playSound();
                return true;
            case 10:  // Enter Mobile
            case 13:  // Enter
            case 32:  // Space
                if (textEntry.length() > 0) {
                    exit = true;
                }
                return true;
            case 8: // Backspace
                if (textEntry.length() > 0) {
                    textEntry = textEntry.substring(0, textEntry.length() - 1);
                }
                return true;
        }

        if (" abcdefghijklmnopqrstuvwxyzāáǎàēéěèīíǐìōóǒòūúǔùǖǘǚǜ".contains(s.toLowerCase())) {
            if (textEntry.length() != 0) {
                s = s.toLowerCase();
            } else {
                s = s.toUpperCase();
            }
            textEntry += s;
            if (savedAccent != -1) {
                String adjusted = adjustedAccent(textEntry, savedAccent);
                if (adjusted != null) {
                    textEntry = adjusted;
                }
            }
            savedAccent = -1;
            return true;
        }
        if ("012345678".contains(s)) {
            int num = character - '0';
            String adjusted = adjustedAccent(textEntry, num);
            if (adjusted != null) {
                textEntry = adjusted;
                savedAccent = -1;
            } else {
                savedAccent = num;
            }
            return true;
        }

        return false;
    }

    @Override
    public Screen update(KuaiApp app) {
        if (exit) {
            Screen screen = new AnswerScreen(textEntry, pinyin);
            pinyin = null;
            return screen;
        }
        return this;
    }
}
