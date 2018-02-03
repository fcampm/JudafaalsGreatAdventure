package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
* Participantes del código:
* 1. Fabián Camp Mussa - A10378565.
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
