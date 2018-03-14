package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

    //TExtos
    private Texto texto;
    private Texto texto2;
    private int vida = 100;
    private String cadenaVida = "Vida "+vida;

    //Sonido
    private Sound choque;
    private Sound levelpassed;
    private Music musicaFondo;

    //Boton pausa
    private Texture botonPausa;


    public PrimerNivel(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }


    @Override
    public void show() {
        nave=new Personaje(new Texture("PrimerNivel/naveFrames.png"));
        texto = new Texto();
        texto2=new Texto();
        botonPausa = new Texture("pruebas/pausaa.png");
        //Sonidos
        choque = Gdx.audio.newSound(Gdx.files.internal("Musica/choque.wav"));
        levelpassed = Gdx.audio.newSound(Gdx.files.internal("Musica/levelUp.wav"));
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
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        render.setView(camara);
        render.render();
        batch.begin();
        nave.render(batch);
        //Puntos
        if(nave.getX()<ANCHO_MAPA-600){
            texto.mostrarMensaje(batch,cadenaVida,nave.getX()-500,ALTO-20);
        }

        if(nave.getX()>=ANCHO_MAPA-500){
            texto2.mostrarMensaje(batch,"Level Completed",ANCHO_MAPA-600,ALTO-20);
            if(nave.getX()>=ANCHO_MAPA-450){
                levelpassed.play();
                if(nave.getX()>=ANCHO_MAPA-249){

                    levelpassed.pause();
                }
            }
        }
        batch.draw(botonPausa, nave.getX()+500,ALTO-60);
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
            if(nave.getY()+2>=v.y && nave.getY()-2<=v.y){
                nave.normal();}

            else if(v.y>nave.getY()){
                nave.subiendo();
            }
            else if(v.y<nave.getY()){
                nave.bajando();
            }
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