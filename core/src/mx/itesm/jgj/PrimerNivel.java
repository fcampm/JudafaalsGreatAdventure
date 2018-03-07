package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class PrimerNivel extends Pantalla
{
    private JudafaalsGreatAdventure jdj;


    private Personaje nave;
    private static final float ANCHO_MAPA=5120;


    private TiledMap mapa;
    private OrthogonalTiledMapRenderer render;

    private int vida = 3;
    private String cadenaVida = "Vidas : "+vida;
    private Texto texto;

    private cruz cross;
    private cruz cross2;
    private cruz cross3;


    public PrimerNivel(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }


    @Override
    public void show() {
        nave=new Personaje(new Texture("PrimerNivel/animacionNaveMover.png"));
        texto = new Texto();
        cross = new cruz(ANCHO+ANCHO/2, ALTO*0.07f);
        cross2 = new cruz(ANCHO, (ALTO*0.5f)+2);
        cross3 = new cruz(ANCHO, (ALTO*0.2f)+4);
        cargarMapa();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void cargarMapa() {
        AssetManager manager =new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("PrimerNivel/prueba1.tmx",TiledMap.class);
        manager.finishLoading();
        mapa=manager.get("PrimerNivel/prueba1.tmx");
        render= new OrthogonalTiledMapRenderer(mapa);
    }

    @Override
    public void render(float delta) {
        actualizarObjetos(delta);
        actualizarCamara();
        verificarColisionesVidas();
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        render.setView(camara);
        render.render();
        batch.begin();
        nave.render(batch);
        cross.render(batch);
        cross2.render(batch);
        cross3.render(batch);
        texto.mostrarMensaje(batch, cadenaVida, 150, ALTO*0.9f);
        batch.end();

    }
    private void actualizarCamara() {
        // Depende de la posición del personaje. Siempre sigue al personaje
        float posX = nave.getX();
        // Primera mitad de la pantalla
        if (posX < ANCHO/2 ) {
            camara.position.set(ANCHO/2, ALTO/2, 0);
        } else if (posX > ANCHO_MAPA - ANCHO/2) {   // Última mitad de la pantalla
            camara.position.set(ANCHO_MAPA-ANCHO/2,camara.position.y,0);
        } else {    // En 'medio' del mapa
            camara.position.set(posX,camara.position.y,0);
        }
        camara.update();
    }

    private void actualizarObjetos(float dt) {
        nave.setX(nave.getX()+10);
        nave.actualizar(dt);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void verificarColisionesVidas(){
        if (cross2.estaColisionando(nave)) {
            vida++;
            cross2.set(-50,ALTO*2);
        }
        if (cross.estaColisionando(nave)) {
            vida++;
            cross.set(-50,ALTO*2);
        }
        if (cross3.estaColisionando(nave)) {
            vida++;
            cross3.set(-50,ALTO*2);
        }

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
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            Vector3 v=new Vector3(screenX,screenY,0);
            camara.unproject(v);
            nave.setY(v.y);
            return true;
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