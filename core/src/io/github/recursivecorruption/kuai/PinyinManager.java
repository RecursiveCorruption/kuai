package io.github.recursivecorruption.kuai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.net.URL;
import java.util.*;

import static io.github.recursivecorruption.kuai.Constants.MP3_URL;

public class PinyinManager {
    private Map<Integer, String> pinyinData = loadPinyinData();
    private Random random = new Random();
    private ArrayDeque<Pinyin> nextPinyins = new ArrayDeque<>();

    public Pinyin takeNextPinyin() {
        if (!loadAhead(1)) {
            return null;
        }
        return nextPinyins.removeFirst();
    }

    public boolean hasNext() {
        return nextPinyins.size() > 0;
    }

    private Pinyin loadRandomPinyin() {
        int soundId = randomSoundId();
        FileHandle basicHandle = saveFile(String.format(MP3_URL, "" + soundId));
        if (basicHandle == null) {
            return null;
        }
        FileHandle handle = Gdx.files.absolute(basicHandle.path());
        Sound sound = Gdx.audio.newSound(handle);
        return new Pinyin(pinyinData.get(soundId), sound);
    }

    public boolean loadAhead(int numToCache) {
        while (nextPinyins.size() < numToCache) {
            Pinyin nextEntry = loadRandomPinyin();
            if (nextEntry == null) {
                return false;
            }
            nextPinyins.addFirst(nextEntry);
        }
        return true;
    }

    private static Map<Integer, String> loadPinyinData() {
        Map<Integer, String> pinyinData = new HashMap<>();
        Reader in = new InputStreamReader(Gdx.files.internal("pinyin-data.txt").read());
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(" ");
            pinyinData.put(Integer.parseInt(parts[0]), parts[1]);
        }
        return pinyinData;
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
        return getIndex(pinyinData.keySet(), random.nextInt(pinyinData.size()));
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
            Logger.error("Failed to load " + url + " with error: " + e);
            return null;
        }
    }
}
