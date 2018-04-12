package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.UIManager;
import javax.xml.soap.Text;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


class PantallaAbout extends Pantalla {

    private JudafaalsGreatAdventure jga;
    private AssetManager assetManager;

    // Texturas a usar.
    private Texture fabianCamp;
    private Texture alfonsoAlquicer;
    private Texture darwinJomair;
    private Texture juanAguilar;
    private Texture fondoPantallaAbout;
    private Texture texturaBack;
    private TextureRegionDrawable imagenBack;

    // Textos a usar.
    private Texto texto;

    // Escena de la pantalla de about.
    private Stage escenaAbout;

    public PantallaAbout(JudafaalsGreatAdventure judafaalsGreatAdventure) {
        this.jga = judafaalsGreatAdventure;
        assetManager = jga.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        cargarTextos();
        cargarEscena();
        Gdx.input.setInputProcessor(escenaAbout);
    }

    private void cargarEscena() {
        escenaAbout = new Stage(vista);

        // Botón back que regresa a la pantalla mas.
        ImageButton btnBackToPantallaMas = new ImageButton(imagenBack);
        btnBackToPantallaMas.setPosition(25, ALTO - 25 - btnBackToPantallaMas.getHeight());
        btnBackToPantallaMas.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new MenuJudafaals(jga));
            }
        });
        escenaAbout.addActor(btnBackToPantallaMas);
    }

    private void cargarTextos() {
        texto = new Texto();
    }

    private void cargarTexturas() {
        fabianCamp = assetManager.get("AboutFotos/fabianCamp.png");
        alfonsoAlquicer = assetManager.get("AboutFotos/alfonsoAlquicer.png");
        darwinJomair = assetManager.get("AboutFotos/darwinJomair.png");
        juanAguilar = assetManager.get("AboutFotos/juanAguilar.png");
        fondoPantallaAbout = assetManager.get("Fondos/FondoAcercaDe.png");
        texturaBack = assetManager.get("Botones/FlechaAtras.png");
        imagenBack = new TextureRegionDrawable(new TextureRegion(texturaBack));
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        // Se dibujan el fondo y las fotos de los desarrolladores.
        batch.draw(fondoPantallaAbout, 0, 0);
        batch.draw(fabianCamp, ANCHO - ANCHO/6, 2.1f * ALTO/4);
        batch.draw(darwinJomair, ANCHO - ANCHO/6, 1.4f * ALTO/4);
        batch.draw(juanAguilar, ANCHO - ANCHO/6, 0.7f * ALTO/4);
        batch.draw(alfonsoAlquicer, ANCHO - ANCHO/6, 0.0f * ALTO/4);

        // Se dibujan los mensajes a mostrar en la pantalla de about.
        texto.mostrarMensaje(batch, "Desarrolladores:", ANCHO/2 - ANCHO/6, 3.5f * ALTO/4);
        texto.mostrarMensaje(batch, "Fabian Camp Mussa - ISC", ANCHO/2 - ANCHO/6 - 30, 2.5f * ALTO/4);
        texto.mostrarMensaje(batch, "Darwin Chavez Salas - ISC", ANCHO/2 - ANCHO/6, 1.8f * ALTO/4);
        texto.mostrarMensaje(batch, "Juan Jose Aguilar Hernandez - LAD", ANCHO/2 - ANCHO/6, 1.1f * ALTO/4);
        texto.mostrarMensaje(batch, "Alfonso Alquicer Mendez - ISC", ANCHO/2 - ANCHO/6, 0.4f * ALTO/4);

        batch.end();

        escenaAbout.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        escenaAbout.dispose();
        assetManager.unload("AboutFotos/fabianCamp.png");
        assetManager.unload("AboutFotos/alfonsoAlquicer.png");
        assetManager.unload("AboutFotos/darwinJomair.png");
        assetManager.unload("AboutFotos/juanAguilar.png");
        assetManager.unload("Fondos/FondoAcercaDe.png");
        assetManager.unload("Botones/FlechaAtras.png");
    }
}
