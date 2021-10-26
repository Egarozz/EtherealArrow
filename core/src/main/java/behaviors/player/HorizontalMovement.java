package behaviors.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.player.Player;
import utils.Utils;

public class HorizontalMovement implements MovementBehavior{
	private Player player;
	
	public HorizontalMovement(Player player) {
		this.player = player;
	}

	@Override
	public void left(float delta) {
		player.deltaX = Utils.approach(player.deltaX, -player.RUN_SPEED, player.RUN_ACCELERATION * delta);
	}

	@Override
	public void right(float delta) {
		player.deltaX = Utils.approach(player.deltaX, player.RUN_SPEED, player.RUN_ACCELERATION * delta);
		
	}

	@Override
	public void jump(float delta) {
		if(!player.jumping && player.currentJump == 0) {
			player.jumping = true;
			
			player.deltaY = player.JUMP_SPEED;
			
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
