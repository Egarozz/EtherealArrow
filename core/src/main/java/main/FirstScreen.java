package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dongbat.jbump.World;
import com.dongbat.jbump.util.MathUtils;

import entities.AABB;
import entities.Entity;
import entities.Player;
import utils.Assets;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
	App app;
	Map map;
	
	OrthographicCamera camera;
	Texture background;
	TextureRegion region;
	FillViewport viewport;
	World<Entity> world;
	Player player;
	
	Vector3 unprojected;
	public Vector3 mousePos;
	float camoffsetX = 0;
	float camoffsetY = 0;
	public FirstScreen(App app) {
		this.app = app;
		
		unprojected = new Vector3();
		mousePos = new Vector3();
		
		world = new World<>(32);
		
		camera = new OrthographicCamera();
		viewport = new FillViewport(320,240, camera);
		viewport.setScaling(Scaling.fill);
		viewport.apply();
		
		map = new Map("TileMap.tmx", new Vector2(-500,-1300), world);
		
		
		background = new Texture("Background.png");
		background.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		
		region = new TextureRegion(background,0,0,1000,background.getHeight());
		camera.position.x = 500;
		camera.position.y = 400;
		
		
		player = new Player(new Vector2(camera.position.x+100,camera.position.y-100), world, this);
		
		
		
	}
	@Override
	public void show() {
		// Prepare your screen here.
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		unprojected.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		mousePos.set(camera.unproject(unprojected));
		
		camera.update();
		player.update(delta);
		app.batch.setProjectionMatrix(camera.combined);
		
		map.render(app.batch, app.drawer, camera);
		
		app.batch.begin();
		app.batch.draw(background, (camera.position.x - 160)*0.95f, (camera.position.y-160)*0.98f);
		map.renderBounds(app.drawer, app.batch);
		app.batch.end();
		
		
		app.batch.begin();
		
		map.renderBuildings(app.batch);
		player.render(app.drawer, app.batch);
		app.batch.end();
		
		
		if(Gdx.input.getX() > Gdx.graphics.getWidth()-40) {
			
			camoffsetX = 80;
		}else {
			camoffsetX = 0;
		}
		camera.position.x += ((player.getPosition().x+camoffsetX) - camera.position.x)*0.1f;
		camera.position.y += (player.getPosition().y - camera.position.y)*0.1f;
		
		camera.position.x = MathUtils.clamp(camera.position.x, map.getMin()+144, map.getMax()-144);
		
		if(Gdx.input.isKeyPressed(Keys.G)) {
			camera.zoom -= 0.1f;
		}
		if(Gdx.input.isKeyPressed(Keys.H)) {
			camera.zoom += 0.1f;
		}
		
		Gdx.graphics.setTitle(String.valueOf(Gdx.graphics.getFramesPerSecond()));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// Invoked when your application is paused.
	}

	@Override
	public void resume() {
		// Invoked when your application is resumed after pause.
	}

	@Override
	public void hide() {
		// This method is called when another screen replaces this one.
	}

	@Override
	public void dispose() {
		// Destroy screen's assets here.
	}
}