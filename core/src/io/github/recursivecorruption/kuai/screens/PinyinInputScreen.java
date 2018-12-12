package io.github.recursivecorruption.kuai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.recursivecorruption.kuai.Constants;

public abstract class PinyinInputScreen extends Screen {
    private String textEntry = "";
    private int savedAccent = -1;
    private boolean exit = false;

    boolean shouldExit() {
        return exit;
    }

    String getTextEntry() {
        return textEntry;
    }

    String getValidCharacters() {
        return " abcdefghijklmnopqrstuvwxyzāáǎàēéěèīíǐìōóǒòūúǔùǖǘǚǜ";
    }

    @Override
    public void dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false);
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
        Gdx.input.setOnscreenKeyboardVisible(true);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        String s = "" + character;
        boolean controlPressed = Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT);
        if ((int) character == 13 && controlPressed) {
            character = '\n';
            s = "\n";
        }
        switch ((int) character) {
            case 10:  // Enter Mobile
            case 13:  // Enter
                if (controlPressed) {
                    break;
                }
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

        if (getValidCharacters().contains(s.toLowerCase())) {
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
}
