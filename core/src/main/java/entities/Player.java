package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.dongbat.jbump.World;

import behaviors.DashMovement;
import behaviors.DoubleJump;
import behaviors.HorizontalMovement;
import behaviors.MovementBehavior;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.Assets;
import utils.SpriteAnimation;
import utils.Utils;

public class Player extends Entity{
	Item<Entity> item;
	CollisionFilter filter;
	
	public float FRICTION = 550f;
	public float RUN_ACCELERATION = 1400f;
	public float RUN_SPEED = 200f;
	public float JUMP_SPEED = 400f;
	public float BOUNCE_SPEED = 800f;
	public float GRAVITY = 1000f;
	public float JUMP_MAX_TIME = .15f;
	
	public float deltaX;
	public float deltaY;
	public float gravityX;
	public float gravityY;
	public float jumpTime;
	public boolean jumping;

	private SpriteAnimation running;    
	private SpriteAnimation idle; 
	private SpriteAnimation current;
	
	public boolean left = true;
	public int currentJump = 0;
	public int currentLeft = 0;
	public int currentRight = 0;
	
	Array<MovementBehavior> behaviors;
	
	public Player(Vector2 position, AABB bounds, World<Entity> world) {
		super(position, bounds, world);
		gravityY = -GRAVITY;
		item = world.add(new Item<Entity>(this), bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y);
		
		filter = new CollisionFilter() {

			@Override
			public Response filter(Item item, Item other) {
				if(other.userData instanceof main.Rect) {
					return Response.slide;
				}
				return null;
			}
		};
		behaviors = new Array<>();
		behaviors.add(new HorizontalMovement(this));
		behaviors.add(new DoubleJump(this));
		behaviors.add(new DashMovement(this));
		
		loadAnimations();
		current = idle;
	}
	
	public void loadAnimations() {
		Array<Sprite> frames = Assets.getInstance().getAtlas().createSprites("Player");
		
		Array<Sprite> run = new Array<Sprite>();
		run.add(frames.get(6));
		run.add(frames.get(7));
		run.add(frames.get(8));
		run.add(frames.get(9));
		this.running = new SpriteAnimation(position, run, 0.1f);
		
		Array<Sprite> idle = new Array<>();
		idle.add(frames.get(0));
		idle.add(frames.get(1));
		idle.add(frames.get(2));
		idle.add(frames.get(3));
		idle.add(frames.get(4));
		idle.add(frames.get(5));
		this.idle = new SpriteAnimation(position, idle, 0.3f);
		
		
	}
	public void update(float delta) {
		for(MovementBehavior m: behaviors) m.update(delta);
		
		if(Gdx.input.isKeyJustPressed(Keys.A)) {
			
			currentLeft++;
		}
		if(Gdx.input.isKeyJustPressed(Keys.D)) {
			
			currentRight++;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
				
			
				for(MovementBehavior m: behaviors) m.left(delta);
				
				if(!left) {
					running.flip();
					idle.flip();
					left = true;
				}
				
			}else if(Gdx.input.isKeyPressed(Keys.D)) {
				
				
				for(MovementBehavior m: behaviors) m.right(delta);
				
				if(left) {
					running.flip();
					idle.flip();
					left = false;
				}
				
			}else {
				deltaX = Utils.approach(deltaX, 0f, RUN_ACCELERATION * delta);
				
			}
					
			if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				for(MovementBehavior m: behaviors) m.jump(delta);
			}
			
			if(deltaX != 0 && deltaY == 0) {
				current = running;
			}else {
				current = idle;
			}
			
			
			deltaX += delta * gravityX;
			deltaY += delta * gravityY;
			position.x += delta * deltaX;
			position.y += delta * deltaY;

			Result result = world.move(item, bounds.getMin().x, bounds.getMin().y, filter);
			for (int i = 0; i < result.projectedCollisions.size(); i++) {
	     		Collision collision = result.projectedCollisions.get(i);
	     		if(collision.normal.y == 1) {
	     			deltaY = 0;
	     			jumping = false;
	     			currentJump = 0;
	     		}
	     		if(collision.normal.y == -1) {
	     			deltaY = 0;
	     			jumping = false;
	     		  	currentJump = 3;
	     		}
	     	}
			
			Rect rect = world.getRect(item);
			if(rect != null) {
				position.set(rect.x + bounds.getSize().x/2, rect.y + bounds.getSize().y/2);
			}
			
			current.update(delta);
	}
	
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
		for(MovementBehavior m: behaviors) m.renderEffect(batch);
		current.render(batch);
	}
	

}
