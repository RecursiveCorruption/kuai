package io.github.recursivecorruption.kuai.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import io.github.recursivecorruption.kuai.KuaiApp;
import io.github.recursivecorruption.kuai.screens.EditorScreen;

import static io.github.recursivecorruption.kuai.desktop.DesktopLauncher.createConfig;

public class DesktopEditorLauncher {
    public static void main(String[] arg) {
        new LwjglApplication(new KuaiApp(new EditorScreen()), createConfig());
    }
}
