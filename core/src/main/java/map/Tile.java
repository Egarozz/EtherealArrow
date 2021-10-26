package map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Tile {
	
	Vector2 position;
	Sprite sprite;
	OrthoCachedTiledMapRenderer r;
	
	public Tile(TextureRegion region, Vector2 position) {
		sprite = new Sprite(region);
		this.position = position;
		sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);
	}
	
	public void render(SpriteBatch batch, ShapeDrawer drawer) {
		sprite.draw(batch);
	}
}
