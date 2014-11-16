package gameobjects;


import org.jbox2d.dynamics.Body;

public abstract class GameObject {
    private final Body body;

    public GameObject(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }
    
}
