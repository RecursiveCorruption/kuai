package io.github.recursivecorruption.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import io.github.recursivecorruption.Button;
import io.github.recursivecorruption.Constants;
import io.github.recursivecorruption.KuaiApp;
import io.github.recursivecorruption.Renderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

import static io.github.recursivecorruption.Constants.ACCENT_GROUPS;

public class AppScreen extends Screen {
    private String textEntry = "", pinyin = null;
    private Sound sound = null;
    private Random random = new Random();
    private int savedAccent = -1;
    private Vector2 buttonPos = new Vector2(0.85f, 0.45f);
    private Button replayButton = new Button("Replay", buttonPos);
    private boolean exit = false;

    public AppScreen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (sound == null || sound.play() == -1) {
                    loadSound();
                }
            }
        }).start();
    }

    private static FileHandle saveFile(String url) {
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileHandle fileHandle = new FileHandle(File.createTempFile("kuai-sample", ".mp3"));
            OutputStream outBuffer = fileHandle.write(false);

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                outBuffer.write(dataBuffer, 0, bytesRead);
            }
            return fileHandle;
        } catch (IOException e) {
            System.err.println("Failed to load URL: " + url);
            return null;
        }
    }

    private static String getPinyin(String soundId) {
        try {
            Document doc = Jsoup.connect(String.format(Constants.INFO_URL, soundId)).get();

            for (Element element : doc.select("a")) {
                if (element.toString().contains("custom.pinyin")) {
                    return element.text();
                }
            }

        } catch (IOException e) {
            return "";
        }
        return "";
    }

    private static Set<Integer> range(int a, int b) {
        Set<Integer> vals = new HashSet<>();
        for (int i = a; i < b; ++i) {
            vals.add(i);
        }
        return vals;
    }

    private static <E> E getIndex(Collection<? extends E> items, int index) {
        if (index >= items.size() || index < 0) {
            throw new IllegalArgumentException();
        }
        Iterator<? extends E> iter = items.iterator();
        for (int i = 0; i < index; ++i) {
            iter.next();
        }
        return iter.next();
    }

    private int randomSoundId() {
        Set<Integer> ids = range(1584, 11150);
        ids.removeAll(range(1734, 1934));
        ids.removeAll(range(2012, 2293));
        ids.removeAll(range(1590, 1599));
        return getIndex(ids, random.nextInt(ids.size()));
    }

    private void loadSound() {
        if (sound != null) {
            sound.dispose();
            sound = null;
        }
        String soundId = "" + randomSoundId();
        String url = String.format(Constants.MP3_URL, soundId);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        FileHandle basicHandle = saveFile(url);
        if (basicHandle != null) {
            FileHandle handle = Gdx.files.absolute(basicHandle.path());
            Sound sound = Gdx.audio.newSound(handle);
            String pinyin = getPinyin(soundId);
            this.sound = sound;  // Prevent sound loading before pinyin
            this.pinyin = pinyin;
        }
    }

    @Override
    public void dispose() {
        if (sound != null) {
            sound.dispose();
        }
        Gdx.input.setOnscreenKeyboardVisible(false);
    }

    @Override
    public void render(Renderer renderer) {
        if (sound == null) {
            renderer.text(0.5f, 0.5f, "Loading...");
        } else {
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
    }

    private static String adjustedAccent(String textEntry, int accentNum) {
        if (textEntry.length() < 1) {
            return null;
        }
        String rawBehind = ("" + textEntry.charAt(textEntry.length() - 1));
        String behind = rawBehind.toLowerCase();
        boolean isLower = behind.equals(rawBehind);
        for (String accents : ACCENT_GROUPS) {
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

    private boolean keyHeld(int key) {
        return Gdx.input.isKeyPressed(key);
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
        if (sound != null) {
            sound.play();
        }
    }

    @Override
    public boolean keyTyped(char character) {
        String s = "" + character;
        Gdx.app.log("Kuai", "Key: " + character + " = " + (int) character);
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
            Screen screen = new AnswerScreen(textEntry, pinyin, sound);
            sound = null;
            return screen;
        }
        return this;
    }
}
