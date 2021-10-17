package entities;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.World;

public abstract class Entity {

	protected Vector2 position;
	protected AABB bounds;
	protected World<Entity> world;
	
	public Entity(Vector2 position, AABB bounds, World<Entity> world) {
		this.position = position;
		this.bounds = bounds;
		
		this.world = world;
		bounds.setEntity(this);
	}
	

	public AABB getBounds() {
		return bounds;
	}


	public Vector2 getPosition() {
		return position;
	}
	
	
}
