package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by fcamp on 10/04/2018.
 */

public class PantallaCargando extends Pantalla {

    private JudafaalsGreatAdventure jga;

    private float tiempo;

    // Texturas a usar.
    private Texture texturaRelojCarga;
    private Sprite spriteRelojCarga;
    private int screen;

    // Asset Manager
    private AssetManager assetManager;

    public PantallaCargando(JudafaalsGreatAdventure judafaalsGreatAdventure, int screen){
        this.jga = judafaalsGreatAdventure;
        this.assetManager = jga.getAssetManager();
        this.screen = screen;
    }

    @Override
    public void show() {
        tiempo = 0;
        texturaRelojCarga = new Texture("pruebas/cargando.png");
        spriteRelojCarga = new Sprite(texturaRelojCarga);
        spriteRelojCarga.setPosition(ANCHO/2 - spriteRelojCarga.getWidth()/2, ALTO/2 - spriteRelojCarga.getHeight()/2);
        cargarRecursos();
    }

    private void cargarRecursos() {
        switch(screen){
            case MENU:
                assetManager.load("Fondos/Pantalla principal.jpg", Texture.class);
                assetManager.load("Botones/BotonPlay.png", Texture.class);
                assetManager.load("Botones/BotonPlayPado.png", Texture.class);
                assetManager.load("Botones/BotonHelp.png", Texture.class);
                assetManager.load("Botones/BotonAyudaPado.png", Texture.class);
                assetManager.load("Botones/BotonInfo.png", Texture.class);
                assetManager.load("Botones/BotonMasPado.png", Texture.class);
                assetManager.load("Botones/BotonConfiguracionN.png", Texture.class);
                assetManager.load("Botones/BotonConfiguracionPado.png", Texture.class);
                break;
            case SETTINGS:
                assetManager.load("Fondos/FondoConfig.png", Texture.class);
                assetManager.load("Botones/sonido.png", Texture.class);
                assetManager.load("Botones/noSonido.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
            case NIVELES:
                assetManager.load("Fondos/Pantalla principal.jpg", Texture.class);
                assetManager.load("Naveinicio.png", Texture.class);
                assetManager.load("Botones/BotonNivelUno.png", Texture.class);
                assetManager.load("Botones/BotonNivelDos.png", Texture.class);
                assetManager.load("Botones/BotonNivelTres.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
            case AYUDA:
                assetManager.load("Fondos/PantallaAyudaB.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
            case ABOUT:
                assetManager.load("AboutFotos/fabianCamp.png", Texture.class);
                assetManager.load("AboutFotos/alfonsoAlquicer.png", Texture.class);
                assetManager.load("AboutFotos/darwinJomair.png", Texture.class);
                assetManager.load("AboutFotos/juanAguilar.png", Texture.class);
                assetManager.load("Fondos/FondoAcercaDe.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
        }
    }


    @Override
    public void render(float delta) {
        actualizarCarga();
        borrarPantalla();

        // Animación de la carga

        spriteRelojCarga.setRotation(spriteRelojCarga.getRotation() + 10);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        if(tiempo > 0.25f) {
            spriteRelojCarga.draw(batch);
        }
        batch.end();
    }

    private void actualizarCarga() {

        // Regresa true si ya terminó la carga
        if(assetManager.update()){
            switch(screen){
                case MENU:
                    jga.setScreen(new MenuJudafaals(jga));
                    break;
                case SETTINGS:
                    jga.setScreen(new PantallaSettings(jga));
                    break;
                case NIVELES:
                    jga.setScreen(new MenuNiveles(jga));
                    break;
                case ABOUT:
                    jga.setScreen(new PantallaAbout(jga));
                    break;
                case PRIMERNIVEL:
                    jga.setScreen(new PrimerNivel(jga));
                    break;
                case SEGUNDONIVEL:
                    jga.setScreen(new SegundoNivel(jga));
                    break;
                case TERCERNIVEL:
                    jga.setScreen(new TercerNivel(jga));
                    break;
                case AYUDA:
                    jga.setScreen(new PantallaAyuda(jga));
                    break;
            }
        }
        else{
            tiempo += Gdx.graphics.getDeltaTime();
            Gdx.app.log("Tiempo", String.valueOf(tiempo));
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
        texturaRelojCarga.dispose();
    }
}
