package mx.itesm.jgj;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

/*
* Participantes del código:
* 1. Fabián Camp Mussa - A10378565.
* 2. Darwin Jomair Chavez Salas - A01373791
* 3. Juan José Aguilar Hernández - A01377413
* 4. Alfonso Alquícer Méndez - A01373252
* */


class MenuJudafaals implements Screen {

    // Variables de instancia
    private JudafaalsGreatAdventure jdj;

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;

    // Dimensiones del mundo
    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    // Batch
    private SpriteBatch batch;

    // Fondo del menú principal
    private Texture fondoMenu;

    // Escenas
    private Stage escenaMenu;

    // Música de fondo.
    private Music musicaFondo;
    private float volumen = 0.5f;

    public MenuJudafaals(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
    }

    // Método que se encarga de mostrar en la pantalla.
    @Override
    public void show() {

        crearCamara();
        crearMenu();
        crearMusica();
        batch = new SpriteBatch();
    }

    private void crearMusica() {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.getFileHandle("message.mp3", Files.FileType.Internal));
        musicaFondo.setVolume(volumen);
        musicaFondo.play();
        musicaFondo.setLooping(true);
    }

    private void crearMenu() {


        escenaMenu = new Stage(vista);
        /*
        // Creación de las texturas de los bótones.
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("play.png")));
        TextureRegionDrawable trdOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("playOnClick.png")));

        //Creacion de botones de Ayuda
        TextureRegionDrawable botonAyuda = new TextureRegionDrawable(new TextureRegion(new Texture("botonAyuda.png")));
        TextureRegionDrawable botonAyudaClick = new TextureRegionDrawable(new TextureRegion(new Texture("botonAyudaClick.png")));

        // Creación del botón con su cambio al hacer click
        ImageButton btnPlay = new ImageButton(trdPlay, trdOnClick);
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2, ALTO/2 - btnPlay.getHeight()/2); //Centramos el botón en la pantalla.

        // Creación del botón ayuda con su cambio al hacer click.
        ImageButton btnAyuda = new ImageButton(botonAyuda, botonAyudaClick);
        btnAyuda.setPosition(ANCHO/2 - btnAyuda.getWidth()/2, ALTO/2 - btnAyuda.getHeight()/2-btnAyuda.getHeight()-20); //Centramos el botón en la pantalla debajo del boton play.





        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener","Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                musicaFondo.stop(); // Para la reproducción de la música al entrar en la siguiente pantalla.
                jdj.setScreen(new PantallaAbout(jdj));

            }
        }); // Click y touch son equivalentes.

        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener","Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                musicaFondo.stop(); // Para la reproducción de la música al entrar en la siguiente pantalla.
                jdj.setScreen(new PantallaAyuda(jdj));
            }
        }); // Click y touch son equivalentes.



        escenaMenu.addActor(btnPlay);
        escenaMenu.addActor(btnAyuda);
        */

        //Crear boton, inicializarlo
        ImageButton btnPlay = crearBotonTodo("play.png", "playOnClick.png", ANCHO/2 - 256/2, ALTO/2-128/2,  new PantallaJugar(jdj));
        //ImageButton btnPlay=iniciarBoton("play.png","playOnClick.png");
        //poner las posiciones y detalles del boton
        //configurarBoton(btnPlay,ANCHO/2 - btnPlay.getWidth()/2, ALTO/2 - btnPlay.getHeight()/2,new PantallaJugar(jdj));

        //usar otro metodo para hacer todos los metodos de otro boton
        ImageButton btnAyuda= crearBotonTodo("botonAyuda.png","botonAyudaClick.png",ANCHO/2 - 65,ALTO/2 - 65-130-20, new PantallaAyuda(jdj));

        //crear boton para pntalla LeaderBoard
        //ImageButton btnLeader=iniciarBoton("play.png","playOnClick.png");
        //configurarBoton(btnLeader,ANCHO-btnLeader.getWidth()-5,5,new PantallaLeaderboard(jdj));
        ImageButton btnLeader = crearBotonTodo("leaderBoard.png", "leaderBoardClicked.png", ANCHO-150, 5,new PantallaLeaderboard(jdj));
        ImageButton btnAbout = crearBotonTodo("about.png", "aboutClicked.png", 5, 5, new PantallaAbout(jdj));
        Gdx.input.setInputProcessor(escenaMenu);
    }

    //Metodo para iniciar el boton
    private ImageButton iniciarBoton(String texturaNormal, String texturaOnClick) {
        TextureRegionDrawable boton = new TextureRegionDrawable(new TextureRegion(new Texture(texturaNormal)));
        TextureRegionDrawable botonClick = new TextureRegionDrawable(new TextureRegion(new Texture(texturaOnClick)));

        ImageButton btn = new ImageButton(boton, botonClick);
        return btn;
    }

    // metodo para configurar el boton
    private void configurarBoton(ImageButton btn,float x, float y, final Screen screen) {
        btn.setPosition(x, y);

        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener","Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                musicaFondo.stop(); // Para la reproducción de la música al entrar en la siguiente pantalla.
                jdj.setScreen(screen);
            }
        }); // Click y touch son equivalentes.

        this.escenaMenu.addActor(btn);
    }
    //metodo para crear todos los componentes del boton en uno
    private ImageButton crearBotonTodo(String texturaNormal, String texturaOnClick, float x, float y, final Screen screen) {

        TextureRegionDrawable boton = new TextureRegionDrawable(new TextureRegion(new Texture(texturaNormal)));
        TextureRegionDrawable botonClick = new TextureRegionDrawable(new TextureRegion(new Texture(texturaOnClick)));

        ImageButton btn = new ImageButton(boton, botonClick);
        float botonWitdh = btn.getWidth();
        float botonHeigth = btn.getHeight();
        btn.setPosition(x, y);

        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener","Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                musicaFondo.pause(); // Para la reproducción de la música al entrar en la siguiente pantalla.
                jdj.setScreen(screen);
            }
        }); // Click y touch son equivalentes.

        this.escenaMenu.addActor(btn);

        return btn;

    }

    // Método que se encarga de crear a la cámara a usar.
    private void crearCamara() {

        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {

        // Inicialización del fondo de pantalla.
        fondoMenu = new Texture("Pantalla principal.jpg");

        Gdx.gl.glClearColor(1,1,1,1); // Borra la pantalla con el color blanco en RGB.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined); // Escala los objetos.

        // Dibuja en pantalla el stage llamado escenaMenu.
        batch.begin();
        batch.draw(fondoMenu, 0,0);
        batch.end();
        escenaMenu.draw();

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

        musicaFondo.dispose();
        escenaMenu.dispose();
        fondoMenu.dispose();
        batch.dispose();
        
    }
}
