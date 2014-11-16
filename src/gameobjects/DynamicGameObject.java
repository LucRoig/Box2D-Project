package gameobjects;


import org.jbox2d.dynamics.Body;

public class DynamicGameObject extends GameObject{

    public DynamicGameObject(Body body) {
        super(body);
    }
}