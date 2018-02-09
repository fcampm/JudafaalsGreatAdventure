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

/*
* Participantes del código:
* 1. Fabián Camp Mussa - A10378565.
* 2. Darwin Jomair Chavez Salas - A01373791
* 3. Juan José Aguilar Hernández - A01377413
* */


class MenuJudafaals implements Screen {

    // Variables de instancia
    private JudafaalsGreatAdventure jdj;

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;

    // Dimensiones del mundo
    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    // Batch
    private SpriteBatch batch;

    // Escenas
    private Stage escenaMenu;

    public MenuJudafaals(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }

    // Método que se encarga de mostrar en la pantalla.
    @Override
    public void show() {

        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
    }

    private void crearMenu() {

        escenaMenu = new Stage(vista);

        // Creación de las texturas de los bótones.
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("play.png")));
        TextureRegionDrawable trdOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("playOnClick.png")));

        // Creación del botón con su cambio al hacer click.
        ImageButton btnPlay = new ImageButton(trdPlay, trdOnClick);
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2, ALTO/2 - btnPlay.getHeight()/2); //Centramos el botón en la pantalla.

        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener","Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                jdj.setScreen(new PantallaAbout(jdj));
            }
        }); // Click y touch son equivalentes.

        escenaMenu.addActor(btnPlay);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    // Método que se encarga de crear a la cámara a usar.
    private void crearCamara() {

        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1); // Borra la pantalla con el color blanco en RGB.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined); // Escala los objetos.

        // Dibuja en pantalla el stage llamado escenaMenu.
        escenaMenu.draw();

    }

    @Override
    public void resize(int width, int height) {

        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
