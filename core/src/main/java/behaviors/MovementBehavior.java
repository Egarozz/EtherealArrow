package behaviors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface MovementBehavior {
	
	public void left(float delta);
	public void right(float delta);
	public void jump(float delta);
	public void update(float delta);
	public void renderEffect(SpriteBatch batch);
}
