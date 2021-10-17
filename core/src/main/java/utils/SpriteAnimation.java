package utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SpriteAnimation {
	
	private float state;
	private Animation<Sprite> animation;
	private Vector2 position;
	private Sprite current;
	public SpriteAnimation(Vector2 position, Array<Sprite> frames, float vel) {
		this.position = position;
		
		animation = new Animation<Sprite>(vel, frames, PlayMode.LOOP);
		
		current = animation.getKeyFrame(0);
		updatePosition();
	}
	
	public void flip() {
		for(Object a: animation.getKeyFrames()) {
			Sprite s = (Sprite) a;
			s.flip(true, false);
		}
	}
	public void updatePosition() {
		for(Object a: animation.getKeyFrames()) {
			Sprite s = (Sprite) a;
			s.setPosition(position.x-s.getWidth()/2, position.y-s.getHeight()/2);
		}
	}
	
	
	
	public void update(float delta) {
		state += delta;
		updatePosition();
		current = animation.getKeyFrame(state);
	}
	public void render(SpriteBatch batch) {
		current.draw(batch);
	}
}
