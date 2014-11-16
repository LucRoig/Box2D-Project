package gameobjects;

import game.Game;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class ObjectFactory {

    public static GameObject createGround() {
        BodyDef definition = new BodyDef();
        definition.position.set(0, 0);
        definition.type = BodyType.STATIC;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1000, 0);
        Body groundBody = Game.world.createBody(definition);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1;
        fixture.restitution = 0.3f;
        fixture.shape = shape;
        groundBody.createFixture(fixture);

        GameObject ground = new StaticGameObject(groundBody, 1000, 0);
        
        Game.bodies.add(ground);
        return ground;
    }

    public static GameObject createTopWall() {
        BodyDef definition = new BodyDef();
        definition.position.set(0, Game.WINDOW_DIMENSIONS[1] / 30);
        definition.type = BodyType.STATIC;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1000, 0);
        Body topWallBody = Game.world.createBody(definition);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1;
        fixture.restitution = 0.3f;
        fixture.shape = shape;
        topWallBody.createFixture(fixture);
        
        GameObject topWall = new StaticGameObject(topWallBody, 1000, 0);
        
        Game.bodies.add(topWall);
        return topWall;
    }

    public static void createLeftWall() {
        BodyDef definition = new BodyDef();
        definition.position.set(0, 0);
        definition.type = BodyType.STATIC;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0, 1000);
        Body leftWall = Game.world.createBody(definition);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1;
        fixture.restitution = 0.3f;
        fixture.shape = shape;
        leftWall.createFixture(fixture);

        Game.bodies.add(new StaticGameObject(leftWall, 0, 1000));
    }

    public static void createRightWall() {
        BodyDef definition = new BodyDef();
        definition.position.set(Game.WINDOW_DIMENSIONS[0] / 30, 0);
        definition.type = BodyType.STATIC;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0, 1000);
        Body rightWall = Game.world.createBody(definition);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1;
        fixture.restitution = 0.3f;
        fixture.shape = shape;
        rightWall.createFixture(fixture);

        Game.bodies.add(new StaticGameObject(rightWall, 0, 1000));
    }

    public static GameObject createBox(float height, float width, float xPosition, float yPosition) {
        BodyDef definition = new BodyDef();
        definition.position.set(xPosition / 30, yPosition / 30);
        definition.type = BodyType.DYNAMIC;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(height, width);
        Body box = Game.world.createBody(definition);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 0.02f;
        fixture.shape = shape;
        box.createFixture(fixture);

        GameObject newBox = new DynamicBoxGameObject(box, width, height);
        Game.bodies.add(newBox);
        
        return newBox;
    }
    
    public static GameObject createKinematicBox(float height, float width, float xPosition, float yPosition) {
        BodyDef definition = new BodyDef();
        definition.position.set(xPosition / 30, yPosition / 30);
        definition.type = BodyType.KINEMATIC;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(height, width);
        Body box = Game.world.createBody(definition);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 0.02f;
        fixture.shape = shape;
        box.createFixture(fixture);

        GameObject newBox = new DynamicBoxGameObject(box, width, height);
        Game.bodies.add(newBox);
        
        return newBox;
    }
    
    public static GameObject createCircle(float radius, float xPosition, float yPosition) {
        BodyDef definition = new BodyDef();
        definition.position.set(xPosition / 30, yPosition / 30);
        definition.type = BodyType.DYNAMIC;

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        Body circle = Game.world.createBody(definition);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 0.02f;
        fixture.shape = shape;
        circle.createFixture(fixture);

        GameObject newBox = new DynamicCircleGameObject(circle, radius);
        Game.bodies.add(newBox);
        
        return newBox;
    }
    
}
