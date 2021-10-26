package behaviors.stpipeline;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.World;

import behaviors.enemy.SteeringBehavior;
import entities.Entity;

public class SteeringPipeline {
	Array<Targeter> targets;
	Array<Decomposer> decomposers;
	Array<Constraints> constraints;
	
	
	SteeringBehavior s;
	
	public SteeringPipeline(SteeringBehavior s, Entity character, Entity target, World<Entity> world) {
		this.s = s;
		AvoidObstacleConstraint a = new AvoidObstacleConstraint(character, world);
		ChaseTargeter c = new ChaseTargeter(target);
		PlanningDecomposer p = new PlanningDecomposer(character, s);
		
		targets = new Array<>();
		decomposers = new Array<>();
		constraints = new Array<>();
		
		targets.add(c);
		decomposers.add(p);
		constraints.add(a);
	}
	public Vector2 getSteering() {
		Goal goal = new Goal();
		
		for(Targeter t: targets) {
			goal.updateChannel(t.getGoal());
		}
		
		for(Decomposer d: decomposers) {
			goal = d.decompose(goal);
		}
		
		for(Constraints c: constraints) {
			if(c.willViolate(goal)) {
				goal = c.suggest(goal);
			}
		}
		
		return s.seekTarget(goal.position);
	}
	
}
