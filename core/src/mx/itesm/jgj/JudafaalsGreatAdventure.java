package mx.itesm.jgj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class JudafaalsGreatAdventure extends Game {

    private final AssetManager assetManager = new AssetManager();

	@Override
	public void create () {

	    assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        Preferences prefs = Gdx.app.getPreferences("usersPreferences");
	    prefs.putBoolean("soundON", true);
	    prefs.flush();

		// Pone Pantalla inicial.
		setScreen(new PantallaCargando(this, Pantalla.MENU)); // Solo objetos de Game pueden correr este comando
	}

	public AssetManager getAssetManager(){
	    return this.assetManager;
    }

    @Override
    public void dispose(){
	    super.dispose();
	    assetManager.clear();
    }

}
