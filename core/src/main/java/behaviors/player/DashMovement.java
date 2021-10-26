package behaviors.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import entities.player.Player;
import utils.Assets;
import utils.GlobalVariables;

public class DashMovement implements MovementBehavior{
	private float RENDER_INTERVAL = 0.02f;
	private Player player;
	float time = 0;
	boolean canDash = false;
	float delay = 0;
	float renderTime = 0;
	float currentInterval = 0;
	
	int currentIndex = 0;
	Sprite effect;
	Array<Sprite> dashEffect;
	Array<Sprite> renderable;
	
	public boolean isLeft = true;
	public DashMovement(Player player) {
		this.player = player;
		dashEffect = new Array<>();
		renderable = new Array<>();
		
		for(int i = 0; i < 20; i++) {
			dashEffect.add(Assets.getInstance().getAtlas().createSprites("Player").get(0));
		}
		
	}
	
	public void flipSprites() {
		for(Sprite s: dashEffect) {
			s.flip(true, false);
		}
	}
	@Override
	public void left(float delta) {
		
		
		if(player.currentLeft == 2 && canDash) {
			player.deltaX -= 500;
			player.currentLeft = 0;
			canDash= false;
			renderTime = 0.2f;
			currentIndex = 0;
			if(!isLeft) {
				flipSprites();
				isLeft = true;
			}
		}
		
		
	}

	@Override
	public void right(float delta) {
		if(player.currentRight == 2 && canDash) {
			player.deltaX += 500;
			player.currentRight = 0;
			canDash = false;
			renderTime = 0.2f;
			currentIndex = 0;
			if(isLeft) {
				flipSprites();
				isLeft = false;
			}
		}
		
		
	}

	@Override
	public void jump(float delta) {
		
	}

	@Override
	public void update(float delta) {
		if(player.currentLeft == 1 || player.currentRight == 1) {
			time+=delta/GlobalVariables.worldTime;
			if(time > 0.2f) {
				player.currentLeft = 0;
				player.currentRight = 0;
				time = 0;
			}
		}
			
		if(!canDash) {
			delay += delta/GlobalVariables.worldTime;
			if(delay > 2) {
				canDash = true;
				delay = 0;
			}
		}
		
		if(!canDash) {
			player.currentLeft = 0;
			player.currentRight = 0;
			
		}
		
		
		if(renderTime > 0) {
			renderTime-=delta;
			currentInterval+=delta;
			if(currentIndex < dashEffect.size && currentInterval >= RENDER_INTERVAL) {
				Sprite s = dashEffect.get(currentIndex);
				s.setPosition(player.getPosition().x -s.getWidth()/2, player.getPosition().y-s.getHeight()/2);
				renderable.add(s);
				currentIndex++;
				currentInterval = 0;
			}
			
			
			
		}
		if(renderTime < 0) {
			renderTime = 0;
		}
		
		if(!renderable.isEmpty()) {
			for(int i = renderable.size-1; i >= 0; i--) {
				Sprite s = renderable.get(i);
				if(s.getColor().a == 0) {
					s.setAlpha(1);
					renderable.removeIndex(i);
					
				}
			}
		}
	}

	@Override
	public void renderEffect(SpriteBatch batch) {
		if(!renderable.isEmpty()) {
			for(Sprite s: renderable) {
				if(s.getColor().a > 0) {
					s.setAlpha(s.getColor().a - 0.008f);
				}
				s.draw(batch);
			}
		}
		
	}


		
		
}
