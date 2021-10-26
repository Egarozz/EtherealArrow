package behaviors.stpipeline;

import com.badlogic.gdx.math.Vector2;

public interface Constraints {
	public boolean willViolate(Goal goal);
	public Goal suggest(Goal goal);
}
