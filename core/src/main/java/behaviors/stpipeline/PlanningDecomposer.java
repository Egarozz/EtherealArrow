package behaviors.stpipeline;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.World;

import behaviors.enemy.SteeringBehavior;
import entities.Entity;

public class PlanningDecomposer implements Decomposer{
	
	private SteeringBehavior behavior;
	private Vector2 projection;
	private Entity character;
	
	public PlanningDecomposer(Entity character, SteeringBehavior behavior) {
		this.behavior = behavior;
		this.character = character;
	}
	
	@Override
	public Goal decompose(Goal goal) {
		if(!goal.hasPosition) return goal;
		
		if(character.getPosition().dst(goal.position) < 5) return goal;
		
		projection.set(behavior.seekTarget(projection)).add(character.getPosition()).setLength2(60*60);
		
		goal.position.set(projection);
		return goal;
	}			
	

}
