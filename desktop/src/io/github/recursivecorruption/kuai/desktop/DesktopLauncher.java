package io.github.recursivecorruption.kuai.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.Files.FileType;
import io.github.recursivecorruption.kuai.KuaiApp;

public class DesktopLauncher {
	public static LwjglApplicationConfiguration createConfig() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("icon/icon-256x256.png", FileType.Internal);
		config.addIcon("icon/icon-64x64.png", FileType.Internal);
		config.addIcon("icon/icon-32x32.png", FileType.Internal);
		config.height = 600;
		config.width = 800;
		config.useGL30 = false;
		config.title = "Kuai";
		config.addIcon("icon/icon-64x64.png", Files.FileType.Internal);
		return config;
	}
	public static void main (String[] arg) {
		new LwjglApplication(new KuaiApp(), createConfig());
	}
}
