package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

import utils.GlobalVariables;

public abstract class Arrow extends Entity{
	
	protected Item<Entity> item;
	protected Vector2 direction;
	protected Sprite sprite;
	protected Vector2 velocity;
	protected boolean isCollide = false;
	protected boolean destroy = false;
	private float time = 0;
	public Arrow(Vector2 position, Vector2 direction, Sprite sprite, World<Entity> world) {
		super(position.cpy(), new AABB(new Vector2(0,0), new Vector2(2,2)), world);
		item = world.add(new Item<Entity>(this), bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y);
		this.direction = direction.cpy();
		this.sprite = sprite;
		
		this.sprite.setPosition(this.position.x - this.sprite.getWidth()/2, this.position.y-this.sprite.getHeight()/2);
		this.sprite.setRotation(this.direction.angleDeg());
		this.velocity = new Vector2();
	}
	
	public void update(float delta) {
		if(!destroy) {
			time += delta;
			if(time >= 5) {
				destroy = true;
			}
		}
	}
	public Vector2 getDirection() {
		return direction;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Item<Entity> getItem() {
		return item;
	}

	public boolean isCollide() {
		return isCollide;
	}

	public void setCollide(boolean isCollide) {
		this.isCollide = isCollide;
	}
	
	
	

}
