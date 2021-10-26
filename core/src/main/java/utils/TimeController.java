package utils;

import com.badlogic.gdx.math.Interpolation;

public class TimeController {
	
	Interpolation interpol = Interpolation.pow3In;
	float progress = 0;
	float alpha = 0;
	float globalTime = 1;
	float map = 0;
	
	public TimeController(Interpolation in) {
		
	}
	
	public void update(float delta) {
		if(!GlobalVariables.blockTime) {
			globalTime += delta;
			progress = Math.min(1, globalTime/2);

			alpha = interpol.apply(progress);
			map = ((alpha-0)/(1-0f))*(1-0.1f)+0.1f;
			GlobalVariables.worldTime = map;
		}
	}		
			
			
		
	
		
		
	
	public void slow(float sl) {
		globalTime -= Math.max(0.1f, sl);
	}
	
	public void setSlow(float sl) {
		globalTime = Math.max(0.1f, sl);
	}
	
}
