package io.github.recursivecorruption;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Renderer {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont font;
    private SpriteBatch batch = new SpriteBatch();

    private Matrix4 matrix = new Matrix4();

    public static int getRenderSize() {
        return Math.max(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static boolean isPortrait() {
        return Gdx.graphics.getWidth() < Gdx.graphics.getHeight();
    }

    public float textHeight() {
        return 1 / 20f + 3 / (float) getRenderSize();
    }

    public boolean isOnScreen(float x, float y) {
        int renderSize = getRenderSize();
        float xEdge = (renderSize - Gdx.graphics.getWidth()) / (2f * renderSize);
        float yEdge = (renderSize - Gdx.graphics.getHeight()) / (2f * renderSize);
        return x > xEdge && x < 1f - xEdge && y > yEdge && y < 1f - yEdge;
    }

    public static String removeDuplicates(String input){
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            if(!result.contains(String.valueOf(input.charAt(i)))) {
                result += String.valueOf(input.charAt(i));
            }
        }
        return result;
    }

    private BitmapFont generateFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ClearSans-Thin.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        for (String accents : Constants.ACCENT_GROUPS) {
            parameter.characters += accents.toLowerCase() + accents.toUpperCase();
        }
        parameter.characters = removeDuplicates(parameter.characters);
        parameter.size = 3 + getRenderSize() / 20;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    public void resize(int sx, int sy) {
        matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(matrix);
        shapeRenderer.setProjectionMatrix(matrix);
        font = generateFont();
    }

    public Renderer() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void begin() {
        batch.begin();
    }

    public void end() {
        batch.end();
    }

    public void circle(float x, float y, float radius, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(tx(x), ty(y), radius);
    }

    public void rect(float x, float y, float width, float height, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(tx(x), ty(y), getRenderSize() * width, -getRenderSize() * height);
        shapeRenderer.end();
    }

    public Vector2 text(float x, float y, String message, Color color) {
        batch.begin();
        batch.setColor(color);
        Vector2 box = textSize(message);
        font.draw(batch, message, tx(x), ty(y - box.y / 2f), 0, Align.center, false);
        batch.end();
        return box;
    }

    public Vector2 textSize(String text) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return new Vector2(layout.width / getRenderSize(), layout.height / getRenderSize());
    }

    public Vector2 text(float x, float y, String message) {
        return text(x, y, message, Color.WHITE);
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
        batch.dispose();
    }

    private float tx(float x) {
        float width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();
        float renderWidth = Math.max(width, height);
        float offset = (width - renderWidth) / 2f;
        return offset + renderWidth * x;
    }

    private float ty(float y) {
        float width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();
        float renderHeight = Math.max(width, height);
        float offset = (height - renderHeight) / 2f;
        return offset + renderHeight * (1f - y);
    }
}
