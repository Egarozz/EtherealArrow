package utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import entities.arrow.Arrow;
import entities.arrow.ExplosiveArrow;
import entities.arrow.NormalArrow;
import entities.arrow.PiercingArrow;
import entities.arrow.TeleportArrow;
import main.FirstScreen;

public class ArrowFactory {
	
	Pool<ExplosiveArrow> explosiveArrowPool;
	Pool<NormalArrow> normalArrowPool;
	Pool<TeleportArrow> teleportArrowPool;
	Pool<PiercingArrow> piercingArrowPool;
	
	public ArrowFactory(FirstScreen screen) {
		
		explosiveArrowPool = new Pool<ExplosiveArrow>(20) {
			@Override
			protected ExplosiveArrow newObject() {
				return new ExplosiveArrow(new Vector2(), new Vector2(), screen);
			}
		};	
		normalArrowPool = new Pool<NormalArrow>(20) {
			@Override
			protected NormalArrow newObject() {
				return new NormalArrow(new Vector2(), new Vector2(), screen);
			}
		};	
		teleportArrowPool = new Pool<TeleportArrow>(20) {
			@Override
			protected TeleportArrow newObject() {
				return new TeleportArrow(new Vector2(), new Vector2(), screen, screen.player);
			}
		};	
		piercingArrowPool = new Pool<PiercingArrow>(20) {
			@Override
			protected PiercingArrow newObject() {
				return new PiercingArrow(new Vector2(), new Vector2(), screen);
			}
		};	
		
	}
	
	public Arrow getArrow(Arrows arrow, Vector2 position, Vector2 direction) {
		
		if(arrow.equals(Arrows.Normal)) {
			NormalArrow a = normalArrowPool.obtain();
			a.init(position, direction);
			return a;
		}
		if(arrow.equals(Arrows.Explosive)) {
			ExplosiveArrow a = explosiveArrowPool.obtain();
			a.init(position, direction);
			return a;
		}
		if(arrow.equals(Arrows.Teleport)) {
			TeleportArrow a = teleportArrowPool.obtain();
			a.init(position, direction);
			return a;
		}
		if(arrow.equals(Arrows.Piercing)) {
			PiercingArrow a = piercingArrowPool.obtain();
			a.init(position, direction);
			return a;
		}
		
		return null;
	}
	
	public void freeArrow(Arrow arrow) {
		if(arrow.getType().equals(Arrows.Normal)) {
			normalArrowPool.free((NormalArrow)arrow);
		}
		if(arrow.getType().equals(Arrows.Explosive)) {
			explosiveArrowPool.free((ExplosiveArrow)arrow);
		}
		if(arrow.getType().equals(Arrows.Teleport)) {
			teleportArrowPool.free((TeleportArrow)arrow);
		}
		if(arrow.getType().equals(Arrows.Piercing)) {
			piercingArrowPool.free((PiercingArrow)arrow);
		}
	}
	
}
