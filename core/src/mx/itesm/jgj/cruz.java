package mx.itesm.jgj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alfonsoalquicer on 3/6/18.
 */

public class cruz {

    private float x;
    private float y;
    private Texture textura;

    public cruz (float x, float y){
        this.x=x;
        this.y=y;
        textura= new Texture("PrimerNivel/corazon.png" );
    }

    public void render(SpriteBatch batch){
        batch.draw(textura,x,y);
    }

    public void set(float x, float y){
        this.x=x;
        this.y=y;

    }

    public boolean estaColisionando(Personaje nave){
        if(x>=nave.getX() && x<= nave.getX()+nave.getWidth()){
            if(y>=nave.getY() && y <=nave.getY()+nave.getHeight())
                return true;
        }
        return false;
    }

}
