package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.Style;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


class PantallaSettings extends Pantalla {

    private JudafaalsGreatAdventure jga;

    private final AssetManager assetManager;

    // Texturas de la pantalla de settings.
    private Texture fondoSettings;
    private Texture texturaSonido;
    private Texture texturaNoSonido;
    private Texture texturaAtras;
    private TextureRegionDrawable imagenSonido;
    private TextureRegionDrawable imagenNoSonido;
    private TextureRegionDrawable imagenAtras;


    // Escenas de la pantalla de Settings.
    private Stage escenaSettings;

    public PantallaSettings(JudafaalsGreatAdventure judafaalsGreatAdventure) {
        this.jga = judafaalsGreatAdventure;
        assetManager = jga.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscena();
        Gdx.input.setInputProcessor(escenaSettings);
    }

    private void crearEscena() {
        escenaSettings = new Stage(vista);

        // Creación de la animación de los botones de sonido y no sonido.
        final ImageButton btnSonido = new ImageButton(imagenSonido);
        final ImageButton btnNoSonido = new ImageButton(imagenNoSonido);
        btnSonido.setPosition(ANCHO/2 - btnSonido.getWidth()/2, ALTO/2 - btnSonido.getHeight()/2);
        btnNoSonido.setPosition(ANCHO/2 - btnSonido.getWidth()/2, ALTO/2 - btnSonido.getHeight()/2);
        btnSonido.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                btnSonido.remove();
                escenaSettings.addActor(btnNoSonido);
            }
        });
        escenaSettings.addActor(btnSonido);

        btnNoSonido.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                btnNoSonido.remove();
                escenaSettings.addActor(btnSonido);
            }
        });

        // Creación del botón home que te lleva a MenuJudafaals.
        ImageButton btnHome = new ImageButton(imagenAtras);
        btnHome.setPosition(0, ALTO - btnHome.getHeight());
        btnHome.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new PantallaCargando(jga, Pantalla.MENU));
            }
        });
        escenaSettings.addActor(btnHome);
    }

    private void cargarTexturas() {
        fondoSettings = assetManager.get("Fondos/FondoConfig.png");
        texturaSonido = assetManager.get("Botones/sonido.png");
        texturaNoSonido = assetManager.get("Botones/noSonido.png");
        texturaAtras = assetManager.get("Botones/FlechaAtras.png");
        imagenSonido = new TextureRegionDrawable(new TextureRegion(texturaSonido));
        imagenNoSonido = new TextureRegionDrawable(new TextureRegion(texturaNoSonido));
        imagenAtras = new TextureRegionDrawable(new TextureRegion(texturaAtras));
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoSettings,0,0);
        batch.end();
        escenaSettings.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        escenaSettings.dispose();
        batch.dispose();
        assetManager.unload("Fondos/FondoConfig.png");
        assetManager.unload("Botones/sonido.png");
        assetManager.unload("Botones/noSonido.png");
        assetManager.unload("Botones/FlechaAtras.png");
    }
}
