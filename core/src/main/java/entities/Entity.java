package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity {

	protected Vector2 position;
	protected AABB bounds;
	protected World<Entity> world;
	protected Item<Entity> item;
	
	public Entity(Vector2 position, AABB bounds, World<Entity> world) {
		this.position = position;
		this.bounds = bounds;
		
		this.world = world;
		
		bounds.setPosition(position);
		item = world.add(new Item<Entity>(this), bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y);
	}
	
	public abstract void update(float delta);
	public abstract void render(ShapeDrawer drawer, SpriteBatch batch);
	
	public AABB getBounds() {
		return bounds;
	}


	public Item<Entity> getItem() {
		return item;
	}

	public Vector2 getPosition() {
		return position;
	}

	public World<Entity> getWorld() {
		return world;
	}

	public void hit(Vector2 direction) {
		
	}
	
	
	
}
