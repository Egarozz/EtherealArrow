package main;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import utils.Assets;

public class Tile {
	
	Vector2 position;
	Sprite sprite;
	
	
	public Tile(TextureRegion region, Vector2 position) {
		sprite = new Sprite(region);
		this.position = position;
		sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);
	}
	
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
