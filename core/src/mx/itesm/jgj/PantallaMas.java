package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;

/**
 * Created by taylu on 23/02/2018.
 */

class PantallaMas extends Pantalla {

    private JudafaalsGreatAdventure jga;

    // Texturas a usar.
    private Texture fondoPantallaMas;
    private TextureRegionDrawable texturaHomeNegro;
    private TextureRegionDrawable texturaHomeGris;
    private TextureRegionDrawable texturaAbout;
    private TextureRegionDrawable texturaAboutOnClick;

    // Escenas a usar.
    private Stage escenaPantallaMas;

    public PantallaMas(JudafaalsGreatAdventure judafaalsGreatAdventure) {
        this.jga = judafaalsGreatAdventure;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscena();
        Gdx.input.setInputProcessor(escenaPantallaMas);
    }

    private void crearEscena() {
        escenaPantallaMas = new Stage(vista);

        // Botón home que regresa al menú principal.
        ImageButton btnHome = new ImageButton(texturaHomeNegro, texturaHomeGris);
        btnHome.setPosition(0, ALTO - btnHome.getHeight());
        btnHome.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new MenuJudafaals(jga));
            }
        });
        escenaPantallaMas.addActor(btnHome);

        // Botón about que manda a la pantalla de about con la información de los desarrolladores.
        ImageButton btnAbout = new ImageButton(texturaAbout, texturaAboutOnClick);
        btnAbout.setPosition(ANCHO/4 - btnAbout.getWidth(), ALTO/4 - btnAbout.getHeight());
        btnAbout.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new PantallaAbout(jga));
            }
        });
        escenaPantallaMas.addActor(btnAbout);
    }

    private void cargarTexturas() {
        fondoPantallaMas = new Texture("Fondos/FondoMasS.png");
        texturaHomeNegro = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/homeNegro.png")));
        texturaHomeGris = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/homeGris.png")));
        texturaAbout = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/about.png")));
        texturaAboutOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/aboutOnClick.png")));
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoPantallaMas, 0, 0);
        batch.end();

        escenaPantallaMas.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        escenaPantallaMas.dispose();
        batch.dispose();
        fondoPantallaMas.dispose();
    }
}
