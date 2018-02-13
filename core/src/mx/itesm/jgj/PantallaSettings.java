package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.Style;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


class PantallaSettings implements Screen {

    private JudafaalsGreatAdventure jdj;

    // Camara
    private OrthographicCamera camara;
    private Viewport vista;

    // Batch
    private SpriteBatch batch;

    // Imágenes


    // Escena
    private Stage escenaSettings;

    // Constructor de la pantalla.
    public PantallaSettings(JudafaalsGreatAdventure JudafaalsGreatAdventure) {

        this.jdj = JudafaalsGreatAdventure;

    }

    @Override
    public void show() {

        crearCamara();
        crearEscena();
        batch = new SpriteBatch();
    }

    private void crearEscena() {

        escenaSettings = new Stage(vista);

        // Creación de las texturas del botón de sonido
        TextureRegionDrawable trdSonido = new TextureRegionDrawable(new TextureRegion(new Texture("sonido.png")));
        TextureRegionDrawable trdNoSonido = new TextureRegionDrawable(new TextureRegion(new Texture("noSonido.png")));
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("homeNegro.png")));
        TextureRegionDrawable trdBackOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("homeGris.png")));


        // Creación del botón
        final ImageButton btnSonido = new ImageButton(trdSonido);
        final ImageButton btnNoSonido = new ImageButton(trdNoSonido);
        final ImageButton btnTemp = new ImageButton(trdSonido);

        // Button Styles

        btnSonido.setPosition(ANCHO / 2 - btnSonido.getWidth()/2, ALTO / 2 - btnSonido.getHeight()/2);
        btnSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener", "Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                btnSonido.setStyle(btnNoSonido.getStyle());

            }
        });

        // Creción del botón back.
        ImageButton backButton = new ImageButton(trdBack, trdBackOnClick);
        backButton.setPosition(0, ALTO - backButton.getHeight());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("ClickListener", "Hizo click el usuario");
                // Cambia de pantalla, solo lo puede hacer 'juego' una escena no.
                jdj.setScreen(new MenuJudafaals(jdj));

            }
        });

        escenaSettings.addActor(btnSonido);
        escenaSettings.addActor(backButton);
        Gdx.input.setInputProcessor(escenaSettings);
    }

    private void crearCamara() {

        camara = new OrthographicCamera(MenuJudafaals.ANCHO, MenuJudafaals.ALTO);
        camara.position.set(MenuJudafaals.ANCHO/2, MenuJudafaals.ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(MenuJudafaals.ANCHO, MenuJudafaals.ALTO, camara);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        escenaSettings.draw();
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

        escenaSettings.dispose();
        batch.dispose();

    }
}
