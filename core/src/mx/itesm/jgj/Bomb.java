package mx.itesm.jgj;

/**
 * Created by mende on 17/04/2018.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by mende on 16/04/2018.
 */

public class Bomb {

    private float x;
    private float y;
    private Texture textura;

    public Bomb(float x, float y){
        this.x=x;
        this.y=y;
        textura= new Texture("pruebas/bomb.png" );
    }

    public void render(SpriteBatch batch){
        batch.draw(textura,x,y);
    }

    public void set(float x, float y){
        this.x=x;
        this.y=y;

    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }


    public boolean estaColisionando(Personaje nave){
        if(x>=nave.getX() && x<= nave.getX()+nave.getWidth()){
            if(y>=nave.getY() && y <=nave.getY()+nave.getHeight())
                return true;
        }
        return false;
    }

    public void mover(int x, int y, boolean moviendo){
        if(moviendo) {
            this.x += x;
            this.y += y;
        }
    }


}
