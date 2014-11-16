package game;

import gameobjects.DynamicBoxGameObject;
import gameobjects.DynamicCircleGameObject;
import util.BodyCallback;
import gameobjects.GameObject;
import gameobjects.ObjectFactory;
import java.util.HashSet;
import java.util.Set;
import joints.Joints;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Game {

    private static final String WINDOW_TITLE = "Physics in 2D!";
    public static final float[] WINDOW_DIMENSIONS = {800, 600};

    public static World world = new World(new Vec2(0, -9.8f));
    public static Set<GameObject> bodies = new HashSet<>();
    private static final int physicScale = 30;

    private static float mouseXWorldPhys;
    private static float mouseYWorldPhys;

    private static float mouseXWorld = 0;
    private static float mouseYWorld = 0;

    private static GameObject ground;
    private static GameObject topWall;

    private static MouseJoint mouseJoint;

    private static Body mouseAnchorBody = world.createBody(new BodyDef());

    private static final Vec2 mousePVec = new Vec2();

    private static boolean isTestUp = false;

    public Game() {
        setUpDisplay();
        setUpObjects();
        setUpMatrices();
        gameLoop();
        cleanUp(false);
    }

    private static void setUpNewWorld() {
        world = new World(new Vec2(0, -9.8f));
        bodies = new HashSet<>();
        mouseAnchorBody = world.createBody(new BodyDef());
        setUpObjects();
    }

    private static void setUpDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode((int) WINDOW_DIMENSIONS[0], (int) WINDOW_DIMENSIONS[1]));
            Display.setTitle(WINDOW_TITLE);
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            cleanUp(true);
        }
    }

    private static void cleanUp(boolean asCrash) {
        Display.destroy();
        System.exit(asCrash ? 1 : 0);
    }

    private static void setUpObjects() {
        ground = ObjectFactory.createGround();
        topWall = ObjectFactory.createTopWall();
        ObjectFactory.createLeftWall();
        ObjectFactory.createRightWall();
    }

    private static void setUpMatrices() {
        glMatrixMode(GL_PROJECTION);
        glOrtho(0, WINDOW_DIMENSIONS[0], 0, WINDOW_DIMENSIONS[1], 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    private static void gameLoop() {
        while (!Display.isCloseRequested()) {
            render();
            logic();
            input();
            update();
        }
    }

    private static void renderCircle(float x, float y, float radius){
        glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(x, y);
        for(int angle=0; angle<=360;angle+=5){
            GL11.glVertex2f(x + (float)Math.sinh(angle)*radius, y + (float)Math.cosh(angle*radius));
        }
        glEnd();
    }
    
    private static void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        for (GameObject gameObject : bodies) {
            if (gameObject.getClass() == DynamicBoxGameObject.class) {
                glPushMatrix();
                Vec2 bodyPosition = gameObject.getBody().getPosition().mul(30);
                glTranslatef(bodyPosition.x, bodyPosition.y, 0);

                glRotated(Math.toDegrees(gameObject.getBody().getAngle()), 0, 0, 1);

                float height = ((DynamicBoxGameObject) gameObject).getHeight();
                float width = ((DynamicBoxGameObject) gameObject).getWidth();

                glRectf(-width * 30, -height * 30, width * 30, height * 30);
                glPopMatrix();
            } else if (gameObject.getClass() == DynamicCircleGameObject.class) {
                glPushMatrix();
                Vec2 bodyPosition = gameObject.getBody().getPosition().mul(30);
                

                
                renderCircle(bodyPosition.x, bodyPosition.y, ((DynamicCircleGameObject)gameObject).getRadius());
                glPopMatrix();
            }
        }
    }

    private static void logic() {
        world.step(1 / 60f, 8, 3);
    }

    private static void input() {

        if (!isTestUp && Keyboard.isKeyDown(Keyboard.KEY_1)) {
            GameObject box1 = ObjectFactory.createBox(1.0f, 1.0f, WINDOW_DIMENSIONS[0] / 2, WINDOW_DIMENSIONS[1] / 2);
            GameObject box2 = ObjectFactory.createBox(1.0f, 1.0f, (WINDOW_DIMENSIONS[0] / 2) + 100, (WINDOW_DIMENSIONS[1] / 2) + 100);
            world.createJoint(Joints.distanceJoint(box1, box2));
            isTestUp = true;
        }

        if (!isTestUp && Keyboard.isKeyDown(Keyboard.KEY_2)) {
            GameObject box1 = ObjectFactory.createBox(1.0f, 1.0f, WINDOW_DIMENSIONS[0] / 2, WINDOW_DIMENSIONS[1] / 2);
            GameObject box2 = ObjectFactory.createBox(1.0f, 1.0f, (WINDOW_DIMENSIONS[0] / 2) + 100, (WINDOW_DIMENSIONS[1] / 2) + 100);
            world.createJoint(Joints.ropeJoint(box1, box2));
            isTestUp = true;
        }

        if (!isTestUp && Keyboard.isKeyDown(Keyboard.KEY_3)) {
            GameObject box1 = ObjectFactory.createBox(1.0f, 1.0f, WINDOW_DIMENSIONS[0] / 2, WINDOW_DIMENSIONS[1] / 2);
            world.createJoint(Joints.revoluteJoint(box1, ground));
            isTestUp = true;
        }

        if (!isTestUp && Keyboard.isKeyDown(Keyboard.KEY_4)) {
            GameObject box1 = ObjectFactory.createBox(1.0f, 1.0f, WINDOW_DIMENSIONS[0] / 2, WINDOW_DIMENSIONS[1] / 2);
            world.createJoint(Joints.prismaticJoint(box1, ground));
            isTestUp = true;
        }

        if (!isTestUp && Keyboard.isKeyDown(Keyboard.KEY_5)) {
            GameObject box1 = ObjectFactory.createBox(1.0f, 1.0f, WINDOW_DIMENSIONS[0] / 2, WINDOW_DIMENSIONS[1] / 2);
            GameObject box2 = ObjectFactory.createBox(1.0f, 1.0f, (WINDOW_DIMENSIONS[0] / 2) + 100, (WINDOW_DIMENSIONS[1] / 2) + 100);
            world.createJoint(Joints.pulleyJoint(box1, box2, physicScale));
            isTestUp = true;
        }

        if (!isTestUp && Keyboard.isKeyDown(Keyboard.KEY_6)) {
            GameObject box1 = ObjectFactory.createBox(1.0f, 1.0f, WINDOW_DIMENSIONS[0] / 2, WINDOW_DIMENSIONS[1] / 2);
            GameObject box2 = ObjectFactory.createBox(1.0f, 1.0f, (WINDOW_DIMENSIONS[0] / 2) + 30, (WINDOW_DIMENSIONS[1] / 2) + 30);
            world.createJoint(Joints.weldJoint(box1, box2, physicScale));
            isTestUp = true;
        }

        if (!isTestUp && Keyboard.isKeyDown(Keyboard.KEY_7)) {
            RagdollTest ragdollTest = new RagdollTest();
            ragdollTest.initiateTest();
            isTestUp = true;
        }

        if (isTestUp && Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
            setUpNewWorld();
            isTestUp = false;
        }

        for (GameObject gameObject : bodies) {
            if (gameObject.getBody().getType() == BodyType.DYNAMIC) {
                if (Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
                    gameObject.getBody().applyForceToCenter(new Vec2(-5, 0));
                } else if (Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A)) {
                    gameObject.getBody().applyForceToCenter(new Vec2(5, 0));
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
                    gameObject.getBody().applyForceToCenter(new Vec2(0, 5));
                } else if (Keyboard.isKeyDown(Keyboard.KEY_S) && !Keyboard.isKeyDown(Keyboard.KEY_W)) {
                    gameObject.getBody().applyForceToCenter(new Vec2(0, -5));
                }
            }
        }
    }

    private static void update() {
        Display.update();
        Display.sync(60);

        updateMouseWorld();
        mouseDrag();
    }

    // Mouse Test
    private static void updateMouseWorld() {
        mouseXWorld = Mouse.getX();
        mouseYWorld = Mouse.getY();

        mouseXWorldPhys = mouseXWorld / physicScale;
        mouseYWorldPhys = mouseYWorld / physicScale;
    }

    private static void mouseDrag() {
        if (Mouse.isButtonDown(0)) {
            if (mouseJoint == null) {
                Body body = getBodyAtMouse();

                if (body != null) {
                    MouseJointDef def = new MouseJointDef();

                    def.bodyA = mouseAnchorBody;
                    def.bodyB = body;
                    def.target.set(mouseXWorldPhys, mouseYWorldPhys);
                    def.collideConnected = true;
                    def.maxForce = (300.0f * body.getMass());
                    mouseJoint = (MouseJoint) world.createJoint(def);
                    body.setAwake(true);
                }
            } else {
                mouseJoint.setTarget(new Vec2(mouseXWorldPhys, mouseYWorldPhys));
            }
        } else if (!Mouse.isButtonDown(0) && mouseJoint != null) {
            world.destroyJoint(mouseJoint);
            mouseJoint = null;
        }
    }

    private static Body getBodyAtMouse() {
        mousePVec.set(mouseXWorldPhys, mouseYWorldPhys);
        AABB aabb = new AABB();
        aabb.lowerBound.set(mouseXWorldPhys - 0.000000000001f, mouseYWorldPhys - 0.000000000001f);
        aabb.upperBound.set(mouseXWorldPhys + 0.000000000001f, mouseYWorldPhys + 0.000000000001f);
        BodyCallback bodyCallback = new BodyCallback(mousePVec);
        world.queryAABB(bodyCallback, aabb);

        Body body = bodyCallback.getBody();
        return body;
    }
}
