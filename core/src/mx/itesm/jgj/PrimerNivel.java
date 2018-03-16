package mx.itesm.jgj;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class PrimerNivel extends Pantalla
{
    private JudafaalsGreatAdventure jdj;


    private Personaje nave;
    private static final float ANCHO_MAPA=5120;
    private int presed=0;

    //Otra camara para componentes
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Stage escenaHUD;

    private TiledMap mapa;
    private OrthogonalTiledMapRenderer render;

    //TExtos
    private Texto texto;
    private Texto texto2;
    private int vida = 100;
    private String cadenaVida = "Vida :"+vida;

    //Sonido
    private Sound choque;
    private Sound levelpassed;
    private Music musicaFondo;
    private float volumen = 0.5f;

    //Boton pausa
    private Texture botonPausa;
    //PAUSA
    private EscenaPausa escenaPausa;

    //Estado
    private EstadoJuego estado;


    public PrimerNivel(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }


    @Override
    public void show() {
        crearMusica();
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




    private void crearMusica() {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.getFileHandle("Musica/volar.ogg", Files.FileType.Internal));
        musicaFondo.setVolume(volumen);
        musicaFondo.play();
        musicaFondo.setLooping(true);
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
        GenerarTextosySonidos();

        batch.draw(botonPausa, ANCHO*0.75f,ALTO*0.8f);
        batch.end();
        if(estado == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }

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

    private void GenerarTextosySonidos(){
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
            if(nave.getX()>=ANCHO_MAPA){
                musicaFondo.pause();
            }
        }
    }

    private void actualizarObjetos(float dt) {
        nave.setX(nave.getX()+1);
        nave.actualizar(dt);
        nave.setY(nave.getY()+presed);




    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        escenaPausa.dispose();
        botonPausa.dispose();

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
            Vector3 v=new Vector3(screenX,screenY,0);
            camara.unproject(v);

            if (v.x>=ANCHO*0.75f && v.x<=ANCHO*0.75f+botonPausa.getWidth()
                    && v.y>=ALTO*0.75f && v.y<=ALTO*0.75f+botonPausa.getHeight()) {
                // Botón pausa!!
                //if (escenaPausa == null) {
                    escenaPausa = new EscenaPausa(vista, batch);
                //}
                // PASA EL CONTROL A LA ESCENA
                estado = EstadoJuego.PAUSADO;
                Gdx.input.setInputProcessor(escenaPausa);
            }// Ya ni detecta touch fuera de la escena

            if(v.y>=ALTO/2){
                nave.subiendo();
                //nave.setY(nave.getY()+2);
                //touchDown(screenX,screenY,pointer,button);
                presed=5;
            }
            else if(v.y<ALTO/2){
                nave.bajando();
                //nave.setY(nave.getY()-1);
                presed=-5;
            }



            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            nave.normal();
            presed=0;
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
           // nave.setY(nave.getY()+2);
            /*Vector3 v=new Vector3(screenX,screenY,0);
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


*/
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

    enum EstadoJuego {
        JUGANDO,
        PAUSADO
    }

    private class EscenaPausa extends Stage
    {

        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista,batch);
            Pixmap pixmap = new Pixmap((int)(ANCHO*0.7f), (int)(ALTO*0.8f), Pixmap.Format.RGBA8888 );
            pixmap.setColor( 1f, 1f, 1f, 0.65f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f*ANCHO, 0.1f*ALTO);
            this.addActor(imgRectangulo);
            Texture texturaBtnSalir = new Texture("Botones/quit.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO/2-btnSalir.getWidth()/2, ALTO/2);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú

                     //JudafaalsGreatAdventure.setScreen(new PrimerNivel(jdj));

                }
            });
            this.addActor(btnSalir);

            Texture texturaBtnContinuar = new Texture("Botones/resume.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO/2-btnContinuar.getWidth()/2, ALTO/4);
            btnContinuar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(new ProcesadorEntrada());
                }
            });
            this.addActor(btnContinuar);
        }
    }

}