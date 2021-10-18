package entities;

import com.badlogic.gdx.math.Vector2;

public class AABB {
	private Vector2 size = new Vector2();
	private Vector2 halfSize = new Vector2();
	private Vector2 position;
	private Vector2 offset;
	
	public AABB(Vector2 min, Vector2 max){
		this.size = max.cpy().sub(min);
		this.halfSize = size.cpy().scl(0.5f);
		this.position = new Vector2();
	}
	
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setOffset(float x, float y) {
		offset.set(x,y);
	}

	public Vector2 getMin() { 
		return this.position.cpy().sub(this.halfSize);
	}

	public Vector2 getMax() {
		return this.position.cpy().add(this.halfSize);
	}

	public Vector2 getSize() {
		return size;
	}

	

}
