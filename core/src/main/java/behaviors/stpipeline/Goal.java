package behaviors.stpipeline;

import com.badlogic.gdx.math.Vector2;

public class Goal {
	public boolean hasPosition, hasOrientation, hasVelocity, hasRotation;
	
	public Vector2 position, velocity;
	public float orientation, rotation;
	
	public Goal() {
		position = new Vector2();
		velocity = new Vector2();
	}
	
	public void updateChannel(Goal o) {
		if(o.hasPosition) position.set(o.position);
		if(o.hasOrientation) orientation = o.orientation;
		if(o.hasVelocity) velocity.set(o.velocity);
		if(o.hasRotation) rotation = o.rotation;
	}
}
