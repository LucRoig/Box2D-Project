package util;


import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class BodyCallback implements QueryCallback {

    private Vec2 mousePVec;
    
    private Body body;
    private Shape shape;

    public BodyCallback(Vec2 mousePVec) {
        this.mousePVec = mousePVec;
    }

    @Override
    public boolean reportFixture(Fixture fixture) {
        body = fixture.getBody();
        shape = fixture.getShape();
        boolean isInside = shape.testPoint(body.getTransform(), mousePVec);
        return isInside;
    }

    public Body getBody() {
        return body;
    }

    public Shape getShape() {
        return shape;
    }
}
