package entities.enemies;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.dongbat.jbump.Response.Result;

import behaviors.enemy.SteeringBehavior;
import behaviors.stpipeline.SteeringPipeline;
import entities.AABB;
import entities.Entity;
import entities.arrow.Arrow;
import entities.player.Player;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.Assets;

public class EVoid extends Enemy{
	CollisionFilter filter;
	CollisionFilter rayFilter;
	
	Collisions collision1;
	Collisions collision2;
	Collisions collision3;
	
	Collisions col;
	Vector2 direction2;
	Vector2 direction3;
	Vector2 prueba;
	Player player;
	
	SteeringPipeline st;
	SteeringBehavior behavior;
	
	public EVoid(Vector2 position, Player player, World<Entity> world) {
		super(position, Assets.getInstance().getAtlas().createSprite("Enemy1"), new AABB(new Vector2(0,0), new Vector2(20,20)), world);
		this.player = player;
		behavior = new SteeringBehavior(this,player);
		setBehavior(behavior);
		
		filter = (item, other) -> {
			if(other.userData instanceof map.Rect || other.userData instanceof Player) {
				return Response.slide;
			}
			return null;
		};
		
		rayFilter = (item, other) -> {
			if(other.userData instanceof Player || other.userData instanceof Arrow) {
				return null;
			}
			return Response.touch;
		};
		
		
		collision1 = new Collisions();
		collision2 = new Collisions();
		collision3 = new Collisions();
		col = new Collisions();
		
		direction2 = new Vector2();
		direction3 = new Vector2();
		prueba = new Vector2();
		
		//st = new SteeringPipeline(behavior, this, player, world);
	}
	@Override
	public void update(float delta) {
		super.update(delta);
		behavior.update(delta);
		Result result = world.move(item, bounds.getMin().x, bounds.getMin().y, filter);
		
		Rect rect = world.getRect(item);
		if(rect != null) {
			position.set(rect.x + bounds.getSize().x/2, rect.y + bounds.getSize().y/2);
		}
	
		
		
		
		
		
	}
	@Override
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		super.render(drawer, batch);
//		drawer.line(position, direction2, Color.RED);
//		drawer.line(position, direction3, Color.RED);
		//drawer.filledCircle(prueba, 5, Color.CORAL);
		
	}
	
	
	
	
	
	
	
	

}
