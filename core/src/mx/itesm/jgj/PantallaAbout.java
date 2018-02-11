package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.UIManager;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


class PantallaAbout implements Screen {

    private final JudafaalsGreatAdventure jdj;

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;
    private Texto texto;

    // Batch
    private SpriteBatch batch;

    //Imagenes
    private Texture foto1,foto2,foto3,foto4, fondoAbout;

    public PantallaAbout(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }

    public void CrearObjetos(){
        texto = new Texto();

    }

    @Override
    public void show() {

        crearCamara();
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        CrearObjetos();
        crearImages();
    }

    private void crearImages(){

        foto1= new Texture("GokuNormalicon.png");
        foto2= new Texture("GokuNormalicon.png");
        foto3= new Texture("GokuNormalicon.png");
        foto4= new Texture("GokuNormalicon.png");
        fondoAbout = new Texture("Pantalla Principal.jpg");

    }

    private void crearCamara() {

        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        //MUESTRA TEXTOS
        batch.begin();
        batch.draw(fondoAbout,0,0);
        batch.draw(foto1,ANCHO-ANCHO/6,2.8f*ALTO/4);
        batch.draw(foto2,ANCHO-ANCHO/6,2.1f*ALTO/4);
        batch.draw(foto3,ANCHO-ANCHO/6,1.4f*ALTO/4);
        batch.draw(foto4,ANCHO-ANCHO/6,0.7f*ALTO/4);

        texto.mostrarMensaje(batch,"Desarrolladores:", ANCHO/2-ANCHO/6, 3.8f*ALTO/4 );
        texto.mostrarMensaje(batch,"Fabian Camp Mussa - Programador", ANCHO/2-ANCHO/6, 3.2f*ALTO/4 );
        texto.mostrarMensaje(batch,"Darwin Chavez Salas - Programador", ANCHO/2-ANCHO/6, 2.5f*ALTO/4 );
        texto.mostrarMensaje(batch,"Juan Jose Aguilar Hernandez - Diseñador", ANCHO/2-ANCHO/6, 1.8f*ALTO/4 );
        texto.mostrarMensaje(batch,"Alfonso Alquicer Mendez - Programador", ANCHO/2-ANCHO/6, 1.1f*ALTO/4 );

        batch.end();


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

        batch.dispose();
        fondoAbout.dispose();
        foto1.dispose();
        foto2.dispose();
        foto3.dispose();
        foto4.dispose();
    }

    private class ProcesadorEntrada implements InputProcessor{

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            jdj.setScreen(new MenuJudafaals(jdj));
            return true; //Ya fue procesado el evento.
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
