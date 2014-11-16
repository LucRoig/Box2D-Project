package game;

import gameobjects.GameObject;
import gameobjects.ObjectFactory;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class RagdollTest {

    private final float physicScale = 30;

    public void initiateTest() {
        CircleShape circle;
        BodyDef bd = new BodyDef();
        RevoluteJointDef jd = new RevoluteJointDef();
        FixtureDef fixtureDef = new FixtureDef();

        // Add 5 ragdolls along the top
        float startX = 400;
        float startY = 300;

        // BODIES
        // set these to dynamic bodies
        bd.type = BodyType.DYNAMIC;

        // Head
        GameObject head = ObjectFactory.createBox(12.5f / physicScale, 12.5f / physicScale, startX, startY);

        //Vec2 firstVec = new Vec2((float)Math.random() * 100 - 50, (float)Math.random() * 100 - 50);
        //head.applyLinearImpulse(firstVec, new Vec2(head.getPosition().x, head.getPosition().y));
        // Torso1    
        GameObject torso1 = ObjectFactory.createBox(10 / physicScale, 15 / physicScale, startX, startY + 28);

        // Torso2
        GameObject torso2 = ObjectFactory.createBox(10 / physicScale, 15 / physicScale, startX, startY + 43);

        // Torso3
        GameObject torso3 = ObjectFactory.createBox(10 / physicScale, 15 / physicScale, startX, startY + 58);

        // UpperArm
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        // L
        GameObject leftUpperArm = ObjectFactory.createBox(6.5f / physicScale, 18 / physicScale, startX - 30, startY + 20);

        // R
        GameObject rightUpperArm = ObjectFactory.createBox(6.5f / physicScale, 18 / physicScale, startX + 30, startY + 20);

        // LowerArm
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        // L
        GameObject leftLowerArm = ObjectFactory.createBox(6 / physicScale, 17 / physicScale, startX - 57, startY + 20);

        // R
        GameObject rightLowerArm = ObjectFactory.createBox(6 / physicScale, 17 / physicScale, startX + 57, startY + 20);

        // UpperLeg
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        // L
        GameObject leftUpperLeg = ObjectFactory.createBox(22 / physicScale, 7.5f / physicScale, startX - 8, startY + 85);

        // R
        GameObject rightUpperLeg = ObjectFactory.createBox(22 / physicScale, 7.5f / physicScale, startX + 8, startY + 85);

        // LowerLeg
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        // L
        GameObject leftLowerLeg = ObjectFactory.createBox(20 / physicScale, 6 / physicScale, startX - 8, startY + 120);

        // R
        GameObject rightLowerLeg = ObjectFactory.createBox(20 / physicScale, 6 / physicScale, startX + 8, startY + 120);

        // JOINTS
        jd.enableLimit = true;

        // Head to shoulders
        jd.lowerAngle = -40 / (180 / (float) (float) Math.PI);
        jd.upperAngle = 40 / (180 / (float) (float) Math.PI);
        jd.initialize(torso1.getBody(), head.getBody(), new Vec2(startX / physicScale, (startY + 15) / physicScale));
        Game.world.createJoint(jd);

        // Upper arm to shoulders
        // L
        jd.lowerAngle = -85 / (180 / (float) Math.PI);
        jd.upperAngle = 130 / (180 / (float) Math.PI);
        jd.initialize(torso1.getBody(), leftUpperArm.getBody(), new Vec2((startX - 18) / physicScale, (startY + 20) / physicScale));
        Game.world.createJoint(jd);
        // R
        jd.lowerAngle = -130 / (180 / (float) Math.PI);
        jd.upperAngle = 85 / (180 / (float) Math.PI);
        jd.initialize(torso1.getBody(), rightUpperArm.getBody(), new Vec2((startX + 18) / physicScale, (startY + 20) / physicScale));
        Game.world.createJoint(jd);

        // Lower arm to upper arm
        // L
        jd.lowerAngle = -130 / (180 / (float) Math.PI);
        jd.upperAngle = 10 / (180 / (float) Math.PI);
        jd.initialize(leftUpperArm.getBody(), leftLowerArm.getBody(), new Vec2((startX - 45) / physicScale, (startY + 20) / physicScale));
        Game.world.createJoint(jd);
        // R
        jd.lowerAngle = -10 / (180 / (float) Math.PI);
        jd.upperAngle = 130 / (180 / (float) Math.PI);
        jd.initialize(rightUpperArm.getBody(), rightLowerArm.getBody(), new Vec2((startX + 45) / physicScale, (startY + 20) / physicScale));
        Game.world.createJoint(jd);

        // Shoulders/stomach
        jd.lowerAngle = -15 / (180 / (float) Math.PI);
        jd.upperAngle = 15 / (180 / (float) Math.PI);
        jd.initialize(torso1.getBody(), torso2.getBody(), new Vec2(startX / physicScale, (startY + 35) / physicScale));
        Game.world.createJoint(jd);
        // Stomach/hips
        jd.initialize(torso2.getBody(), torso3.getBody(), new Vec2(startX / physicScale, (startY + 50) / physicScale));
        Game.world.createJoint(jd);

        // Torso to upper leg
        // L
        jd.lowerAngle = -25 / (180 / (float) Math.PI);
        jd.upperAngle = 45 / (180 / (float) Math.PI);
        jd.initialize(torso3.getBody(), leftUpperLeg.getBody(), new Vec2((startX - 8) / physicScale, (startY + 72) / physicScale));
        Game.world.createJoint(jd);
        // R
        jd.lowerAngle = -45 / (180 / (float) Math.PI);
        jd.upperAngle = 25 / (180 / (float) Math.PI);
        jd.initialize(torso3.getBody(), rightUpperLeg.getBody(), new Vec2((startX + 8) / physicScale, (startY + 72) / physicScale));
        Game.world.createJoint(jd);

        // Upper leg to lower leg
        // L
        jd.lowerAngle = -25 / (180 / (float) Math.PI);
        jd.upperAngle = 115 / (180 / (float) Math.PI);
        jd.initialize(leftUpperLeg.getBody(), leftLowerLeg.getBody(), new Vec2((startX - 8) / physicScale, (startY + 105) / physicScale));
        Game.world.createJoint(jd);
        // R
        jd.lowerAngle = -115 / (180 / (float) Math.PI);
        jd.upperAngle = 25 / (180 / (float) Math.PI);
        jd.initialize(rightUpperLeg.getBody(), rightLowerLeg.getBody(), new Vec2((startX + 8) / physicScale, (startY + 105) / physicScale));
        Game.world.createJoint(jd);

    }
}
