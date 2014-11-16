package joints;

import gameobjects.GameObject;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.PulleyJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.RopeJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;

public class Joints {

    public static DistanceJointDef distanceJoint(GameObject object1, GameObject object2) {
        DistanceJointDef jointDef = new DistanceJointDef();
        
        jointDef.initialize(object1.getBody(), object2.getBody(), object1.getBody().getWorldCenter(), object2.getBody().getWorldCenter());
        
        return jointDef;
    }

    public static RopeJointDef ropeJoint(GameObject object1, GameObject object2) {
        RopeJointDef jointDef = new RopeJointDef();
        
        jointDef.bodyA = object1.getBody();
        jointDef.bodyB = object2.getBody();
        jointDef.maxLength = 6;
        jointDef.collideConnected = true;
        
        return jointDef;
    }

    public static RevoluteJointDef revoluteJoint(GameObject box1, GameObject box2){
        RevoluteJointDef jointDef = new RevoluteJointDef();
        
        jointDef.initialize(box1.getBody(), box2.getBody(), box1.getBody().getWorldCenter());
        jointDef.maxMotorTorque = 1.0f;
        jointDef.enableMotor = true;
        
        return jointDef;
    }

    public static PrismaticJointDef prismaticJoint(GameObject box1, GameObject box2) {
        PrismaticJointDef jointDef = new PrismaticJointDef();
        
        jointDef.initialize(box1.getBody(), box2.getBody(), box1.getBody().getWorldCenter(), new Vec2(1.0f, 0));
        
        return jointDef;
    }
    
    public static PulleyJointDef pulleyJoint(GameObject box1, GameObject box2, int PIXELS_TO_METRE) {
        PulleyJointDef jointDef = new PulleyJointDef();
        
        Vec2 box1Anchor = box1.getBody().getWorldCenter();
        Vec2 box2Anchor = box2.getBody().getWorldCenter();
        
        Vec2 groundAnchor1 = new Vec2(box1Anchor.x, box1Anchor.y-(300/PIXELS_TO_METRE));
        Vec2 groundAnchor2 = new Vec2(box2Anchor.x, box2Anchor.y-(300/PIXELS_TO_METRE));
        
        jointDef.initialize(box1.getBody(), box2.getBody(), groundAnchor1, groundAnchor2, box2Anchor, box2Anchor, 1f);
        
        jointDef.lengthA = 500/PIXELS_TO_METRE;
        jointDef.lengthB = 500/PIXELS_TO_METRE;
        
        return jointDef;
    }
    
    public static WeldJointDef weldJoint(GameObject box1, GameObject box2, int PIXELS_TO_METRE) {
        WeldJointDef jointDef = new WeldJointDef();
        
        Vec2 box1Anchor = box1.getBody().getWorldCenter();
        
        jointDef.initialize(box1.getBody(), box2.getBody(), box1Anchor);

        return jointDef;
    }
}
