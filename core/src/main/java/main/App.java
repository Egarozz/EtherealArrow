package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import space.earlygrey.shapedrawer.ShapeDrawer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class App extends Game {
	public SpriteBatch batch;
	public ShapeDrawer drawer;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		drawer = new ShapeDrawer(batch,new TextureRegion(new Texture("Pixel.png")));
		setScreen(new FirstScreen(this));
	}
}