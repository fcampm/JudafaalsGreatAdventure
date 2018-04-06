package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

    // Texturas de la pantalla de settings.
    private Texture fondoSettings;
    private TextureRegionDrawable texturaSonido;
    private TextureRegionDrawable texturaNoSonido;
    private TextureRegionDrawable texturaHome;


    // Escenas de la pantalla de Settings.
    private Stage escenaSettings;

    public PantallaSettings(JudafaalsGreatAdventure judafaalsGreatAdventure) {
        this.jga = judafaalsGreatAdventure;
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
        final ImageButton btnSonido = new ImageButton(texturaSonido);
        final ImageButton btnNoSonido = new ImageButton(texturaNoSonido);
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
        ImageButton btnHome = new ImageButton(texturaHome);
        btnHome.setPosition(0, ALTO - btnHome.getHeight());
        btnHome.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new MenuJudafaals(jga));
            }
        });
        escenaSettings.addActor(btnHome);
    }

    private void cargarTexturas() {
        fondoSettings = new Texture("Fondos/FondoConfig.png");
        texturaSonido = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/sonido.png")));
        texturaNoSonido = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/noSonido.png")));
        texturaHome = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/FlechaAtras.png")));
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
        fondoSettings.dispose();
        escenaSettings.dispose();
        batch.dispose();
    }
}
