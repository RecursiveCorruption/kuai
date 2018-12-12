package io.github.recursivecorruption.kuai.screens;

import io.github.recursivecorruption.kuai.KuaiApp;
import io.github.recursivecorruption.kuai.Renderer;

public class EditorScreen extends PinyinInputScreen {
    @Override
    public void render(Renderer renderer) {
        renderer.text(0.5f, 0.5f, getTextEntry());
    }

    @Override
    public Screen update(KuaiApp app) {
        if (shouldExit()) {
            return new EditorScreen();
        }
        return this;
    }

    @Override
    String getValidCharacters() {
        return super.getValidCharacters() + ".,\"\n':/?<>!";
    }
}
