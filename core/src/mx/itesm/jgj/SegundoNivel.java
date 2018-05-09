package mx.itesm.jgj;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

class SegundoNivel extends Pantalla {

    private JudafaalsGreatAdventure jga;
    private AssetManager assetManager;

    // Preferencias del ususario del sonido
    private Preferences soundPreferences = Gdx.app.getPreferences("usersPreferences");
    boolean musicaActivada = soundPreferences.getBoolean("soundOn");

    private Random random = new Random();
    private boolean bombaTomada= false;
    private Bomb bomba = new Bomb(12500, 900);

    //Enemigos
    private Array<Enemigo> arrEnemigo;

    //


    private Personaje nave;
    private float velocidadNave=6;
    private Texture progresoBarra;
    private Texture progresoIndicador;
    private float progresoX;
    float tiempoChoque =0;

    // Nuevo tiled map tiene de ancho 14080 para cambiarlo.
    private static final float ANCHO_MAPA = 14080;
    private double presed = 0;
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
    private String cadenaVida = "Vida :" + vida;

    //Sonido
    private Sound choque;
    private Sound levelpassed;
    private Music musicaFondo;
    private Music bombTaked;

    //vidas
    private Vida life;
    private Vida life2;
    private Vida life3;


    //Boton pausa
    private Texture botonPausa;
    private Texture texturaNave;

    //PAUSA
    private EscenaPausa escenaPausa;
    private EscenaGanar escenaGanar;
    private EscenaPerder escenaPerder;

    //Estado
    private EstadoJuego estado;


    public SegundoNivel(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jga = judafaalsGreatAdventure;
        assetManager = jga.getAssetManager();
    }




    @Override
    public void show() {
        cargarVidas();
        cargarEnemigos();
        cargarTexturas();
        crearMusica();
        cargarPersonaje();
        cargarTextos();
        crearHUD();
        estado = EstadoJuego.JUGANDO;
        escenaGanar = new EscenaGanar(vistaHUD, batch);
        escenaPerder = new EscenaPerder(vistaHUD, batch);


        //Sonidos
        choque = assetManager.get("Musica/choque.mp3");
        levelpassed = assetManager.get("Musica/levelUp.wav");
        cargarMapa();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(escenaHUD);
    }

    private void cargarVidas(){
        life = new Vida(1500, 500);
        life2 = new Vida(4500, 500);
        life3 = new Vida(3300, 500);
    }


    private void crearHUD() {
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO / 2, ALTO / 2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, camaraHUD);
        Skin skin = new Skin();
        skin.add("flechas", flechas);
        skin.add("pausa", botonPausa);
        //Vista del pad
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        Touchpad.TouchpadStyle estilo2 = new Touchpad.TouchpadStyle();
        Touchpad.TouchpadStyle estilo3 = new Touchpad.TouchpadStyle();
        estilo.knob = skin.getDrawable("pausa");
        estilo2.knob = skin.getDrawable("flechas");
        //Crea el pad
        final Touchpad pad = new Touchpad(64, estilo);
        final Touchpad pad2 = new Touchpad(55, estilo2);
        pad.setBounds(ANCHO * 0.78f, ALTO * 0.75f, 256, 256);
        //Aquí van las condiciones para que funcione el boton de pausa en HUD
        pad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                super.clicked(event, x, y);
                estado = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(vistaHUD, batch);
                Gdx.input.setInputProcessor(escenaPausa);

            }
        });
        pad.setColor(1, 1, 1, 1);
        pad2.setBounds(14, 0, 256, 456);
        //Aquí van las condiciones para que funcionen las flechas :)
        pad2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                while(nave.getY()>=ALTO-50){
                    nave.setY(nave.getY()-101);
                    nave.normal();
                    vida--;
                    cadenaVida="Vida: "+vida;
                }
                if(nave.getY()<ALTO-50){
                    if (pad2.getKnobPercentY() > 0) {

                        nave.subiendo();
                        System.out.println("subiendo");
                        presed = (-6) * pad2.getKnobPercentY();
                    } else if (pad2.getKnobPercentY() < 0) {

                        nave.bajando();
                        presed = (-6) * pad2.getKnobPercentY();
                    } else {
                        if(velocidadNave!=0){
                            nave.normal();}
                        presed = 0;
                    }
                }

                while(nave.getY()<=10){
                    nave.setY(nave.getY()+101);
                    vida--;
                    cadenaVida="Vida: "+vida;
                }

            }

        });
        pad2.setColor(1, 1, 1, 1);
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(pad2);
    }


    private void cargarTexturas() {
        botonPausa = assetManager.get("pruebas/pausaa.png");
        flechas = assetManager.get("PrimerNivel/flechas2.png");
        progresoBarra = assetManager.get("PrimerNivel/progresoBarra.png");
        progresoIndicador = assetManager.get("PrimerNivel/progresoIndicador.png");
        texturaNave = assetManager.get("PrimerNivel/NaveUReducida.png");
    }

    private void cargarTextos() {
        texto = new Texto();
        texto2 = new Texto();
    }

    private void cargarPersonaje() {
        nave = new Personaje(texturaNave);
    }

    private void cargarEnemigos(){
        arrEnemigo=new Array<Enemigo>(11*10);
        for(int i =0; i<5; i++){
            for(int j=0; j<10;j++){
                Enemigo enemy = new Enemigo(random.nextInt((int) (ANCHO_MAPA+7000 - (3500))) + (3500)+j*100,random.nextInt((int) (ALTO-300 - (300))) + (300)+i*100-250);
                arrEnemigo.add(enemy);

            }
        }
    }

    private void bajarVida(boolean bandera, int daño){
        if(bandera)
            this.vida=vida-daño;
    }

    private void moverEnemigos(boolean bandera){
        if(bandera) {
            for (Enemigo enemy : arrEnemigo) {
                enemy.mover(-random.nextInt(15 - (2)) + (2), 0);

            }
        }
    }


    private void crearMusica() {
        float volumen = 0.5f;
        musicaFondo = assetManager.get("Musica/level1.mp3");
        musicaFondo.setVolume(volumen);
        if(musicaActivada) {
            musicaFondo.play();
            musicaFondo.setLooping(true);
        }
    }


    private void cargarMapa() {
        mapa = assetManager.get("SegundoNivel/mapaNivelDos.tmx");
        render = new OrthogonalTiledMapRenderer(mapa);}

    @Override
    public void render(float delta) {
        if (estado == EstadoJuego.PAUSADO) {
            moverEnemigos(false);
            actualizarObjetos(delta, false);
            if(musicaActivada) {
                musicaFondo.pause();
            }
            verificarColisiones(false);
        }

        if (estado == EstadoJuego.JUGANDO) {
            verificarColisiones(true);
            moverEnemigos(true);
            actualizarObjetos(delta, true);
            if(musicaActivada) {
                musicaFondo.play();
            }
            life.mover(-1, +random.nextInt(7 - (-7)) + (-7), true);
            life2.mover(-1, +random.nextInt(4 - (-4)) + (-4), true);
            life3.mover(-1, +random.nextInt(5 - (-5)) + (-5), true);
            bomba.mover(-1, +random.nextInt(4 - (-4)) + (-4), true);
        }


        actualizarCamara();
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        render.setView(camara);
        render.render();
        batch.begin();
        for(Enemigo enemigo: arrEnemigo){
            enemigo.render(batch);
        }
        nave.render(batch);
        batch.draw(progresoBarra,nave.getX()-380,ALTO-55);
        batch.draw(progresoIndicador, progresoX,ALTO-55);

        life.render(batch);
        life2.render(batch);
        life3.render(batch);

        //life.mover(+1,+1, true);
        GenerarTextosySonidos();
        bomba.render(batch);


        batch.end();
        if (estado == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

        if (estado == EstadoJuego.PERDIDO) {
            //escenaPerder = new EscenaPerder(vistaHUD, batch);
            Gdx.input.setInputProcessor(escenaPerder);
            escenaPerder.draw();
        }

        if (estado == EstadoJuego.GANADO) {
            actualizarObjetos(1,true);
            //escenaGanar = new EscenaGanar(vistaHUD, batch);
            verificarColisiones(false);
            Gdx.input.setInputProcessor(escenaGanar);
            escenaGanar.draw();
            if(musicaActivada) {
                musicaFondo.dispose();
            }
        }

        //CamaraHUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();
        Gdx.app.log("fps", "FPS:" + Gdx.graphics.getFramesPerSecond());

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            estado = EstadoJuego.PAUSADO;
            escenaPausa = new EscenaPausa(vistaHUD, batch);
            Gdx.input.setInputProcessor(escenaPausa);
        }
    }

    private void actualizarCamara() {
        // Depende de la posición del personaje. Siempre sigue al personaje
        float posX = nave.getX();
        // Primera mitad de la pantalla
        if (posX < ANCHO / 2) {
            camara.position.set(ANCHO / 2, ALTO / 2, 0);
        } else if (posX > ANCHO_MAPA - ANCHO / 2) {   // Última mitad de la pantalla
            camara.position.set(ANCHO_MAPA - ANCHO / 2, camara.position.y, 0);
        } else {    // En 'medio' del mapa
            camara.position.set(posX, camara.position.y, 0);
        }
        camara.update();
    }

    private void GenerarTextosySonidos() {
        if (nave.getX() < ANCHO_MAPA - 600) {
            texto.mostrarMensaje(batch, cadenaVida, nave.getX() - 500, ALTO - 20);
        }
        if (nave.getX() >= ANCHO_MAPA - 150) {
            if(bombaTomada) {
                texto2.mostrarMensaje(batch, "Level Completed", ANCHO_MAPA - 650, ALTO - 20);
                estado = EstadoJuego.GANADO;
                musicaFondo.dispose();
            } else{
                estado = EstadoJuego.PERDIDO;
                texto2.mostrarMensaje(batch, "You forgot the bomb!", ANCHO_MAPA - 650, ALTO - 20);
            }
        }

        if(bombaTomada) {
            if(nave.getX()<=ANCHO_MAPA-530)
            texto2.mostrarMensaje(batch, "Bomba tomada!", nave.getX() - 45, 50);
        }
    }

    private void actualizarObjetos(float dt, boolean actualizar) {
        if (actualizar) {
            nave.setX(nave.getX() + velocidadNave);
            nave.setY(nave.getY() + (float)presed);
            progresoX =(nave.getX()*817/ANCHO_MAPA)+nave.getX()-380;

        }
        verificarColisiones(true);
        if(nave.getEstado()==EstadoNave.CHOQUE){
            if(tiempoChoque >40){
                nave.normal();
                velocidadNave=6;
                tiempoChoque =0;
            }
            else{
                tiempoChoque++;
            }
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

        if (escenaPausa != null) {
            escenaPausa.dispose();
        }

        escenaHUD.dispose();

        assetManager.unload("pruebas/pausaa.png");
        assetManager.unload("PrimerNivel/flechas2.png");
        assetManager.unload("PrimerNivel/NaveUReducida.png");
        assetManager.unload("Botones/BotonExitN.png");
        assetManager.unload("Botones/BotonPlayN.png");
        assetManager.unload("Botones/BotonReinicioN.png");
        assetManager.unload("PrimerNivel/YouWin.png");
        assetManager.unload("Botones/BotonExitN.png");
        assetManager.unload("Botones/BotonReinicioN.png");
        assetManager.unload("PrimerNivel/youFailed.png");
        assetManager.unload("Botones/BotonExitN.png");
        assetManager.unload("Botones/BotonReinicioN.png");
        assetManager.unload("Musica/choque.mp3");
        assetManager.unload("Musica/levelUp.wav");
        assetManager.unload("Musica/level1.mp3");
        assetManager.unload("Musica/bombTaked.mp3");
        assetManager.unload("SegundoNivel/mapaNivelDos.tmx");
        assetManager.unload("PrimerNivel/progresoBarra.png");
        assetManager.unload("PrimerNivel/progresoIndicador.png");

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
    private void colisionesMapa(int x1, int y1,int x2,int y2) {
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Estructuras");
        int cx = (int)(nave.getX()+x2) / 32;
        int cy = (int)(nave.getY()+y2) / 32;
        int cx2 =(int)(nave.getX()+x1) / 32;
        int cy2 =(int)(nave.getY()+y1) / 32;
        TiledMapTileLayer.Cell celda = capa.getCell(cx, cy);
        TiledMapTileLayer.Cell celda2 = capa.getCell(cx, cy2);
        TiledMapTileLayer.Cell celda3 = capa.getCell(cx2, cy);
        TiledMapTileLayer.Cell celda4 = capa.getCell(cx2, cy2);
        if (celda != null || celda2!=null || celda3 != null || celda4!=null) {
            vida -= 10;
            if (vida <= 0) {
                estado = EstadoJuego.PERDIDO;

            }
            cadenaVida = "Vida: " + vida;
            if(musicaActivada) {
                choque.play();
            }
            choque();

        }
    }

    private void choque() {

        nave.setX(nave.getX() - 260);
        velocidadNave=(float)0;
        nave.chocar();



    }

    private void verificarColisiones(boolean bandera) {
        colisionesMapa(9,23,17,52);
        colisionesMapa(18,48,28,52);
        colisionesMapa(18,10,66,48);
        colisionesMapa(67,38,82,44);
        colisionesMapa(67,29,104,36);
        colisionesMapa(67,19,118,26);
        colisionesMapa(67,12,106,19);
        colisionesMapa(107,16,128,19);

        if(bandera) {

            if (bomba.estaColisionando(nave)) {
                bombaTomada = true;
                bomba.set(-50, ALTO * 2);
                bombTaked = assetManager.get("Musica/bombTaked.mp3");
                bombTaked.setVolume(5f);
                if(musicaActivada) {
                    bombTaked.play();
                    bombTaked.setLooping(false);
                }
            }
            int cx = (int) (nave.getX() + 32) / 32;
            int cy = (int) (nave.getY() + nave.getHeight() / 2) / 32;
            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Estructuras");
            TiledMapTileLayer.Cell celda = capa.getCell(cx, cy);
            if (celda != null) {
                System.out.println(celda);
                bajarVida(true, 10);
                if (vida <= 0) {
                    estado = EstadoJuego.PERDIDO;
                }
                cadenaVida = "Vida: " + vida;
                if(musicaActivada) {
                    choque.play();
                }
                nave.setX(nave.getX() - 260);
            }
            if (life.estaColisionando(nave) || life2.estaColisionando(nave) || life3.estaColisionando(nave)) {
                vida += 20;
                cadenaVida = "Vida: " + vida;
                if (life.estaColisionando(nave)) {
                    life.set(-50, ALTO * 2);
                    if(musicaActivada) {
                        levelpassed.play();
                    }
                }
                if (life2.estaColisionando(nave)) {
                    life2.set(-50, ALTO * 2);
                    if(musicaActivada) {
                        levelpassed.play();
                    }
                }
                if (life3.estaColisionando(nave)) {
                    life3.set(-50, ALTO * 2);
                    if(musicaActivada) {
                        levelpassed.play();
                    }
                }


            }

            for (Enemigo enemy : arrEnemigo) {
                if (enemy.estaColisionando(nave)) {
                    vida--;
                    cadenaVida = "Vida: " + vida;
                    if(vida<=0) {
                        estado = EstadoJuego.PERDIDO;
                        cadenaVida = "You lose!";
                    }

                }

            }

        }

    }

    enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANADO,
        PERDIDO
    }

    private class EscenaPausa extends Stage {

        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista, batch);

            // Creación de texturas.
            Texture texturaBtnSalir;
            Texture texturaBtnContinuar;
            Texture restartButton;

            Pixmap pixmap = new Pixmap((int) (ANCHO * 0.7f), (int) (ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f * ANCHO, 0.1f * ALTO);
            this.addActor(imgRectangulo);
            texturaBtnSalir = assetManager.get("Botones/BotonExitN.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO / 2 - btnSalir.getWidth() / 2, ALTO / 2);
            btnSalir.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    if(musicaActivada) {
                        musicaFondo.dispose();
                    }
                    jga.setScreen(new PantallaCargando(jga, Pantalla.MENU));

                }
            });
            this.addActor(btnSalir);

            texturaBtnContinuar = assetManager.get("Botones/BotonPlayN.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO / 2 - btnContinuar.getWidth() / 2 - 150, ALTO / 4);
            btnContinuar.addListener(new ClickListener() {
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


            restartButton = assetManager.get("Botones/BotonReinicioN.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(ANCHO / 2 - restartBtn.getWidth() / 2 + 150, ALTO / 4);

            restartBtn.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    if(musicaActivada) {
                        musicaFondo.stop();
                    }
                    jga.setScreen(new PantallaCargando(jga, Pantalla.SEGUNDONIVEL));

                }

            });

            this.addActor(restartBtn);
        }

        // Escena para la pantalla de ganar ------------------------------------------------------------


    }

    private class EscenaGanar extends Stage {

        public EscenaGanar(Viewport vista, SpriteBatch batch) {
            super(vista, batch);

            // Creación de texturas.
            Texture texturaBtnSalir;
            Texture texturaBtnContinuar;
            Texture restartButton;
            Texture textureNextLevel;

            Pixmap pixmap = new Pixmap((int) (ANCHO * 0.7f), (int) (ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0f, 0f, 0f, 0f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f * ANCHO, 0.1f * ALTO);
            this.addActor(imgRectangulo);
            texturaBtnSalir = assetManager.get("PrimerNivel/YouWin.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO / 2 - btnSalir.getWidth() / 2, ALTO / 2);
            this.addActor(btnSalir);

            texturaBtnContinuar = assetManager.get("Botones/BotonExitN.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnExit = new ImageButton(trdContinuar);
            btnExit.setPosition(880 - btnExit.getWidth() / 2, 150);
            btnExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    if(musicaActivada) {
                        musicaFondo.dispose();
                    }
                    jga.setScreen(new PantallaCargando(jga, Pantalla.NIVELES));
                }

            });
            this.addActor(btnExit);


            restartButton = assetManager.get("Botones/BotonReinicioN.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(330, 150);

            restartBtn.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    if(musicaActivada) {
                        musicaFondo.stop();
                    }
                    jga.setScreen(new PantallaCargando(jga, Pantalla.SEGUNDONIVEL));

                }

            });

            this.addActor(restartBtn);

            textureNextLevel = assetManager.get("Botones/nextLevel.png");
            TextureRegionDrawable nextLevel = new TextureRegionDrawable(new TextureRegion(textureNextLevel));
            ImageButton btnNextLevel = new ImageButton(nextLevel);

            btnNextLevel.setPosition(ANCHO/2 - btnNextLevel.getWidth()/2, 150);
            btnNextLevel.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if(musicaActivada){
                        musicaFondo.stop();
                    }
                    jga.setScreen(new PantallaCargando(jga, Pantalla.TERCERNIVEL));
                }
            });

            this.addActor(btnNextLevel);
        }

    }

    private class EscenaPerder extends Stage {

        public EscenaPerder(Viewport vista, SpriteBatch batch) {
            super(vista, batch);

            // Creación de texturas.
            Texture texturaBtnSalir;
            Texture texturaBtnContinuar;
            Texture restartButton;

            Pixmap pixmap = new Pixmap((int) (ANCHO * 0.7f), (int) (ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0f, 0f, 0f, 0f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f * ANCHO, 0.1f * ALTO);
            this.addActor(imgRectangulo);
            texturaBtnSalir = assetManager.get("PrimerNivel/youFailed.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO / 2 - btnSalir.getWidth() / 2, ALTO / 2);
            this.addActor(btnSalir);

            texturaBtnContinuar = assetManager.get("Botones/BotonExitN.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO / 2 - btnContinuar.getWidth() / 2 + 300, ALTO / 4);
            btnContinuar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(musicaActivada) {
                        musicaFondo.dispose();
                    }
                    jga.setScreen(new PantallaCargando(jga, Pantalla.MENU));
                }
            });
            this.addActor(btnContinuar);


            restartButton = assetManager.get("Botones/BotonReinicioN.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(586 - 300, ALTO / 4);

            restartBtn.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    if(musicaActivada) {
                        musicaFondo.stop();
                    }
                    jga.setScreen(new PantallaCargando(jga, Pantalla.SEGUNDONIVEL));

                }

            });

            this.addActor(restartBtn);
        }

    }

}