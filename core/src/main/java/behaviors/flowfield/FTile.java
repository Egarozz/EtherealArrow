package behaviors.flowfield;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class FTile {
	
	private Vector2 position;
	private Vector2 direction;
	private Vector2 center;
	
	public FTile(Vector2 position) {
		this.position = position;
		direction = new Vector2();
		center = new Vector2(position.x + 16, position.y + 16);
	}
	
	public void renderTile(ShapeDrawer drawer) {
		//drawer.rectangle(position.x, position.y, 32, 32, Color.YELLOW);
		//drawer.filledCircle(center, 1, Color.CYAN);
	}
	
}
