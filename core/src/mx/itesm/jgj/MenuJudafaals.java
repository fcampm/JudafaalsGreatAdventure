package mx.itesm.jgj;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/*
* Participantes del código:
* 1. Fabián Camp Mussa - A10378565.
* 2. Darwin Jomair Chavez Salas - A01373791
* 3. Juan José Aguilar Hernández - A01377413
* 4. Alfonso Alquícer Méndez - A01373252
* */


class MenuJudafaals extends Pantalla {

    // Variables de instancia
    private JudafaalsGreatAdventure jga;

    // Texturas a usar del menu.
    private Texture fondoMenu;
    private Texture texturaPlay, texturaPlayOnClick;
    private Texture texturaAyuda, texturaAyudaOnClick;
    private Texture texturaAcercaDe, texturaAcercaDeOnClick;
    private Texture texturaSettings, texturaSettingsOnClick;

    //Preferencias
    private Preferences soundPreferences = Gdx.app.getPreferences("usersPreferences");

    // Escenas
    private Stage escenaMenu;

    // Música de fondo.
    private Music musicaFondo;

    // assetManager a usar.
    private final AssetManager assetManager;


    public MenuJudafaals(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jga = judafaalsGreatAdventure;
        assetManager = jga.getAssetManager();
    }

    // Método que se encarga de mostrar en la pantalla.
    @Override
    public void show() {
        cargarTexturas();
        crearMenu();
        crearMusica();
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void cargarTexturas() {
        fondoMenu = assetManager.get("Fondos/Pantalla principal.jpg");
        texturaPlay = assetManager.get("Botones/BotonPlay.png");
        texturaPlayOnClick = assetManager.get("Botones/BotonPlayPado.png");
        texturaAyuda = assetManager.get("Botones/BotonHelp.png");
        texturaAyudaOnClick = assetManager.get("Botones/BotonAyudaPado.png");
        texturaAcercaDe = assetManager.get("Botones/BotonInfo.png");
        texturaAcercaDeOnClick = assetManager.get("Botones/BotonMasPado.png");
        texturaSettings = assetManager.get("Botones/BotonConfiguracionN.png");
        texturaSettingsOnClick = assetManager.get("Botones/BotonConfiguracionPado.png");
    }

    private void crearMusica() {
        float volumen = 0.5f;
        musicaFondo = assetManager.get("Musica/message.mp3");
        musicaFondo.setVolume(volumen);
        boolean musicaActivada = soundPreferences.getBoolean("soundOn");
        if(musicaActivada) {
            musicaFondo.play();
            musicaFondo.setLooping(true);
        }
    }

    private void crearMenu() {

        escenaMenu = new Stage(vista);
        ImageButton btnPlay = crearBotonTodo(texturaPlay, texturaPlayOnClick, ANCHO/2 - 95, ALTO/2 - 85,  new PantallaCargando(jga, Pantalla.NIVELES));
        ImageButton btnAyuda= crearBotonTodo(texturaAyuda, texturaAyudaOnClick,ANCHO - 240,ALTO/2 -350, new PantallaCargando(jga, Pantalla.AYUDA));
        ImageButton btnMas = crearBotonTodo(texturaAcercaDe, texturaAcercaDeOnClick, ANCHO/24 - 50, ALTO/2 -350, new PantallaCargando(jga, Pantalla.ABOUT));
        ImageButton btnSetting = crearBotonTodo(texturaSettings, texturaSettingsOnClick, ANCHO/2 - 72, 50, new PantallaCargando(jga, Pantalla.SETTINGS));
    }
    //metodo para crear todos los componentes del boton en uno{
    private ImageButton crearBotonTodo(Texture texturaNormal, Texture texturaOnClick, float x, float y, final Screen screen) {

        TextureRegionDrawable boton = new TextureRegionDrawable(new TextureRegion(texturaNormal));
        TextureRegionDrawable botonClick = new TextureRegionDrawable(new TextureRegion(texturaOnClick));

        ImageButton btn = new ImageButton(boton, botonClick);
        float botonWitdh = btn.getWidth();
        float botonHeigth = btn.getHeight();
        btn.setPosition(x, y);

        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                musicaFondo.pause(); // Para la reproducción de la música al entrar en la siguiente pantalla.
                jga.setScreen(screen);
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
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                musicaFondo.stop(); // Para la reproducción de la música al entrar en la siguiente pantalla.
                jga.setScreen(screen);
            }
        }); // Click y touch son equivalentes.

        this.escenaMenu.addActor(btn);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined); // Escala los objetos.

        // Dibuja en pantalla el stage llamado escenaMenu.
        batch.begin();
        batch.draw(fondoMenu, 0,0);
        batch.end();
        escenaMenu.draw();

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
        batch.dispose();
        assetManager.unload("Fondos/Pantalla principal.jpg");
        assetManager.unload("Botones/BotonPlay.png");
        assetManager.unload("Botones/BotonPlayPado.png");
        assetManager.unload("Botones/BotonHelp.png");
        assetManager.unload("Botones/BotonAyudaPado.png");
        assetManager.unload("Botones/BotonInfo.png");
        assetManager.unload("Botones/BotonMasPado.png");
        assetManager.unload("Botones/BotonConfiguracionN.png");
        assetManager.unload("Botones/BotonConfiguracionPado.png");
        assetManager.unload("Musica/message.mp3");
        
    }
}
