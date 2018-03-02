package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


public class menu3 implements Screen {

    private final JudafaalsGreatAdventure jdj;

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;

    // Batch
    private SpriteBatch batch;

    // Crear Imágenes.
    private Texture primerNivel;
    Texture foto4 = new Texture("nave2.png");
    Texture foto3 =new Texture("text3.png");
    Texture foto2 =new Texture("text2.png");
    Texture foto1 =new Texture("text1.png");
    private Stage escenaAbout;
    private Texto texto=new Texto();

    public menu3(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }
    @Override
    public void show() {
        crearCamara();
        batch = new SpriteBatch();
        primerNivel = new Texture("Fondos/fondo2.png");
        crearMenu();



    }

    private void crearMenu() {
        escenaAbout = new Stage(vista);

        // Creación de las texturas del botón de back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BottonPlayP.png")));
        TextureRegionDrawable trdBackOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BottonPlayTP.png")));

        // Creción del botón back.
        ImageButton backButton = new ImageButton(trdBack, trdBackOnClick);
        backButton.setPosition(ANCHO/2-foto3.getWidth()/2, ALTO/2-foto3.getHeight());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener", "Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                jdj.setScreen(new Menu2(jdj));

            }
        });

        escenaAbout.addActor(backButton);
        Gdx.input.setInputProcessor(escenaAbout);

    }
    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(primerNivel,0,-220);
        batch.draw(foto4, ANCHO/2-foto4.getWidth()/2,ALTO/2-foto4.getHeight()/2-100);
        batch.draw(foto3,ANCHO/2-foto3.getWidth()/2, ALTO/2-foto3.getHeight() );
        batch.draw(foto2,ANCHO/2-foto3.getWidth()/2, ALTO/2-(foto3.getHeight()*2) );
        batch.draw(foto1,ANCHO/2-foto3.getWidth()/2, ALTO/2-(foto3.getHeight()*3) );

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
