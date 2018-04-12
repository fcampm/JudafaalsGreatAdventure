package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


public class PantallaAyuda extends Pantalla {

    private final JudafaalsGreatAdventure jga;
    private AssetManager assetManager;

    // Creación de las texturas a utilizar en la pantalla.
    private Texture ayudaImg;
    private Texture texturaBack;
    private TextureRegionDrawable imagenBack;

    // Creación de la escena de la PantallaAyuda.
    private Stage escenaPantallaAyuda;

    public PantallaAyuda(JudafaalsGreatAdventure judafaalsGreatAdventure) {
        this.jga = judafaalsGreatAdventure;
        assetManager = jga.getAssetManager();
    }


    @Override
    public void show() {
        cargarTexturas();
        crearEscenaAyuda();
        Gdx.input.setInputProcessor(escenaPantallaAyuda);
    }

    private void cargarTexturas() {
        ayudaImg = assetManager.get("Fondos/PantallaAyudaB.png");
        texturaBack = assetManager.get("Botones/FlechaAtras.png");
        imagenBack = new TextureRegionDrawable(new TextureRegion(texturaBack));
    }

    private void crearEscenaAyuda() {
        escenaPantallaAyuda = new Stage(vista);

        // Creación del botón back.
        ImageButton btnBack = new ImageButton(imagenBack);
        btnBack.setPosition(25, ALTO - 30 - btnBack.getHeight());
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new MenuJudafaals(jga));
            }
        });
        escenaPantallaAyuda.addActor(btnBack);
    }


    @Override
    public void render(float delta) {
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(ayudaImg,0,0);
        batch.end();
        escenaPantallaAyuda.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        batch.dispose();
        escenaPantallaAyuda.dispose();
        assetManager.unload("Fondos/PantallaAyudaB.png");
        assetManager.unload("Botones/FlechaAtras.png");
    }

}
