package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.dongbat.jbump.World;

import behaviors.arrow.ArrowBehavior;
import behaviors.arrow.GravityMovement;
import behaviors.arrow.NormalMovement;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.GlobalVariables;
import utils.Utils;

public class NormalArrow extends Arrow{

	ArrowBehavior behavior;
	
	public NormalArrow(Vector2 position, Vector2 direction, World<Entity> world) {
		super(position.cpy(), direction.cpy(), new Sprite(new Texture("Arrow.png")), world);
		behavior = new GravityMovement(this);

	}	
	
	

	@Override
	public void update(float delta) {
		super.update(delta);
		behavior.update(delta);
		
		
		
	}



	@Override
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		sprite.draw(batch);
		drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
		
	}	
		
	

}
