package mx.itesm.jgj;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private JudafaalsGreatAdventure jdj;

    // Fondo del menú principal
    private Texture fondoMenu;

    // Escenas
    private Stage escenaMenu;

    // Música de fondo.
    private Music musicaFondo;


    public MenuJudafaals(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jdj = judafaalsGreatAdventure;
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
        fondoMenu = new Texture("Fondos/Pantalla principal.jpg");
    }

    private void crearMusica() {
        float volumen = 0.5f;
        musicaFondo = Gdx.audio.newMusic(Gdx.files.getFileHandle("Musica/message.mp3", Files.FileType.Internal));
        musicaFondo.setVolume(volumen);
        musicaFondo.play();
        musicaFondo.setLooping(true);
    }

    private void crearMenu() {

        escenaMenu = new Stage(vista);
        ImageButton btnPlay = crearBotonTodo("Botones/BotonPlay.png", "Botones/BotonPlayPado.png", ANCHO/2 - 95, ALTO/2 - 85,  new MenuNiveles(jdj));
        ImageButton btnAyuda= crearBotonTodo("Botones/BotonAyuda3.2.png","Botones/BotonAyudaPado.png",ANCHO - 240,ALTO/2 -350, new PantallaAyuda(jdj));
        ImageButton btnMas = crearBotonTodo("Botones/BotonMas.png", "Botones/BotonMasPado.png", ANCHO/24 - 50, ALTO/2 -350, new PantallaMas(jdj));
        ImageButton btnSetting = crearBotonTodo("Botones/ajustes.png", "Botones/ajustesOnClick.png", 0, ALTO - 128, new PantallaSettings(jdj));
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
        fondoMenu.dispose();
        batch.dispose();

        
    }
}
