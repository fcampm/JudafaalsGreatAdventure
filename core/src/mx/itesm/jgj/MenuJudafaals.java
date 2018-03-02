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
        musicaFondo = Gdx.audio.newMusic(Gdx.files.getFileHandle("Musica/message.mp3", Files.FileType.Internal));
        musicaFondo.setVolume(volumen);
        musicaFondo.play();
        musicaFondo.setLooping(true);
    }

    private void crearMenu() {

        escenaMenu = new Stage(vista);
        ImageButton btnPlay = crearBotonTodo("Botones/BotonPlay.png", "Botones/BotonPlayPado.png", ANCHO/2 - 95, ALTO/2 - 85,  new menu3(jdj));
        ImageButton btnAyuda= crearBotonTodo("Botones/BotonAyuda3.2.png","Botones/BotonAyudaPado.png",ANCHO - 240,ALTO/2 -350, new PantallaAyuda(jdj));
        ImageButton btnMas = crearBotonTodo("Botones/BotonMas.png", "Botones/BotonMasPado.png", ANCHO/24 - 50, ALTO/2 -350, new PantallaMas(jdj));
        ImageButton btnSetting = crearBotonTodo("Botones/ajustes.png", "Botones/ajustesOnClick.png", 0, ALTO - 128, new PantallaSettings(jdj));
        Gdx.input.setInputProcessor(escenaMenu);
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
        fondoMenu = new Texture("Fondos/Pantalla principal.jpg");

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
