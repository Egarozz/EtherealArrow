package entities;

import com.badlogic.gdx.math.Vector2;

public class AABB {
	private Vector2 size = new Vector2();
	private Vector2 halfSize = new Vector2();
	private Entity entity = null;
	
	public AABB(Vector2 min, Vector2 max){
		this.size = max.cpy().sub(min);
		this.halfSize = size.cpy().scl(0.5f);
		
	}
	
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}


	public Vector2 getMin() { 
		return this.entity.getPosition().cpy().sub(this.halfSize);
	}

	public Vector2 getMax() {
		return this.entity.getPosition().cpy().add(this.halfSize);
	}

	public Vector2 getSize() {
		return size;
	}

	

}
