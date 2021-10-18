package behaviors.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Player;

public class DoubleJump implements MovementBehavior{
	Player player;
	
	public DoubleJump(Player player) {
		this.player = player;
	}
	@Override
	public void left(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void right(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump(float delta) {
		
		if(player.currentJump < 2 && player.jumping) {
			player.deltaY = player.JUMP_SPEED;
			player.currentJump++;
		}
		
		
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void renderEffect(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}


}
