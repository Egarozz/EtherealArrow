package main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

import entities.AABB;
import entities.Entity;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Rect extends Entity{
	
	
	
	World<Entity> world;
	Item<Entity> item;
	public Rect(Vector2 position, float width, float height, World<Entity> world) {
		super(position, new AABB(new Vector2(0,0), new Vector2(width, height)),world);
		item = world.add(new Item<Entity>(this), bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y);
	}
	
	public void render(ShapeDrawer drawer) {
		drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
	}

	public Vector2 getPosition() {
		return position;
	}
	
	
}
