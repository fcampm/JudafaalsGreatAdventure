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

import java.util.Random;

class PrimerNivel extends Pantalla {

    private Random random = new Random();
    public int[] arreglo={-4,4,-2,2};

    private JudafaalsGreatAdventure jga;
    private boolean bandera=true;


    private Personaje nave;
    private static final float ANCHO_MAPA=5120;
    private int presed=0;
    private Texture flechas;


    //Otra camara para componentes
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Stage escenaHUD;

    // Creación del mapa.
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer render;

    //Textos
    private Texto texto;
    private Texto texto2;
    private int vida = 100;
    private String cadenaVida = "Vida :"+vida;

    //Sonido
    private Sound choque;
    private Sound levelpassed;
    private Music musicaFondo;

    //vidas
    cruz life = new cruz(1500,500);
    cruz life2 = new cruz(4500,500);
    cruz life3 = new cruz(3300,500);


    //Boton pausa
    private Texture botonPausa;
    //PAUSA
    private EscenaPausa escenaPausa;

    //Estado
    private EstadoJuego estado;


    public PrimerNivel(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jga = judafaalsGreatAdventure;
    }


    @Override
    public void show() {
        crearMusica();
        cargarPersonaje();
        cargarTextos();
        cargarTexturas();
        crearHUD();
        estado = EstadoJuego.JUGANDO;


        //Sonidos
        choque = Gdx.audio.newSound(Gdx.files.internal("Musica/choque.wav"));
        levelpassed = Gdx.audio.newSound(Gdx.files.internal("Musica/levelUp.wav"));
        cargarMapa();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        Gdx.input.setInputProcessor(escenaHUD);
        //Gdx.input.setInputProcessor(escenaPausa);
    }


    private void crearHUD(){
        camaraHUD=new OrthographicCamera(ANCHO,ALTO);
        camaraHUD.position.set(ANCHO/2,ALTO/2,0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO,ALTO,camaraHUD);
        Skin skin = new Skin();
        skin.add("flechas",new Texture("PrimerNivel/flechasPrueba.png"));
        skin.add("pausa", new Texture("pruebas/pausaa.png"));
        //Vista del pad
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        Touchpad.TouchpadStyle estilo2 = new Touchpad.TouchpadStyle();
        Touchpad.TouchpadStyle estilo3 = new Touchpad.TouchpadStyle();
        estilo.knob = skin.getDrawable("pausa");
        estilo2.knob = skin.getDrawable("flechas");
        //Crea el pad
        final Touchpad pad = new Touchpad(64, estilo);
        final Touchpad pad2 = new Touchpad(64, estilo2);
        pad.setBounds(ANCHO*0.75f,ALTO*0.7f,256,256);
        //Aquí van las condiciones para que funcione el boton de pausa en HUD
        pad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                super.clicked(event,x,y);
                estado=EstadoJuego.PAUSADO;
                escenaPausa= new EscenaPausa(vistaHUD, batch);
                Gdx.input.setInputProcessor(escenaPausa);

            }
        });
        pad.setColor(1,1,1,1);
        pad2.setBounds(16, 40,256,256);
        //Aquí van las condiciones para que funcionen las flechas :)
        pad2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(pad2.getKnobPercentY()>0){
                    //pad2.getKnobPercentX();
                    nave.subiendo();
                    presed=4;
                    //nave.setY(nave.getY()+2);
                    //touchDown(screenX,screenY,pointer,button);
                }
                else if(pad2.getKnobPercentY()<0){
                    nave.bajando();
                    //nave.setY(nave.getY()-1);
                    presed=-4;
                }
                else {
                    nave.normal();
                    presed=0;
                }
            }
        });
        pad2.setColor(1,1,1,1);
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(pad2);
    }




    private void cargarTexturas() {
        botonPausa = new Texture("pruebas/pausaa.png");
        flechas=new Texture("PrimerNivel/flechasPrueba.png");
    }

    private void cargarTextos() {
        texto = new Texto();
        texto2= new Texto();
    }

    private void cargarPersonaje() {
        nave = new Personaje(new Texture("PrimerNivel/NaveUFrames.png"));
    }


    private void crearMusica() {
        float volumen = 0.5f;
        musicaFondo = Gdx.audio.newMusic(Gdx.files.getFileHandle("Musica/level1.mp3", Files.FileType.Internal));
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
        verificarColisiones();
        if(estado==EstadoJuego.PAUSADO){
            actualizarObjetos(delta, false);
            musicaFondo.pause();
        }else{
            actualizarObjetos(delta, true);
            musicaFondo.play();
            life.mover(-1,+random.nextInt(7-(-7))+(-7), true);
            life2.mover(-1,+random.nextInt(4-(-4))+(-4), true);
            life3.mover(-1,+random.nextInt(5-(-5))+(-5), true);

            //life.mover(-1, +(int) (life.getY()+arreglo[(int) (Math.random()*2)]),true);
            //life.mover(-1, +(int) (life.getY()+arreglo[random.nextInt(1-(-0))+(-0)]),true);
        }
        actualizarCamara();
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        render.setView(camara);
        render.render();
        batch.begin();
        nave.render(batch);
        life.render(batch);
        life2.render(batch);
        life3.render(batch);

        //life.mover(+1,+1, true);
        GenerarTextosySonidos();

        //batch.draw(botonPausa, ANCHO*0.75f,ALTO*0.8f);
        //batch.draw(flechas,nave.getX()-570,50);
        batch.end();
        if(estado == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }

        //CamaraHUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

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
            if(nave.getX()==ANCHO_MAPA-450){
                levelpassed.play();
                if(nave.getX()>=ANCHO_MAPA-249){
                    levelpassed.pause();
                }
            }
            if(nave.getX()>=ANCHO_MAPA){
                musicaFondo.dispose();
            }
        }
    }

    private void actualizarObjetos(float dt, boolean actualizar) {
        if(actualizar) {
            nave.setX(nave.getX() + 5);
            //nave.actualizar(dt);
            nave.setY(nave.getY() + presed);
        }
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
        escenaHUD.dispose();

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
            //Vector3 v=new Vector3(screenX,screenY,0);
            //camara.unproject(v);

            //if (v.x>=ANCHO*0.75f && v.x<=ANCHO*0.75f+botonPausa.getWidth()
                    //&& v.y>=ALTO*0.75f && v.y<=ALTO*0.75f+botonPausa.getHeight()) {
                // Botón pausa!!
                //if (escenaPausa == null) {
                   // escenaPausa = new EscenaPausa(vista, batch);
                //}
                // PASA EL CONTROL A LA ESCENA
                //estado = EstadoJuego.PAUSADO;
                //Gdx.input.setInputProcessor(escenaPausa);
            //}// Ya ni detecta touch fuera de la escena
            //if(v.y>=190 && v.y<=280 && v.x<nave.getX()-370){
              //  nave.subiendo();
                //nave.setY(nave.getY()+2);
                //touchDown(screenX,screenY,pointer,button);
                //presed=4;
            //}
            //else if(v.y>=50 && v.y<140 && v.x<nave.getX()-370){
              //  nave.bajando();
                //nave.setY(nave.getY()-1);
                //presed=-4;
            //}





            return false;
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
            Vector3 v=new Vector3(screenX,screenY,0);
            camara.unproject(v);
            /*if(nave.getY()+2>=v.y && nave.getY()-2<=v.y){
                nave.normal();}

            else if(v.y>nave.getY()){
                nave.subiendo();
            }
            else if(v.y<nave.getY()){
                nave.bajando();
            }
            nave.setY(v.y);*/
             if(v.y>=190 && v.y<=280 && v.x<nave.getX()-370){
                nave.subiendo();
                //nave.setY(nave.getY()+2);
                //touchDown(screenX,screenY,pointer,button);
                presed=4;
            }
            else if(v.y>=50 && v.y<140 && v.x<nave.getX()-370){
                nave.bajando();
                //nave.setY(nave.getY()-1);
                presed=-4;
            }
            else if(v.y>=140 && v.y<190 && v.x<nave.getX()-370){
                nave.normal();
                presed=0;
             }



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

    private void verificarColisiones(){
        if(life.estaColisionando(nave)||life2.estaColisionando(nave)||life3.estaColisionando(nave)){
            vida+=20;
            cadenaVida="Vida: "+vida;
            if(life.estaColisionando(nave)) {
                life.set(-50, ALTO * 2);
                levelpassed.play();
            }
            if(life2.estaColisionando(nave)) {
                life2.set(-50, ALTO * 2);
                levelpassed.play();
            }
            if(life3.estaColisionando(nave)) {
                life3.set(-50, ALTO * 2);
                levelpassed.play();
            }


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
            pixmap.setColor( 0.65f, 1f, 4f, 1f );
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
                     musicaFondo.dispose();
                     jga.setScreen(new MenuJudafaals(jga));

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
                    cargarMapa();
                    Gdx.input.setInputProcessor(new ProcesadorEntrada());
                    Gdx.input.setInputProcessor(escenaHUD);
                    estado = EstadoJuego.JUGANDO;
                }
            });
            this.addActor(btnContinuar);



            Texture restartButton = new Texture("Botones/restart.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(586,150);

            restartBtn.addListener(new ClickListener(){

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    musicaFondo.stop();
                    jga.setScreen(new PrimerNivel(jga));

                }

            });

            this.addActor(restartBtn);
        }
    }

}