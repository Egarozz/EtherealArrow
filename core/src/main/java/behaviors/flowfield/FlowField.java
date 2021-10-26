package behaviors.flowfield;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;

import space.earlygrey.shapedrawer.Drawing;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class FlowField {
	
	HashMap<String, FTile> tiles;
	OrthoCachedTiledMapRenderer r;
	public Drawing cached;
	public FlowField() {
		tiles = new HashMap<>();
		
	}
	
	public void putTile(String key, FTile tile) {
		
			tiles.put(key, tile);
		
	}
	
	public void render(ShapeDrawer drawer) {
		
		drawer.startRecording();
		tiles.forEach((s, tile)->tile.renderTile(drawer));
		cached = drawer.stopRecording();
	}
}
