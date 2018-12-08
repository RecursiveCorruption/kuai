package io.github.recursivecorruption.kuai;

import com.badlogic.gdx.Gdx;

public class Logger {
    static public void log(String message) {
        Gdx.app.log("kuai", message);
    }
    static public void error(String message) {
        Gdx.app.error("kuai", message);
    }
}
