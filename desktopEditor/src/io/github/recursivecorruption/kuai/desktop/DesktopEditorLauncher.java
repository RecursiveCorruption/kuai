package io.github.recursivecorruption.kuai.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.recursivecorruption.kuai.KuaiApp;
import io.github.recursivecorruption.kuai.screens.EditorScreen;

public class DesktopEditorLauncher {
    private static LwjglApplicationConfiguration createConfig() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.addIcon("icon/icon-256x256.png", Files.FileType.Internal);
        config.addIcon("icon/icon-64x64.png", Files.FileType.Internal);
        config.addIcon("icon/icon-32x32.png", Files.FileType.Internal);
        config.height = 600;
        config.width = 800;
        config.useGL30 = false;
        config.title = "Kuai Editor";
        config.addIcon("icon/icon-64x64.png", Files.FileType.Internal);
        return config;
    }
    public static void main(String[] arg) {
        new LwjglApplication(new KuaiApp(new EditorScreen()), createConfig());
    }
}
