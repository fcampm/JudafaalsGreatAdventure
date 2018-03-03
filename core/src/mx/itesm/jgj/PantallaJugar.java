package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


public class PantallaJugar implements Screen {

    private final JudafaalsGreatAdventure jdj;

    // Tamaño total del nivel 1
    private final float ANCHO_MAPA = 5120;

    // Mapas
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer render; // Dibuja el mapa.

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;

    // Batch
    private SpriteBatch batch;

    // Crear Imágenes.
    private Texture primerNivel;

    //Personaje
    private Nave nave;
    private float vxNave = 0;
    private final float VX_NAVE = 5;


    public PantallaJugar(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }
    @Override
    public void show() {
        crearCamara();
        batch = new SpriteBatch();
        primerNivel = new Texture("nivel1.PNG");
        cargarMapa();
        Gdx.input.setInputProcessor(new PantallaJugar.ProcesadorEntrada());


    }

    private void cargarMapa() {

        AssetManager manager = new AssetManager(); // Su especialidad es cargar texturas.
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("PrimerNivel/prueba1.tmx", TiledMap.class);
        manager.finishLoading();
        mapa = manager.get("PrimerNivel/prueba1.tmx");
        render = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {

        moverCamara();

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        render.setView(camara);
        render.render();

    }

    private void moverCamara() {

        camara.position.set(camara.position.x + 2, camara.position.y, 0);
        camara.update();

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
