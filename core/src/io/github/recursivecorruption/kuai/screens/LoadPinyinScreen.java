package io.github.recursivecorruption.kuai.screens;

import io.github.recursivecorruption.kuai.KuaiApp;
import io.github.recursivecorruption.kuai.Pinyin;
import io.github.recursivecorruption.kuai.Renderer;

public class LoadPinyinScreen extends Screen {
    private boolean hasErrored = false;
    private Pinyin nextPinyin = null;
    private Thread workerThread = null;

    @Override
    public void render(Renderer renderer) {
        renderer.text(0.5f, 0.5f, hasErrored ? "Error. Retrying..." : "Loading...");
    }

    @Override
    public Screen update(final KuaiApp app) {
        if (workerThread == null) {
            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        nextPinyin = app.getPinyinManager().takeNextPinyin();
                        if (nextPinyin != null) {
                            return;
                        }
                        hasErrored = true;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            });
            workerThread.start();
        }
        if (nextPinyin != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    app.getPinyinManager().loadAhead(3);
                }
            }).start();
            return new AppScreen(nextPinyin);
        }
        return this;
    }
}
