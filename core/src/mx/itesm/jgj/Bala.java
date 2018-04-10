package mx.itesm.jgj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alf on 10/04/2018.
 */

public class Bala {

    private float x;
    private float y;
    private Texture texturaBala;

    private boolean activa = false;

    public Bala(float x, float y){
        this.x=x;
        this.y=y;
        activa = false;
    }

    public void render(SpriteBatch batch){
        batch.draw(texturaBala, x, y);
    }

    public void mover(float dx, float dy) {

        x += dx;
        y += dy;
        if (y>Pantalla.ALTO) {
            activa = false;
        }

    }



    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }





    public boolean isActiva() {
        return activa;
    }



    public boolean estaColisionando(Personaje nave) {

        if (x>=nave.getX() && x<=nave.getX()+nave.getWidth()) {

            if (y>=nave.getY() && y<=nave.getY()+nave.getHeight()) {

                return true;

            }

        }

        return false;

    }



    public void setActiva(boolean activa) {

        this.activa = activa;

    }

}

