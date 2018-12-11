package io.github.recursivecorruption.kuai;

import com.badlogic.gdx.audio.Sound;

public class Pinyin {
    private String label;
    private Sound sound;

    public Pinyin(String label, Sound sound) {
        this.label = label;
        this.sound = sound;
    }

    public Sound getSound() {
        return sound;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void dispose() {
        sound.dispose();
    }
}
