package utils;

import com.badlogic.gdx.math.Vector2;

public class Utils {
	public static float approach(float start, float target, float increment) {
		increment = Math.abs(increment);
		if (start < target) {
		start += increment;
			            if (start > target) {
			                start = target;
			            }
			        } else {
			            start -= increment;
			            
			            if (start < target) {
			                start = target;
			            }
			        }
			        return start;
		}
	
	public static void rotate (Vector2 vec, float angleDeg, Vector2 origin) {
		//lo llevamos al eje de coordenadas para hacer los calculos
		float x = vec.x - origin.x;
		float y = vec.y - origin.y;

		float cos = (float) Math.cos(Math.toRadians(angleDeg));
		float sin = (float) Math.sin(Math.toRadians(angleDeg));

		//En teoria de point on Box2D se explica mejor esta formula
		float xprime = (x*cos) - (y*sin);
		float yprime = (x * sin) + (y*cos);
		//Se devuelve al punto de origen
		xprime += origin.x;
		yprime += origin.y;
		//Se rota el punto
		vec.x = xprime;
		vec.y = yprime;
	}

	

}
