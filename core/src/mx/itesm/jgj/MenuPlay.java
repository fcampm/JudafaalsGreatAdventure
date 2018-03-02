package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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


public class MenuPlay implements Screen {

    private final JudafaalsGreatAdventure jdj;

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;

    // Batch
    private SpriteBatch batch;

    // Crear Imágenes.
    private Texture primerNivel;
    Texture naveFondo = new Texture("Naveinicio.png");
    Texture botonTercerNivel =new Texture("Botones/BotonNivelTres.png");
    Texture botonSegundoNivel =new Texture("Botones/BotonNivelDos.png");
    Texture botonPrimerNivel =new Texture("Botones/BotonNivelUno.png");
    private Stage escenaAbout;

    public MenuPlay(JudafaalsGreatAdventure judafaalsGreatAdventure) {

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
        backButton.setPosition(ANCHO/2-botonTercerNivel.getWidth()/2, ALTO/2-botonTercerNivel.getHeight());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener", "Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                jdj.setScreen(new PrimerNivel(jdj));

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
        batch.draw(naveFondo, ANCHO/2-naveFondo.getWidth()/2,ALTO/2-naveFondo.getHeight()/2-100);
        batch.draw(botonTercerNivel,ANCHO/2-botonTercerNivel.getWidth()/2, ALTO/2-botonTercerNivel.getHeight() );
        batch.draw(botonSegundoNivel,ANCHO/2-botonTercerNivel.getWidth()/2, ALTO/2-(botonTercerNivel.getHeight()*2) );
        batch.draw(botonPrimerNivel,ANCHO/2-botonTercerNivel.getWidth()/2, ALTO/2-(botonTercerNivel.getHeight()*3) );

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
