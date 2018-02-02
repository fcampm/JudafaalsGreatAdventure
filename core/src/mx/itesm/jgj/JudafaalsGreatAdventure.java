package mx.itesm.jgj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JudafaalsGreatAdventure extends Game {

	@Override
	public void create () {

		// Pone Pantalla inicial.
		setScreen(new MenuJudafaals(this)); // Solo objetos de Game pueden correr este comando
	}


}
