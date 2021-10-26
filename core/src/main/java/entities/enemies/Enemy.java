package entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

import behaviors.enemy.EnemyBehavior;
import behaviors.enemy.SteeringBehavior;
import entities.AABB;
import entities.Entity;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.GlobalVariables;

public abstract class Enemy extends Entity{
	
	protected Sprite sprite;
	protected EnemyBehavior behavior;
	protected Vector2 direction;	
	
	
	public Enemy(Vector2 position, Sprite sprite, AABB bounds, World<Entity> world) {
		super(position, bounds, world);
		this.sprite = sprite;
		this.sprite.setPosition(position.x -sprite.getWidth()/2, position.y-sprite.getHeight()/2);
		direction = new Vector2();
	}
	
	
	public void setBehavior(EnemyBehavior behavior) {
		this.behavior = behavior;
	}

	public void update(float delta) {
		
		//behavior.update(delta);
		this.sprite.setPosition(position.x -sprite.getWidth()/2, position.y-sprite.getHeight()/2);
		
	}
	public Vector2 getPosition() {
		return position;
	}
	@Override
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		sprite.draw(batch);
		//drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
		//drawer.line(position, direction, Color.RED);
		
	}
	public void flip() {
		sprite.flip(true, false);
	}


	public EnemyBehavior getBehavior() {
		return behavior;
	}


	@Override
	public void hit(Vector2 direction) {
		((SteeringBehavior) this.behavior).getVel().add(direction);
	}


	public Sprite getSprite() {
		return sprite;
	}
	
	
	
	
}
