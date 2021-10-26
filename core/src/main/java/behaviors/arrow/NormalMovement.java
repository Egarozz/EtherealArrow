package behaviors.arrow;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;

import entities.Entity;
import entities.arrow.Arrow;
import entities.enemies.Enemy;
import utils.GlobalVariables;

public class NormalMovement implements ArrowBehavior{
	
	private Arrow arrow;
	private CollisionFilter filter;
	private boolean collide = false;
	private int maxPiercing;
	private ObjectSet<Entity> penetrated;
	private Entity e;
	Float differenceX = null;
	Float differenceY = null;
	public NormalMovement(Arrow arrow, int maxPiercing) {
		this.arrow = arrow;
		this.maxPiercing = maxPiercing;
		penetrated = new ObjectSet<>();
		filter = (item, other) -> {
			if(penetrated.size < maxPiercing) {
				if(other.userData instanceof Enemy) {
					Entity e1 = (Entity)other.userData;
					if(!penetrated.contains(e1)) return Response.cross;
				}		
			}else {
				if(other.userData instanceof Enemy) {
					Entity e2 = (Entity)other.userData;
					if(!penetrated.contains(e2)) {
						return Response.touch;
					}
					
				}
			}
			if(other.userData instanceof map.Rect) {
				return Response.touch;
			}
			return null;
		};
	}
	@SuppressWarnings("unchecked")
	@Override
	public void update(float delta) {
		
		if(!collide) {
			arrow.getSprite().setPosition(arrow.getPosition().x -arrow.getSprite().getWidth()/2, arrow.getPosition().y-arrow.getSprite().getHeight()/2);
			arrow.getSprite().setRotation(arrow.getDirection().angleDeg());
			arrow.getVelocity().set(arrow.getDirection());
			arrow.getVelocity().setLength(1000f).scl(delta);
			arrow.getPosition().add(arrow.getVelocity());
		}
		
		Result result = arrow.getWorld().move(arrow.getItem(),  arrow.getBounds().getMin().x, arrow.getBounds().getMin().y, filter);
		if(!collide) {
			Collisions col = result.projectedCollisions;
			
			for(Item<Entity> c: col.others) {
				this.e = c.userData;
				if(penetrated.size < maxPiercing) {
					penetrated.add(e);
				}
				if(!penetrated.contains(e) || c.userData instanceof map.Rect) {
					collide = true;
					arrow.setCollide(true);

				}
			}		
			
			
			arrow.collision(col);
		}	
		
		
		Rect rect = arrow.getWorld().getRect(arrow.getItem());
		if(rect!= null && !collide) {
			arrow.getPosition().set(rect.x + arrow.getBounds().getSize().x/2, rect.y + arrow.getBounds().getSize().y/2);
		}
		if(rect!= null && collide && e!=null) {
			if(differenceX == null && differenceY == null) {
				differenceX = rect.x - e.getBounds().getMin().x;
				differenceY = rect.y - e.getBounds().getMin().y;
			}
			arrow.getPosition().set(e.getBounds().getMin().x + differenceX, e.getBounds().getMin().y+differenceY);
		}
		
		arrow.getSprite().setPosition(arrow.getPosition().x -arrow.getSprite().getWidth()/2, arrow.getPosition().y-arrow.getSprite().getHeight()/2);
		arrow.getSprite().setRotation(arrow.getVelocity().angleDeg());
		
	}
	@Override
	public void reset() {
		penetrated.clear();
		collide = false;
		arrow.getVelocity().set(0,0);
		e = null;
		differenceX = null;
		differenceY = null;
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
