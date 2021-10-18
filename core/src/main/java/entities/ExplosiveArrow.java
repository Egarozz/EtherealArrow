package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.World;

import behaviors.arrow.ArrowBehavior;
import behaviors.arrow.GravityMovement;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.GlobalVariables;

public class ExplosiveArrow extends Arrow{
	ArrowBehavior behavior;
	float time = 0;
	public ExplosiveArrow(Vector2 position, Vector2 direction, World<Entity> world) {
		super(position.cpy(), direction.cpy(), new Sprite(new Texture("Arrow.png")), world);
		behavior = new GravityMovement(this);

	}	
	
	

	@Override
	public void update(float delta) {
		behavior.update(delta);
		
		if(!destroy) {
			time += delta;
			if(time >= 2) {
				destroy = true;
			}
		}
		if(this.destroy) {
			System.out.println("BOOOM");
		}
		
		
	}



	@Override
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		sprite.draw(batch);
		drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
		
	}	
}
