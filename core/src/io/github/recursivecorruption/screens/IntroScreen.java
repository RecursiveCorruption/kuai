package io.github.recursivecorruption.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import io.github.recursivecorruption.KuaiApp;
import io.github.recursivecorruption.Renderer;

public class IntroScreen extends Screen {
    private int currentPos = 0;
    private String[][] intros = {
            {
                    "This tool will help you",
                    "train your ears to",
                    "hear chinese syllables"
            },
            {
                    "To use it, listen to the",
                    "sound and type the",
                    "pinyin transcription"
            },
            {
                    "Type the numbers 1-4",
                    "after a vowel to add a tone:",
                    "a1 -> ā, e4 -> è, o3 -> ǒ"
            },
            {
                    "a + 1 2 3 4 = ā á ǎ à",
                    "e + 1 2 3 4 = ē é ě è",
                    "i + 1 2 3 4 = ī í ǐ ì",
                    "o + 1 2 3 4 = ō ó ǒ ò",
                    "u + 1 2 3 4 = ū ú ǔ ù",
                    "u + 5 6 7 8 = ǖ ǘ ǚ ǜ"
            },
            {
                    "Good luck!"
            }
    };
    private Preferences preferences;

    public IntroScreen(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void render(Renderer renderer) {
        String[] lines = intros[currentPos];
        for (int i = 0; i < lines.length; ++i) {
            renderer.text(0.5f, 0.5f - ((lines.length - 1) / 2f) * 0.07f + 0.07f * i, lines[i]);
        }
    }

    @Override
    public Screen update(KuaiApp app) {
        if (Gdx.input.justTouched()) {
            ++currentPos;
        }
        if (currentPos >= intros.length) {
            preferences.putBoolean("hasShownIntro", true);
            preferences.flush();
            return new AppScreen();
        }
        return this;
    }
}
