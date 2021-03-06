package map;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.HashMap;

import utils.Assets;
import com.dongbat.jbump.World;

import behaviors.flowfield.FTile;
import behaviors.flowfield.FlowField;
import entities.Entity;
public class Map {
	
	
	TiledMap map;
	HashMap<String, Tile> tiles;
	Vector2 position;
	SpriteCache spriteCache;
	public int id;
	
	Array<Sprite> decoration;
	Array<Rect> bound;
	World<Entity> world;
	Array<Sprite> buildings;
	public int max = 0;
	public int min = 9999;
	
	FlowField flowfield;
	
	public Map(String route, Vector2 position, World<Entity> world) {
		map = Assets.getInstance().getTiledMap(route);
		this.world = world;
		tiles = new HashMap<>();
		this.position = position;
		
		spriteCache= new SpriteCache(8191, true);
		
		decoration = new Array<>();
		bound = new Array<>();
		buildings = new Array<>();
		flowfield = new FlowField();
		loadMap();
		
	}
	
	private void loadMap() {
		TiledMapTileLayer layerTiles = (TiledMapTileLayer) map.getLayers().get("Tiles");
		TiledMapTileLayer layerDecor = (TiledMapTileLayer) map.getLayers().get("Decor");
		TiledMapTileLayer layerBuildings = (TiledMapTileLayer) map.getLayers().get("Buildings");
		
		
		
		min = 99999;
		max = 0;
		for(int i = 0; i < layerTiles.getWidth(); i++) {
			for(int j = 0; j < layerTiles.getHeight(); j++) {
				
				Cell cellTile = layerTiles.getCell(i, j);
				Cell cellDecor = layerDecor.getCell(i, j);
				Cell cellBuilding = layerBuildings.getCell(i, j);
				if(cellTile == null) {
					flowfield.putTile(String.valueOf(i) + "," + String.valueOf(j), new FTile(new Vector2(position.x + 32*i -16,position.y+32*j -16)));
				}
				
				if(cellTile != null) {
					tiles.put(String.valueOf(i) + "," + String.valueOf(j), new Tile(cellTile.getTile().getTextureRegion(), 
							new Vector2(position.x + 32*i,position.y+32*j)));
					bound.add(new Rect(new Vector2(position.x + 32*i, position.y+32*j), 32, 32, world));
					if(i < min) min = i;
					if(i > max) max = i;
				}
				if(cellDecor != null) {
					Sprite decor = new Sprite(cellDecor.getTile().getTextureRegion());
					decor.setPosition(position.x + 32*i - 16, position.y+32*j -16);
					decoration.add(decor);
				}
				if(cellBuilding != null) {
					Sprite building = new Sprite(cellBuilding.getTile().getTextureRegion());
					building.setPosition(position.x + 32*i - 16, position.y+32*j -16);
					buildings.add(building);
				}
			}	
		}
		
		bound.add(new Rect(new Vector2(position.x+32*min-32, position.y),32,32*10000,world));
		bound.add(new Rect(new Vector2(position.x+32*max+32, position.y),32,32*10000,world));
		spriteCache.clear();
		spriteCache.beginCache();
		tiles.forEach((k,v) -> renderTile(v));
		for(Sprite s: decoration) {
			spriteCache.add(s.getTexture(),s.getVertices(),0,20);
		}
	    spriteCache.endCache();		
		
		System.out.println((decoration.size+tiles.size())*12);
		
		addBoundings();
	}
	
	public void addBoundings() {
		Array<Rect> correct = new Array<Rect>();
		
		for(Rect r: bound) {
			
			//izquierda
			if(!tiles.containsKey(String.valueOf((int)((r.getPosition().x-position.x)/32)) + "," + String.valueOf((int)((r.getPosition().y-position.y)/32) + 1))) {
				correct.add(r);
			}else if(!tiles.containsKey(String.valueOf((int)((r.getPosition().x-position.x)/32)) + "," + String.valueOf((int)((r.getPosition().y-position.y)/32) - 1))) {
			//izquierda
				correct.add(r);
			}else if(!tiles.containsKey(String.valueOf((int)((r.getPosition().x-position.x)/32)+1) + "," + String.valueOf((int)((r.getPosition().y-position.y)/32)))) {
			//izquierda
				correct.add(r);
			//izquierda
			}else if(!tiles.containsKey(String.valueOf((int)((r.getPosition().x-position.x)/32)-1) + "," + String.valueOf((int)((r.getPosition().y-position.y)/32)))) 
				correct.add(r);
			}
		for(Rect r: correct) {
			r.addToWorld();
		}
		
		bound = correct;
	}
	
	public void render(SpriteBatch batch, ShapeDrawer drawer, OrthographicCamera camera) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); 
		spriteCache.setProjectionMatrix(camera.combined);
		spriteCache.begin();
		spriteCache.draw(0);
		spriteCache.end();
		
//		Gdx.gl.glDisable(GL20.GL_BLEND);
//		for(Rect r: bound) {
//			r.render(drawer);
//		}
//		
	}
	
	public FlowField getFlowField() {
		return this.flowfield;
	}
	public void renderBounds(ShapeDrawer drawer, SpriteBatch batch) {
		for(Rect r: bound) {
			r.render(drawer, batch);
		}
	}
	public void renderBuildings(SpriteBatch batch) {
		for(Sprite s: buildings) {
			s.draw(batch);
		}
	}
	public float getMax() {
		return position.x + 32*max;
	}
	public float getMin() {
		return position.x + 32*min;
	}
	private void renderTile(Tile tile) {
		spriteCache.add(tile.sprite.getTexture(), tile.sprite.getVertices(),0, 20);
	}	
	
}		
	

