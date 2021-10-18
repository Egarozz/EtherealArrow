package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.World;

import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity {

	protected Vector2 position;
	protected AABB bounds;
	protected World<Entity> world;
	
	public Entity(Vector2 position, AABB bounds, World<Entity> world) {
		this.position = position;
		this.bounds = bounds;
		
		this.world = world;
		
		bounds.setPosition(position);
		
	}
	
	public abstract void update(float delta);
	public abstract void render(ShapeDrawer drawer, SpriteBatch batch);
	
	public AABB getBounds() {
		return bounds;
	}


	public Vector2 getPosition() {
		return position;
	}

	public World<Entity> getWorld() {
		return world;
	}


	
	
	
}
