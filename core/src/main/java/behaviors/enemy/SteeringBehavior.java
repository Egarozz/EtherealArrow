package behaviors.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;

public class SteeringBehavior implements EnemyBehavior{
	private Enemy enemy;
	private Vector2 acc, vel, desired, steering, scaledVel, scaledForce;
	
	private float orientation, angularVel, rotation;

	private float MAX_SPEED = 100;
	private float MAX_FORCE = 1000f;

	
	
	private Entity target;
	
	
	float targetRadius = 0.1f;
	float slowRadius = 0.3f;
	float maxRotation = 10f;
	float timeToTarget = 0.1f;
	float maxAngularAcc = 3f;

	
	public SteeringBehavior(Enemy enemy, Entity target) {
		this.enemy = enemy;
		this.target = target;
		this.acc = new Vector2();
		this.vel = new Vector2();
		this.desired = new Vector2();
		this.steering = new Vector2();
		this.scaledVel = new Vector2();
		this.scaledForce = new Vector2();
		
		
	}
	@Override
	public void update(float delta) {
		
		Vector2 seekForce = seek(delta);
		seekForce.scl(1f);
		
		applyForce(seekForce, delta);
		
	
		
		if(vel.len2() < MAX_SPEED*MAX_SPEED) {
			vel.add(acc);
		}else {
			vel.add(acc);
			vel.setLength2(MAX_SPEED*MAX_SPEED);
		}
		
		
		
		scaledVel.set(vel).scl(delta);
		face(target.getPosition(), delta);
		
		orientation += rotation;
		orientation += angularVel;
		
		//System.out.println(angularVel);
		enemy.getSprite().setRotation((float)Math.toDegrees(orientation));
		enemy.getPosition().add(scaledVel);
//		
		acc.scl(0);
		
		
		
		
	}
	
	public void avoid(Vector2 target, float delta) {
		seekTarget(target);
	}
	public void applyForce(Vector2 force, float delta) {
		scaledForce.set(force).scl(delta);
		acc.add(scaledForce);
	}
	
	public void push(Vector2 force) {
		vel.add(force);
	}
	public Vector2 seek(float delta) {
		desired.set(target.getPosition()).sub(enemy.getPosition());
		desired.setLength(MAX_SPEED*MAX_SPEED);
		steering.set(desired).sub(vel);
		if(steering.len2() < MAX_FORCE*MAX_FORCE) {
			return steering;
			
		}else {
			steering.setLength2(MAX_FORCE*MAX_FORCE);
			return steering;
			
		}
		
	}	
	public Vector2 seekTarget(Vector2 target) {
		desired.set(target).sub(enemy.getPosition());
		desired.setLength(MAX_SPEED*MAX_SPEED);
		steering.set(desired).sub(vel);
		if(steering.len2() < MAX_FORCE*MAX_FORCE) {
			
			return steering;
			
		}else {
			steering.setLength2(MAX_FORCE*MAX_FORCE);
			
			
			return steering;
			
		}
		
	}
	
	
	public void align(float tarOrientation, float delta) {
		float rotation = tarOrientation- orientation;
		rotation = mapToRange(rotation);
		float rotationSize = Math.abs(rotation);
		if(rotationSize < targetRadius) {
			angularVel = 0;
			return;
		}
		float targetRotation;
		if(rotationSize > slowRadius) {
			targetRotation = maxRotation;
		}else {
			targetRotation = maxRotation*rotationSize/slowRadius;
		}
		targetRotation *= rotation/rotationSize;
		this.angularVel = targetRotation - this.rotation;
		this.angularVel /= timeToTarget;
		
		float angularAcc = Math.abs(angularVel);
		if(angularAcc > maxAngularAcc) {
			this.angularVel /= angularAcc;
			this.angularVel*=maxAngularAcc;
		}
		
		this.angularVel = angularVel*delta;
		
	}
	public void face(Vector2 position, float delta) {
		Vector2 direction = new Vector2();
		direction.set(vel);
		if(direction.len2() == 0) return;
		align(direction.angleRad(), delta);
	}
	
	public Vector2 getVel() {
		return vel;
	}
	
	public float mapToRange(float rotation)
	{
		if (rotation > Math.PI)
			return rotation - 2 * (float)Math.PI;
		if (rotation < -Math.PI)
			return rotation + 2 * (float)Math.PI;
		return rotation;
	}
	

	
	
}
