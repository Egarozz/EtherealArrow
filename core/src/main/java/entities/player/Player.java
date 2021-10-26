package entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;

import behaviors.player.DashMovement;
import behaviors.player.DoubleJump;
import behaviors.player.HorizontalMovement;
import behaviors.player.MovementBehavior;
import entities.AABB;
import entities.Entity;
import entities.arrow.Arrow;
import entities.enemies.Enemy;

import com.dongbat.jbump.World;
import com.payne.games.piemenu.PieMenu;

import main.FirstScreen;
import space.earlygrey.shapedrawer.ShapeDrawer;
import utils.ArrowFactory;
import utils.Arrows;
import utils.Assets;
import utils.GlobalVariables;
import utils.SpriteAnimation;
import utils.Utils;
import static utils.GlobalVariables.worldTime;
public class Player extends Entity{
	
	CollisionFilter filter;
	
	public float FRICTION = 550f;
	public float RUN_ACCELERATION = 1200f;
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
	
	Sprite arrow;
	Vector2 arrowDirection;
	FirstScreen fs;
	
	Array<Arrow> arrows;
	ArrowFactory arrowFactory;
	
	boolean canshoot = true;
	float shootTimer = 0;
	private PieMenu menu;
	private boolean blockShoot = false;
	
	private Arrows currentArrow;
	public Player(Vector2 position, World<Entity> world, FirstScreen fs) {
		super(position, new AABB(new Vector2(0,0), new Vector2(10,18)), world);
		this.fs = fs;
		arrowDirection = new Vector2();
		gravityY = -GRAVITY;
		
		
		filter = (item, other) -> {
			if(other.userData instanceof map.Rect || other.userData instanceof Enemy) {
				return Response.slide;
			}
			return null;
		};
		behaviors = new Array<>();
		behaviors.add(new HorizontalMovement(this));
		behaviors.add(new DoubleJump(this));
		behaviors.add(new DashMovement(this));
		
		loadAnimations();
		current = idle;
		
		arrow = new Sprite(new Texture("Bow.png"));
		arrow.setPosition(position.x, position.y);
		
		arrows = new Array<>();
		arrowFactory = new ArrowFactory(fs);
		
		
		PieMenu.PieMenuStyle style = new PieMenu.PieMenuStyle();
		 style.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("rael_pie.png")));
		 style.selectedColor = new Color(1,.5f,.5f,.5f);
		 menu = new PieMenu(new TextureRegion(new Texture("Pixel.png")), style, 80, 24f/80, 30) {
	            /* Since we are using Images, we want to resize them to fit within each sector. */
	            @Override
	            public void modifyActor(Actor actor, float degreesPerChild, float actorDistanceFromCenter) {
	                float size = getEstimatedRadiusAt(degreesPerChild, actorDistanceFromCenter);
	                size *= 1.26f; // adjusting the returned value to our likes
	                actor.setSize(size, size);
	            }
	            
	        };
	        menu.setInfiniteSelectionRange(true);
	        menu.addListener(new ChangeListener() {
	            @Override
	            public void changed(ChangeEvent event, Actor actor) {
	            	if(menu.getSelectedIndex() == 2) {
	            		currentArrow = Arrows.Explosive;
	            	}
	            	if(menu.getSelectedIndex() == 0) {
	            		currentArrow = Arrows.Normal;
	            	}
	            	if(menu.getSelectedIndex() == 3) {
	            		currentArrow = Arrows.Teleport;
	            	}
	            	if(menu.getSelectedIndex() == 1) {
	            		currentArrow = Arrows.Piercing;
	            	}
	            	System.out.println("ChangeListener - newly selected index: " + menu.getSelectedIndex());
	            	blockShoot = false;
	            	canshoot = true;
					GlobalVariables.worldTime = 1f;
					GlobalVariables.blockTime = false;
	            	menu.setVisible(false);
	                menu.remove();
	            }
	        });
	        Array<Image> imgs = new Array<>();
	        imgs.add(new Image(new Texture(Gdx.files.internal("NormalArrow.png"))));
	        imgs.add(new Image(new Texture(Gdx.files.internal("PiercingArrow.png"))));
	        imgs.add(new Image(new Texture(Gdx.files.internal("ExplosionArrow.png"))));
	        imgs.add(new Image(new Texture(Gdx.files.internal("TeleportArrow.png"))));
	        imgs.add(new Image());
	        imgs.add(new Image());
	        
	        for (int i = 0; i < imgs.size; i++)
	            menu.addActor(imgs.get(i));
	            
	        
	        currentArrow = Arrows.Normal;
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
	
	@Override
	public void update(float delta) {
		
		
		
		if(!canshoot && !blockShoot) {
			shootTimer += delta/GlobalVariables.worldTime;
			if(shootTimer > 0.5f) {
				canshoot = true;
				shootTimer = 0;
			}
		}
		
		
		for(MovementBehavior m: behaviors) m.update(delta);
		
		if(Gdx.input.isKeyJustPressed(Keys.A)) {
			
			currentLeft++;
		}
		if(Gdx.input.isKeyJustPressed(Keys.D)) {
			
			currentRight++;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
				
			
				for(MovementBehavior m: behaviors) m.left(delta);
				
				
				
			}else if(Gdx.input.isKeyPressed(Keys.D)) {
				
				
				for(MovementBehavior m: behaviors) m.right(delta);
			
				
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
			
			if(Gdx.input.isButtonPressed(Buttons.LEFT) && canshoot) {
				arrows.add(arrowFactory.getArrow(currentArrow, position, arrowDirection));
				canshoot = false;
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
			

			arrow.setPosition(position.x-20, position.y-15);	
			if((arrowDirection.angleDeg() > 0 && arrowDirection.angleDeg() < 86) || 
					(arrowDirection.angleDeg() > 276 && arrowDirection.angleDeg() < 360)) {
				if(left) {
					running.flip();
					idle.flip();
					left = false;
					
				}
			}else if(arrowDirection.angleDeg() > 90 && arrowDirection.angleDeg() < 270){
				if(!left) {
					running.flip();
					idle.flip();
					left = true;
					
				}
			}		
				
			
				
			arrow.setOrigin(21, 16);
			arrow.setCenter(arrow.getX()+arrow.getWidth()/2, arrow.getY() + arrow.getHeight()/2);
			arrowDirection.set(fs.mousePos.x - (arrow.getX() + arrow.getWidth()/2), fs.mousePos.y - (arrow.getY()+arrow.getHeight()/2));
			arrow.setRotation(arrowDirection.angleDeg()+180);
			
			current.update(delta);
			
			for(int i = arrows.size-1; i >=0; i--) {
				Arrow a = arrows.get(i);
				
				a.update(delta);
				if(a.isDestroy()) {
					
					arrows.removeIndex(i);
					world.remove(a.getItem());
					arrowFactory.freeArrow(a);
					
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Keys.C)) {
				canshoot = false;
				blockShoot = true;
				GlobalVariables.worldTime = 0.05f;
				GlobalVariables.blockTime = true;
				fs.stage.addActor(menu);
	            menu.centerOnMouse();
	            menu.resetSelection();
	            menu.setVisible(true);
			}
			
	}
	
	@Override
	public void render(ShapeDrawer drawer, SpriteBatch batch) {
		drawer.rectangle(bounds.getMin().x, bounds.getMin().y, bounds.getSize().x, bounds.getSize().y, Color.GREEN);
		for(MovementBehavior m: behaviors) m.renderEffect(batch);
		current.render(batch);
		arrow.draw(batch);
		
		for(Arrow a: arrows) {
			a.render(drawer,batch);
		}
	}
	

}
