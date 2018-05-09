package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import javax.xml.soap.Text;

/**
 * Created by fcamp on 10/04/2018.
 */

public class PantallaCargando extends Pantalla {

    private JudafaalsGreatAdventure jga;

    private float tiempo;

    // Texturas a usar.
    private Texture splashArt;
    private Texture texturaRelojCarga;
    private Sprite spriteRelojCarga;
    private int screen;
    private boolean counter = true;

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
        splashArt = new Texture("Fondos/SplashArt.png");
        spriteRelojCarga = new Sprite(texturaRelojCarga);
        spriteRelojCarga.setPosition(ANCHO/2 - spriteRelojCarga.getWidth()/2, ALTO/2 - spriteRelojCarga.getHeight()/2);
        cargarRecursos();
    }

    private void cargarRecursos() {
        switch(screen){
            case MENU:
            case SPLASHART:
                // Cargamos las texturas a usar.
                assetManager.load("Fondos/Pantalla principal.jpg", Texture.class);
                assetManager.load("Botones/BotonPlay.png", Texture.class);
                assetManager.load("Botones/BotonPlayPado.png", Texture.class);
                assetManager.load("Botones/BotonHelp.png", Texture.class);
                assetManager.load("Botones/BotonAyudaPado.png", Texture.class);
                assetManager.load("Botones/BotonInfo.png", Texture.class);
                assetManager.load("Botones/BotonMasPado.png", Texture.class);
                assetManager.load("Botones/BotonConfiguracionN.png", Texture.class);
                assetManager.load("Botones/BotonConfiguracionPado.png", Texture.class);

                //Cargamos la música a usar.
                assetManager.load("Musica/message.mp3", Music.class);
                break;
            case SETTINGS:
                // Cargamos las texturas a usar.
                assetManager.load("Fondos/FondoConfig.png", Texture.class);
                assetManager.load("Botones/sonido.png", Texture.class);
                assetManager.load("Botones/noSonido.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
            case NIVELES:
                // Cargamos las texturas a usar.
                assetManager.load("Fondos/Pantalla principal.jpg", Texture.class);
                assetManager.load("Naveinicio.png", Texture.class);
                assetManager.load("Botones/BotonNivelUno.png", Texture.class);
                assetManager.load("Botones/BotonNivelDos.png", Texture.class);
                assetManager.load("Botones/NoBotonNivelDos.png", Texture.class);
                assetManager.load("Botones/BotonNivelTres.png", Texture.class);
                assetManager.load("Botones/NoBotonNivelTres.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
            case AYUDA:
                // Cargamos las texturas a usar.
                assetManager.load("Fondos/PantallaAyudaB.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
            case ABOUT:
                // Cargamos las texturas a usar.
                assetManager.load("AboutFotos/fabianCamp.png", Texture.class);
                assetManager.load("AboutFotos/alfonsoAlquicer.png", Texture.class);
                assetManager.load("AboutFotos/darwinJomair.png", Texture.class);
                assetManager.load("AboutFotos/juanAguilar.png", Texture.class);
                assetManager.load("Fondos/FondoAcercaDe.png", Texture.class);
                assetManager.load("Botones/FlechaAtras.png", Texture.class);
                break;
            case PRIMERNIVEL:
                // Cargamos las texturas a usar.
                assetManager.load("pruebas/pausaa.png", Texture.class);
                assetManager.load("PrimerNivel/NaveUReducida.png", Texture.class);
                assetManager.load("PrimerNivel/flechas2.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonPlayN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("PrimerNivel/YouWin.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("PrimerNivel/youFailed.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("Botones/nextLevel.png", Texture.class);

                // Cargamos todos los sonidos.
                assetManager.load("Musica/level1.mp3", Music.class);
                assetManager.load("Musica/choque.mp3", Sound.class);
                assetManager.load("Musica/levelUp.wav", Sound.class);

                // Cargamos el mapa y sus componentes.
                assetManager.load("PrimerNivel/prueba1.tmx", TiledMap.class);
                assetManager.load("PrimerNivel/progresoBarra.png", Texture.class);
                assetManager.load("PrimerNivel/progresoIndicador.png", Texture.class);
                break;
            case SEGUNDONIVEL:
                // Cargamos las texturas a usar.
                assetManager.load("pruebas/pausaa.png", Texture.class);
                assetManager.load("PrimerNivel/flechas2.png", Texture.class);
                assetManager.load("PrimerNivel/NaveUReducida.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonPlayN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("PrimerNivel/YouWin.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("PrimerNivel/youFailed.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("Botones/nextLevel.png", Texture.class);

                // Cargamos todos los sonidos.
                assetManager.load("Musica/choque.mp3", Sound.class);
                assetManager.load("Musica/levelUp.wav", Sound.class);
                assetManager.load("Musica/level1.mp3", Music.class);
                assetManager.load("Musica/bombTaked.mp3", Music.class);

                // Cargamos el mapa y sus componentes.
                assetManager.load("SegundoNivel/mapaNivelDos.tmx", TiledMap.class);
                assetManager.load("PrimerNivel/progresoBarra.png", Texture.class);
                assetManager.load("PrimerNivel/progresoIndicador.png", Texture.class);
                break;
            case TERCERNIVEL:
                // Cargamos las texturas a usar.
                assetManager.load("pruebas/pausaa.png", Texture.class);
                assetManager.load("PrimerNivel/flechas2.png", Texture.class);
                assetManager.load("PrimerNivel/NaveUReducida.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonPlayN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("PrimerNivel/YouWin.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);
                assetManager.load("PrimerNivel/youFailed.png", Texture.class);
                assetManager.load("Botones/BotonExitN.png", Texture.class);
                assetManager.load("Botones/BotonReinicioN.png", Texture.class);

                // Cargamos todos los sonidos.
                assetManager.load("Musica/choque.mp3", Sound.class);
                assetManager.load("Musica/levelUp.wav", Sound.class);
                assetManager.load("Musica/level1.mp3", Music.class);

                // Cargamos el mapa y sus componentes.
                assetManager.load("PrimerNivel/prueba2.tmx", TiledMap.class);
                assetManager.load("PrimerNivel/progresoBarra.png", Texture.class);
                assetManager.load("PrimerNivel/progresoIndicador.png", Texture.class);
                break;
        }
    }


    @Override
    public void render(float delta) {
        actualizarCarga();
        if (screen == Pantalla.SPLASHART) {
            borrarPantalla(25/255f,50/255f,140/255f);
            batch.setProjectionMatrix(camara.combined);

            batch.begin();
            batch.draw(splashArt, ANCHO/2 - splashArt.getWidth()/2, ALTO/2 - splashArt.getHeight()/2);
            batch.end();
        }
        else {
            borrarPantalla();

            // Animación de la carga

            spriteRelojCarga.setRotation(spriteRelojCarga.getRotation() + 10);

            batch.setProjectionMatrix(camara.combined);

            batch.begin();
            if (tiempo > 0.25f) {
                spriteRelojCarga.draw(batch);
            }
            batch.end();
        }
    }

    private void actualizarCarga() {

        // Regresa true si ya terminó la carga
        if(assetManager.update()){
            switch(screen){
                case MENU:
                case SPLASHART:
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
