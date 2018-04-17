package mx.itesm.jgj;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

class SegundoNivel extends Pantalla {

    private Random random = new Random();

    private JudafaalsGreatAdventure jga;

    //Enemigos
    private Array<Enemigo> arrEnemigo;

    //
    private Personaje nave;
    private static final float ANCHO_MAPA = 11520;
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

    //vidas
    private Vida life;
    private Vida life2;
    private Vida life3;


    //Boton pausa
    private Texture botonPausa;
    //PAUSA
    private EscenaPausa escenaPausa;
    private EscenaGanar escenaGanar;
    private EscenaPerder escenaPerder;

    //Estado
    private EstadoJuego estado;


    public SegundoNivel(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jga = judafaalsGreatAdventure;
    }


    @Override
    public void show() {
        cargarVidas();
        cargarEnemigos();
        crearMusica();
        cargarPersonaje();
        cargarTextos();
        cargarTexturas();
        crearHUD();
        estado = EstadoJuego.JUGANDO;
        escenaGanar = new EscenaGanar(vistaHUD, batch);
        escenaPerder = new EscenaPerder(vistaHUD, batch);


        //Sonidos
        choque = Gdx.audio.newSound(Gdx.files.internal("Musica/choque.mp3"));
        levelpassed = Gdx.audio.newSound(Gdx.files.internal("Musica/levelUp.wav"));
        cargarMapa();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
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
        skin.add("flechas", new Texture("PrimerNivel/flechas2.png"));
        skin.add("pausa", new Texture("pruebas/pausaa.png"));
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
                while (nave.getY() >= ALTO - 50) {
                    nave.setY(nave.getY() - 101);
                    //nave.normal();
                    vida--;
                    cadenaVida = "Vida: " + vida;
                }
                if (nave.getY() < ALTO - 50) {
                    if (pad2.getKnobPercentY() > 0) {
                        nave.subiendo();
                        presed = (-6) * pad2.getKnobPercentY();
                        System.out.println(pad2.getKnobPercentY());
                    } else if (pad2.getKnobPercentY() < 0) {
                        nave.bajando();
                        presed = (-6) * pad2.getKnobPercentY();
                        System.out.println(pad2.getKnobPercentY());
                    } else {
                        nave.normal();
                        presed = 0;
                        System.out.println(pad2.getKnobPercentY());
                    }
                }

                while (nave.getY() <= 50) {
                    nave.setY(nave.getY() + 101);
                    //nave.normal();
                    bajarVida(true, 1);
                    cadenaVida = "Vida: " + vida;
                }

            }
        });
        pad2.setColor(1, 1, 1, 1);
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(pad2);
    }


    private void cargarTexturas() {
        botonPausa = new Texture("pruebas/pausaa.png");
        flechas = new Texture("PrimerNivel/flechas2.png");
    }

    private void cargarTextos() {
        texto = new Texto();
        texto2 = new Texto();
    }

    private void cargarPersonaje() {
        nave = new Personaje(new Texture("PrimerNivel/NaveUReducida.png"));
    }

    private void cargarEnemigos(){
        arrEnemigo=new Array<Enemigo>(11*10);
        for(int i =0; i<5; i++){
            for(int j=0; j<12;j++){
                Enemigo enemy = new Enemigo(random.nextInt((int) (ANCHO_MAPA+7000 - (3500))) + (3500)+j*100,random.nextInt((int) (ALTO-150 - (100))) + (100)+i*100-250);
                arrEnemigo.add(enemy);
                //ANCHO_MAPA+j*1000-1000
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
        musicaFondo = Gdx.audio.newMusic(Gdx.files.getFileHandle("Musica/level1.mp3", Files.FileType.Internal));
        musicaFondo.setVolume(volumen);
        musicaFondo.play();
        musicaFondo.setLooping(true);
    }


    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("PrimerNivel/prueba1.tmx", TiledMap.class);
        manager.finishLoading();
        mapa = manager.get("PrimerNivel/prueba1.tmx");
        render = new OrthogonalTiledMapRenderer(mapa);

    }

    @Override
    public void render(float delta) {
        //moverEnemigos(true);
        verificarColisiones();
        if (estado == EstadoJuego.PAUSADO) {
            moverEnemigos(false);
            actualizarObjetos(delta, false);
            musicaFondo.pause();
        }

        if (estado == EstadoJuego.JUGANDO) {
            moverEnemigos(true);
            actualizarObjetos(delta, true);
            musicaFondo.play();
            life.mover(-1, +random.nextInt(7 - (-7)) + (-7), true);
            life2.mover(-1, +random.nextInt(4 - (-4)) + (-4), true);
            life3.mover(-1, +random.nextInt(5 - (-5)) + (-5), true);
        }

        //if (estado == EstadoJuego.GANADO) {
         //   escenaGanar = new EscenaGanar(vistaHUD, batch);
           // Gdx.input.setInputProcessor(escenaGanar);
            //escenaGanar.draw();
            //musicaFondo.stop();
        //}

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
        life.render(batch);
        life2.render(batch);
        life3.render(batch);

        //life.mover(+1,+1, true);
        GenerarTextosySonidos();

        //batch.draw(botonPausa, ANCHO*0.75f,ALTO*0.8f);
        //batch.draw(flechas,nave.getX()-570,50);
        batch.end();
        if (estado == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

        if (estado == EstadoJuego.PERDIDO) {
            //escenaPerder = new EscenaPerder(vistaHUD, batch);
            Gdx.input.setInputProcessor(escenaPerder);
            nave.setY(ALTO*2);
            escenaPerder.draw();
        }

        if (estado == EstadoJuego.GANADO) {
            //escenaGanar = new EscenaGanar(vistaHUD, batch);
            Gdx.input.setInputProcessor(escenaGanar);

            escenaGanar.draw();
            musicaFondo.dispose();
        }

        //CamaraHUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

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
        if (nave.getX() >= ANCHO_MAPA - 500) {
            texto2.mostrarMensaje(batch, "Level Completed", ANCHO_MAPA - 650, ALTO - 20);

            if (nave.getX() >= ANCHO_MAPA) {
                estado = EstadoJuego.GANADO;
                musicaFondo.dispose();
            }
        }
    }

    private void actualizarObjetos(float dt, boolean actualizar) {
        if (actualizar) {
            nave.setX(nave.getX() + (float)6.5);
            //nave.actualizar(dt);
            nave.setY(nave.getY() + (float) presed);
        }
        verificarColisiones();
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
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {/*
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);

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
            if (v.y >= 190 && v.y <= 280 && v.x < nave.getX() - 370) {
                nave.subiendo();
                //nave.setY(nave.getY()+2);
                //touchDown(screenX,screenY,pointer,button);
                presed = 4;
            } else if (v.y >= 50 && v.y < 140 && v.x < nave.getX() - 370) {
                nave.bajando();
                //nave.setY(nave.getY()-1);
                presed = -4;
            }
            */
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            /*nave.normal();
            presed = 0;*/
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {/*
            // nave.setY(nave.getY()+2);
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);
            /*if(nave.getY()+2>=v.y && nave.getY()-2<=v.y){
                nave.normal();}

            else if(v.y>nave.getY()){
                nave.subiendo();
            }
            else if(v.y<nave.getY()){
                nave.bajando();
            }
            nave.setY(v.y);
            if (v.y >= 190 && v.y <= 280 && v.x < nave.getX() - 370) {
                nave.subiendo();
                //nave.setY(nave.getY()+2);
                //touchDown(screenX,screenY,pointer,button);
                presed = 4;
            } else if (v.y >= 50 && v.y < 140 && v.x < nave.getX() - 370) {
                nave.bajando();
                //nave.setY(nave.getY()-1);
                presed = -4;
            } else if (v.y >= 140 && v.y < 190 && v.x < nave.getX() - 370) {
                nave.normal();
                presed = 0;
            }

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

    private void verificarColisiones() {
        int cx = (int) (nave.getX() + 32) / 32;
        int cy = (int) (nave.getY()+nave.getHeight()/2) / 32;
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Estructura");
        //String name=capa.getName();
        //System.out.println(name+"ddd");
        TiledMapTileLayer.Cell celda = capa.getCell(cx, cy);
        if (celda != null) {
            System.out.println(celda);
            bajarVida(true,10);
            if (vida <= 0) {
                estado = EstadoJuego.PERDIDO;
            }
            cadenaVida = "Vida: " + vida;
            choque.play();
            nave.setX(nave.getX() - 260);
        }
        /*Object tipo = celda.getTile().getProperties().get("tipo");
        if (!"Estructura".equals(tipo)) {
            // No es obstáculo, puede pasar
            presed=34;}*/

        if (life.estaColisionando(nave) || life2.estaColisionando(nave) || life3.estaColisionando(nave)) {
            vida += 20;
            cadenaVida = "Vida: " + vida;
            if (life.estaColisionando(nave)) {
                life.set(-50, ALTO * 2);
                levelpassed.play();
            }
            if (life2.estaColisionando(nave)) {
                life2.set(-50, ALTO * 2);
                levelpassed.play();
            }
            if (life3.estaColisionando(nave)) {
                life3.set(-50, ALTO * 2);
                levelpassed.play();
            }


        }

        for(Enemigo enemy: arrEnemigo){
            if (enemy.estaColisionando(nave)){
                vida--;
                cadenaVida= "Vida: "+vida;
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
            Pixmap pixmap = new Pixmap((int) (ANCHO * 0.7f), (int) (ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0.65f, 1f, 4f, 1f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f * ANCHO, 0.1f * ALTO);
            this.addActor(imgRectangulo);
            Texture texturaBtnSalir = new Texture("Botones/BotonExitN.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO / 2 - btnSalir.getWidth() / 2, ALTO / 2);
            btnSalir.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    musicaFondo.dispose();
                    jga.setScreen(new MenuJudafaals(jga));

                }
            });
            this.addActor(btnSalir);

            Texture texturaBtnContinuar = new Texture("Botones/BotonPlayN.png");
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


            Texture restartButton = new Texture("Botones/BotonReinicioN.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(ANCHO / 2 - restartBtn.getWidth() / 2 + 150, ALTO / 4);

            restartBtn.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    musicaFondo.stop();
                    jga.setScreen(new SegundoNivel(jga));

                }

            });

            this.addActor(restartBtn);
        }

        // Escena para la pantalla de ganar ------------------------------------------------------------


    }

    private class EscenaGanar extends Stage {

        public EscenaGanar(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            Pixmap pixmap = new Pixmap((int) (ANCHO * 0.7f), (int) (ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0f, 0f, 0f, 1f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f * ANCHO, 0.1f * ALTO);
            this.addActor(imgRectangulo);
            Texture texturaBtnSalir = new Texture("PrimerNivel/YouWin.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO / 2 - btnSalir.getWidth() / 2, ALTO / 2);
            this.addActor(btnSalir);

            Texture texturaBtnContinuar = new Texture("Botones/BotonExitN.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnExit = new ImageButton(trdContinuar);
            btnExit.setPosition(880 - btnExit.getWidth() / 2, 150);
            btnExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    musicaFondo.dispose();
                    jga.setScreen(new PantallaCargando(jga, Pantalla.NIVELES));
                }

            });
            this.addActor(btnExit);


            Texture restartButton = new Texture("Botones/BotonReinicioN.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(330, 150);

            restartBtn.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    musicaFondo.stop();
                    jga.setScreen(new SegundoNivel(jga));

                }

            });

            this.addActor(restartBtn);
        }

    }

    private class EscenaPerder extends Stage {

        public EscenaPerder(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            Pixmap pixmap = new Pixmap((int) (ANCHO * 0.7f), (int) (ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0f, 0f, 0f, 1f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f * ANCHO, 0.1f * ALTO);
            this.addActor(imgRectangulo);
            Texture texturaBtnSalir = new Texture("PrimerNivel/youFailed.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO / 2 - btnSalir.getWidth() / 2, ALTO / 2);
            this.addActor(btnSalir);

            Texture texturaBtnContinuar = new Texture("Botones/BotonExitN.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO / 2 - btnContinuar.getWidth() / 2 + 300, ALTO / 4);
            btnContinuar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    musicaFondo.dispose();
                    jga.setScreen(new MenuJudafaals(jga));
                }
            });
            this.addActor(btnContinuar);


            Texture restartButton = new Texture("Botones/BotonReinicioN.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(586 - 300, ALTO / 4);

            restartBtn.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    musicaFondo.stop();
                    jga.setScreen(new SegundoNivel(jga));

                }

            });

            this.addActor(restartBtn);
        }

    }

}