package utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Assets extends AssetManager{

private volatile static Assets assets;
	
	public static Assets getInstance() {
		if(assets == null) {
			synchronized(Assets.class) {
				if(assets == null) {
					assets = new Assets();
				}
			}
		}
		return assets;
	}
	
	private Assets() {
		
		//load("Assets.atlas", TextureAtlas.class);
		setLoader(TiledMap.class, new AtlasTmxMapLoader(new InternalFileHandleResolver()));
		load("TileMap.tmx", TiledMap.class);
		finishLoading();
	}
	
	public TiledMap getTiledMap(String name) {
		return get(name, TiledMap.class);
	}
	public TextureAtlas getAtlas() {
		return get("Assets.atlas");
	}
}
