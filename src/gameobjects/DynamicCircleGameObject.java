
package gameobjects;

import org.jbox2d.dynamics.Body;


public class DynamicCircleGameObject extends DynamicGameObject{
    private float radius;
    
    public DynamicCircleGameObject(Body body, float radius) {
        super(body);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
    
}
