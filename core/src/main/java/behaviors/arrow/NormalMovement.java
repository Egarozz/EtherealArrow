package behaviors.arrow;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;

import entities.Arrow;
import entities.Entity;
import utils.GlobalVariables;

public class NormalMovement implements ArrowBehavior{
	
	private Arrow arrow;
	private CollisionFilter filter;
	private boolean collide = false;
	public NormalMovement(Arrow arrow) {
		this.arrow = arrow;
		
		filter = new CollisionFilter() {

			@Override
			public Response filter(Item item, Item other) {
				if(other.userData instanceof main.Rect) {
					return Response.touch;
				}
				return null;
			}
		};	
	}
	@Override
	public void update(float delta) {
		float delt = delta*GlobalVariables.worldTime;
		arrow.getSprite().setPosition(arrow.getPosition().x -arrow.getSprite().getWidth()/2, arrow.getPosition().y-arrow.getSprite().getHeight()/2);
		arrow.getSprite().setRotation(arrow.getDirection().angleDeg());
		arrow.getVelocity().set(arrow.getDirection());
		arrow.getVelocity().setLength(1000f).scl(delt);
		arrow.getPosition().add(arrow.getVelocity());
		
		Result result = arrow.getWorld().move(arrow.getItem(),  arrow.getBounds().getMin().x, arrow.getBounds().getMin().y, filter);
		if(!collide) {
			Collisions col = result.projectedCollisions;
			
			for(Item c: col.others) {
				if(c.userData instanceof main.Rect) {
					arrow.setCollide(true);
					collide = true;
				}	
			}	
		}	
		
		Rect rect = arrow.getWorld().getRect(arrow.getItem());
		if(rect!= null) {
			arrow.getPosition().set(rect.x + arrow.getBounds().getSize().x/2, rect.y + arrow.getBounds().getSize().y/2);
		}
	}

}
