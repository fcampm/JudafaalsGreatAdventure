package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


public class Menu2 implements Screen {

    private final JudafaalsGreatAdventure jdj;

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;

    // Batch
    private SpriteBatch batch;

    // Crear Imágenes.
    private Texture primerNivel;
    private int DX=1, DY=2;
    Texture foto4 = new Texture("Naveinicio.png");
    Texture foto3 = new Texture("nave3.png");

    public Menu2(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }
    @Override
    public void show() {
        crearCamara();
        batch = new SpriteBatch();
        primerNivel = new Texture("Fondos/fondo2.png");
        Gdx.input.setInputProcessor(new Menu2.ProcesadorEntrada());


    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {
        actualizarMenu();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(primerNivel,0,-DY+-220);
        if(DX>40&&DX<80){
        batch.draw(foto4, ANCHO/2-foto4.getWidth()/2,ALTO/2-foto4.getHeight()/2-100);
        DX+=1;}
        else{
            batch.draw(foto3, ANCHO/2-foto4.getWidth()/2,ALTO/2-foto4.getHeight()/2-100);
            DX+=1;
            if(DX>80){
                DX=1;
            };
        }

        batch.end();
    }

    private void actualizarMenu() {
        DY+=5;
        if(DY>2100){
            jdj.setScreen(new PantallaJugar(jdj));
        }
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
        primerNivel.dispose();
    }

    private class ProcesadorEntrada implements InputProcessor {

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
