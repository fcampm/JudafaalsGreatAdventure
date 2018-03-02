package mx.itesm.jgj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alfonsoalquicer on 3/2/18.
 */

public class Enemigo {

    private Texture texturaEnemigo;

    public float x,y;

    public Enemigo(float x, float y){
        texturaEnemigo = new Texture("images.png");    //   ----->    Falta el sprite para enemigo
        this.x=x;
        this.y=y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void render(SpriteBatch batch){

        batch.begin();
        batch.draw(texturaEnemigo,x,y);
        batch.end();

    }

    public void mover(float dx, float dy){
        x-=dx;
        y-=dy;
    }

}
