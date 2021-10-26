package behaviors.stpipeline;

import entities.Entity;

public class ChaseTargeter implements Targeter{
	Entity chased;
	
	public ChaseTargeter(Entity chased) {
		this.chased = chased;
	}
	@Override
	public Goal getGoal() {
		Goal goal = new Goal();
		goal.position.set(chased.getPosition());
		goal.hasPosition = true;
		return goal;
	}	
	

}
