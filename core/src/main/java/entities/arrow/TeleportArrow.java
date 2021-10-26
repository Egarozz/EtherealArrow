package entities.arrow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

import behaviors.arrow.NormalMovement;
import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;
import main.FirstScreen;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.Arrows;

public class TeleportArrow extends Arrow{
	
	
	private boolean activated = false;
	private Player player;
	public TeleportArrow(Vector2 position, Vector2 direction, FirstScreen sc, Player player) {
		super(Arrows.Teleport,position.cpy(), direction.cpy(), new Sprite(new Texture("Arrow.png")),sc);
		this.player = player;
		setBehavior(new NormalMovement(this,1));
	}

	@Override
	public void update(float delta) {
		super.update(delta);
				
		if(!activated && isCollide) {
			player.getPosition().set(position);
			activated = true;
		}
		
		
	}

	@Override
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		sprite.draw(batch);
		//drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
		
	}

	@Override
	public void collision(Collisions col) {
		for(Item<Entity> e: col.others) {
			if(e.userData instanceof Enemy) {
				Vector2 force = new Vector2();
				force.set(getDirection()).setLength(10000);;
				e.userData.hit(force);
				getScreen().tc.setSlow(0.1f);
			}	
		}
		
	}

	@Override
	public void reset() {
		super.reset();
		activated = false;
	}
	
	

}
