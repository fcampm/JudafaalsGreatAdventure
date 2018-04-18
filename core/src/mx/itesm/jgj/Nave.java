package mx.itesm.jgj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alfonsoalquicer on 3/2/18.
 */

public class Nave {

    private Texture texturanave;
    private float x,y;

    public Nave(float x, float y){
        texturanave= new Texture("nave2.png");    // ---->  Falta el sprite para nave
        this.x=x;
        this.y=y;

    }

    public void render(SpriteBatch batch){
        batch.draw(texturanave,x,y);
    }

    public void mover(float dx){
        x+= dx;
    }

    public int getWidth(){
        return texturanave.getWidth();
    }

    public float getY(){
        return y;
    }

    public float getX(){
        return x;
    }

    public Texture getTexturanave(){
        return this.texturanave;
    }


}
