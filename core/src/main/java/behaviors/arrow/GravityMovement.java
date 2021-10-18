package behaviors.arrow;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;

import entities.Arrow;
import entities.Entity;
import utils.GlobalVariables;

public class GravityMovement implements ArrowBehavior{
	private Arrow arrow;
	private CollisionFilter filter;
	private boolean collide = false;
	private Vector2 gravity;
	private int maxPiercing = 2;
	private Array<Entity> penetrated;
	
	public GravityMovement(Arrow arrow) {
		this.arrow = arrow;
		penetrated = new Array<>();
		filter = new CollisionFilter() {

			@Override
			public Response filter(Item item, Item other) {
				if(penetrated.size < maxPiercing) {
					if(other.userData instanceof main.Rect) {
						Entity e = (Entity)other.userData;
						if(!penetrated.contains(e, false)) {
							return Response.cross;
						}	
						
					}
				}else {
					if(other.userData instanceof main.Rect) {
						Entity e = (Entity)other.userData;
						if(!penetrated.contains(e, false)) {
							return Response.touch;
						}
						
					}
				}
				return null;
			}
		};
				
		
		gravity = new Vector2();
		
	}
	@Override
	public void update(float delta) {
		
		
		arrow.getSprite().setPosition(arrow.getPosition().x -arrow.getSprite().getWidth()/2, arrow.getPosition().y-arrow.getSprite().getHeight()/2);
		arrow.getSprite().setRotation(arrow.getVelocity().angleDeg());
		
		gravity.set(0,40).scl(delta);
		if(!collide) arrow.getDirection().sub(gravity);
		
		
		arrow.getVelocity().add(arrow.getDirection());
		
		arrow.getVelocity().setLength(1000f).scl(delta);
		arrow.getPosition().add(arrow.getVelocity());
		
		
		
		
		
		Result result = arrow.getWorld().move(arrow.getItem(),  arrow.getBounds().getMin().x, arrow.getBounds().getMin().y, filter);
		if(!collide) {
			Collisions col = result.projectedCollisions;
			
			for(Item c: col.others) {
				if(c.userData instanceof main.Rect) {
					Entity e = (Entity)c.userData;
					if(!penetrated.contains(e, false)) {
						penetrated.add(e);
					}
					if(penetrated.size >= maxPiercing) {
						collide = true;
					}
				}
			}
		}
		
		Rect rect = arrow.getWorld().getRect(arrow.getItem());
		if(rect!= null) {
			arrow.getPosition().set(rect.x + arrow.getBounds().getSize().x/2, rect.y + arrow.getBounds().getSize().y/2);
		}
	}
	
}
