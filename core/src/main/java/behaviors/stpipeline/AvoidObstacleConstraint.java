package behaviors.stpipeline;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;

import entities.Entity;
import entities.arrow.Arrow;
import entities.player.Player;

public class AvoidObstacleConstraint implements Constraints{
	
	World<Entity> world;
	Collisions col;
	Entity character;
	CollisionFilter filter;
	float margin = 5;
	float radius = 10;
	Vector2 problemPoint;
	Vector2 normal;
	
	Vector2 temp;
	Vector2 minPosition;
	Goal previusGoal;
	
	float minDistance = 9999;
	public AvoidObstacleConstraint(Entity character, World<Entity> world) {
		this.world = world;
		this.character = character;
		problemPoint = new Vector2();
		normal = new Vector2();
		temp = new Vector2();
		minPosition = new Vector2();
		filter = (item, other) -> {
			if(other.userData instanceof Player || other.userData instanceof Arrow) {
				return null;
			}
			return Response.touch;
		};
		
		
		col = new Collisions();
	}
	@Override
	public boolean willViolate(Goal goal) {
		col.clear();
		world.project(character.getItem(), character.getPosition().x, 
				character.getPosition().y, 1, 1, goal.position.x, goal.position.y, filter, col);
		
		if(!col.isEmpty()) {
			Collision c = col.get(0);
			Vector2 touch = new Vector2(c.touch.x, c.touch.y);
			problemPoint.set(touch);
			normal.set(c.normal.x, c.normal.y);
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public Goal suggest(Goal goal) {
		
		
		minDistance = 99999;
		if(normal.x == -1) {
			temp.set(problemPoint.x - radius, problemPoint.y);
			updateMin(goal, temp);
			
			temp.set(problemPoint.x - radius, problemPoint.y + radius);
			updateMin(goal, temp);
			
			temp.set(problemPoint.x - radius, problemPoint.y - radius);
			updateMin(goal, temp);
		}
		
		if(normal.x == 1) {
			temp.set(problemPoint.x + radius, problemPoint.y);
			updateMin(goal, temp);
			
			temp.set(problemPoint.x + radius, problemPoint.y + radius);
			updateMin(goal, temp);
			
			
			temp.set(problemPoint.x + radius, problemPoint.y - radius);
			updateMin(goal, temp);
		}
		
		if(normal.y == 1) {
			temp.set(problemPoint.x, problemPoint.y + radius);
			updateMin(goal, temp);
			
			temp.set(problemPoint.x - radius, problemPoint.y + radius);
			updateMin(goal, temp);
			
			temp.set(problemPoint.x + radius, problemPoint.y + radius);
			updateMin(goal, temp);
		}
		if(normal.y == -1) {
			temp.set(problemPoint.x, problemPoint.y - radius);
			updateMin(goal, temp);
			
			temp.set(problemPoint.x - radius, problemPoint.y - radius);
			updateMin(goal, temp);
			
			temp.set(problemPoint.x + radius, problemPoint.y - radius);
			updateMin(goal, temp);
		}
		
		//System.out.println(minPosition);
		goal.position.set(minPosition);
		
		return goal;
	}
	
	public void updateMin(Goal goal, Vector2 problem) {
		
		col.clear();
		col = world.project(character.getItem(), character.getPosition().x, 
				character.getPosition().y, 1, 1, problem.x, problem.y, filter, col);
		if(col.isEmpty()) {
			float dst = temp.dst(goal.position);
			if(dst< minDistance) {
				minDistance = dst;
				minPosition.set(temp);
			}
		}
	}

}
