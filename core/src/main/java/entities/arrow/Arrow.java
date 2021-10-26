package entities.arrow;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

import behaviors.arrow.ArrowBehavior;
import entities.AABB;
import entities.Entity;
import main.FirstScreen;
import utils.Arrows;

public abstract class Arrow extends Entity implements Poolable{
	
	protected Item<Entity> item;
	protected Vector2 direction;
	protected Sprite sprite;
	protected Vector2 velocity;
	protected boolean isCollide = false;
	protected boolean destroy = false;
	private float time = 0;
	private float totalTime = 0;
	private ArrowBehavior behavior;
	private Arrows type;
	protected FirstScreen screen;
	public Arrow(Arrows type, Vector2 position, Vector2 direction, Sprite sprite, FirstScreen screen) {
		super(position.cpy(), new AABB(new Vector2(0,0), new Vector2(2,2)), screen.world);
		this.screen = screen;
		item = world.add(new Item<Entity>(this), bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y);
		this.direction = direction.cpy();
		this.sprite = sprite;
		
		this.sprite.setPosition(this.position.x - this.sprite.getWidth()/2, this.position.y-this.sprite.getHeight()/2);
		this.sprite.setRotation(this.direction.angleDeg());
		this.velocity = new Vector2();
		this.type = type;
	}
	
	public void update(float delta) {
		behavior.update(delta);
		
		if(!isDestroy() && isCollide) {
			time += delta;
			if(time >= 2) {
				destroy = true;
			}
		}
		totalTime += delta;
		if(totalTime > 10) {
			destroy = true;
		}
	}
	public abstract void collision(Collisions col);
	
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

	@Override
	public void reset() {
		isCollide = false;
		destroy = false;
		position.set(0,0);
		time = 0;
		totalTime = 0;
		behavior.reset();
	}
	
	public Arrows getType() {
		return this.type;
	}
	public void init(Vector2 position, Vector2 direction) {
		this.position.set(position);
		this.direction.set(direction);
		behavior.init();
		
		this.velocity.set(0,0);
		item = world.add(new Item<Entity>(this), bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y);
		
	}

	public ArrowBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(ArrowBehavior behavior) {
		this.behavior = behavior;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public FirstScreen getScreen() {
		return screen;
	}



	
	
	

}
