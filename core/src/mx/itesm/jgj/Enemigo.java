package mx.itesm.jgj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by alfonsoalquicer on 3/6/18.
 */

public class Enemigo {

    private float x;
    private float y;
    private Texture textura;
    private float width;
    private float height;

    public Enemigo(float x, float y ){
        this.x = x;
        this.y = y;
        textura = new Texture("pruebas/estructura.png");
    }

    public void render(SpriteBatch batch){

        batch.draw(textura,x,y);
    }

    public void set(float x, float y){
        this.x=x;
        this.y=y;

    }

    public boolean estaColisionando(Personaje nave) {
        Rectangle rectNave = new Rectangle(nave.getX(), nave.getY(), nave.getWidth(), nave.getHeight());
        Rectangle rectFuego = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if (rectNave.overlaps(rectFuego)) {
            return true;
        }
        if(this.getY()>PrimerNivel.ALTO/2)
        if(nave.getY()<this.getY()){

        }
        return false;

    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
