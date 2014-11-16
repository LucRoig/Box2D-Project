package gameobjects;

import org.jbox2d.dynamics.Body;

public class StaticGameObject extends GameObject {

    private float width;
    private float height;

    public StaticGameObject(Body body, float width, float height) {
        super(body);
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
