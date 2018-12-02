package io.github.recursivecorruption;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Button {
    private String label;
    private Vector2 buttonCorner = null, buttonSize = null;
    private final Vector2 center, padding;
    private Color color;

    public Button(String label, Vector2 center, Color color, Vector2 padding) {
        this.label = label;
        this.center = center;
        this.color = color;
        this.padding = padding;
    }

    public Button(String label, Vector2 center) {
        this.label = label;
        this.center = center;
        this.color = CommonUI.BUTTON_COLOR;
        this.padding = CommonUI.BUTTON_PADDING;
    }

    public void render(Renderer renderer) {
        Vector2 box = renderer.textSize(label).cpy().add(padding.cpy().scl(2f));
        buttonSize = box;
        buttonCorner = center.cpy().sub(box.cpy().scl(1 / 2f));
        renderer.rect(buttonCorner.x, buttonCorner.y, box.x, box.y, color);
        renderer.text(center.x, center.y, label);
    }

    public boolean touches(float x, float y) {
        if (buttonCorner == null || buttonSize == null) {
            return true;
        }
        if (Input.isTouched()) {
            Vector2 botRight = new Vector2(buttonCorner).add(buttonSize);
            return buttonCorner.x < x && x < botRight.x &&
                    buttonCorner.y < y && y < botRight.y;
        }
        return false;
    }
}
