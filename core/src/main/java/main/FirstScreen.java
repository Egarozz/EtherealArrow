package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.dongbat.jbump.World;
import com.dongbat.jbump.util.MathUtils;
import entities.Entity;
import entities.enemies.EVoid;
import entities.enemies.Enemy;
import entities.player.Player;
import map.Map;
import utils.GlobalVariables;
import utils.TimeController;


/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
	App app;
	Map map;
	
	OrthographicCamera camera;
	Texture background;
	TextureRegion region;
	FillViewport viewport;
	public World<Entity> world;
	public Player player;
	public EVoid enemy;
	
	Vector3 unprojected;
	public Vector3 mousePos;
	float camoffsetX = 0;
	float camoffsetY = 0;
	float time = 0;
	public TimeController tc;
	float delt = 1;
	public Stage stage;
	
	ParticleEffectPool explosionEffectPool;
	ParticleEffectPool hitEffectPool;
	TextureAtlas particleAtlas;
	
	Array<PooledEffect> effects;
	
	public FirstScreen(App app) {
		this.app = app;
		particleAtlas = new TextureAtlas("particles.atlas");
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
		enemy = new EVoid(new Vector2(camera.position.x + 10, camera.position.y-100), player, world);
		
		tc = new TimeController(Interpolation.sine);
		map.getFlowField().render(app.drawer);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		
		effects = new Array<>();
		ParticleEffect explosionEffect = new ParticleEffect();
		explosionEffect.load(Gdx.files.internal("expl.p"), particleAtlas);
		
		
		ParticleEffect hitEffect = new ParticleEffect();
		hitEffect.load(Gdx.files.internal("Part1.p"), particleAtlas);
		
		
		hitEffectPool = new ParticleEffectPool(hitEffect, 1, 2);
		explosionEffectPool = new ParticleEffectPool(explosionEffect, 1, 2);
	}
	@Override
	public void show() {
		// Prepare your screen here.
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		tc.update(delta);
		delt = delta*GlobalVariables.worldTime;
		
		unprojected.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		mousePos.set(camera.unproject(unprojected));
		
		camera.update();
		player.update(delt);
		enemy.update(delt);
		app.batch.setProjectionMatrix(camera.combined);
		
		
		
		app.batch.begin();
		app.batch.draw(background, (camera.position.x - 160)*0.95f, (camera.position.y-160)*0.98f);
		
		app.batch.end();
		
		
		app.batch.begin();
		map.render(app.batch, app.drawer, camera);
		map.renderBounds(app.drawer, app.batch);
		map.renderBuildings(app.batch);
		enemy.render(app.drawer,app.batch);
		//map.getFlowField().cached.draw();
		
		player.render(app.drawer, app.batch);
		
		for (int i = effects.size - 1; i >= 0; i--) {
			PooledEffect effect = effects.get(i);
			
			effect.draw(app.batch, delt);
			if (effect.isComplete()) {
				effect.free();
				effects.removeIndex(i);
			}
		}
		
		app.batch.end();
		
		//System.out.println(app.batch.renderCalls);
		
		
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
//		
//		if(GlobalVariables.worldTime < 1f) {
//			GlobalVariables.time+= delta;
//			float progress = Math.min(1, GlobalVariables.time/2);
//			
//			float alpha = interpolation.apply(progress);
//			float map = ((alpha-0)/(1-0f))*(1-0.1f)+0.1f;
//			GlobalVariables.worldTime = map;
//		}	
			
		
		
		stage.draw();
		
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
	
	public void createExplosionEffect(Vector2 position, float angle) {
		PooledEffect effect = explosionEffectPool.obtain();
		effect.setDuration(300);
		effect.getEmitters().get(0).getAngle().setHigh(angle-30, angle+30);
		effect.setPosition(position.x, position.y);
		effects.add(effect);
	}
	public void createHitEffect(Vector2 position, float angle) {
		PooledEffect effect = hitEffectPool.obtain();
		effect.setDuration(100);
		effect.getEmitters().get(0).getAngle().setHigh(angle-30, angle+30);
		effect.setPosition(position.x, position.y);
		effects.add(effect);
	}
}