package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.World;

import behaviors.arrow.ArrowBehavior;
import behaviors.arrow.NormalMovement;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.GlobalVariables;

public class TeleportArrow extends Arrow{
	
	private ArrowBehavior behavior;
	private boolean activated = false;
	private Player player;
	public TeleportArrow(Vector2 position, Vector2 direction, World<Entity> world, Player player) {
		super(position.cpy(), direction.cpy(), new Sprite(new Texture("Arrow.png")), world);
		this.player = player;
		behavior = new NormalMovement(this);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		behavior.update(delta);
		
		if(!activated && isCollide) {
			player.position.set(position);
			activated = true;
		}
		
		
	}

	@Override
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		sprite.draw(batch);
		drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
		
	}

}
