package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by alfonsoalquicer on 3/6/18.
 */

public class Enemigo {


    private Animation animacion;
    private float x,y, width,height;
    private float timerAnimacion;


    public Enemigo(float x, float y){
        set(x,y);
        TextureRegion region=new TextureRegion(new Texture("FramesNaveER.png"));
        TextureRegion[][] frames=region.split(149,72);
        animacion=new Animation(0.2f,frames[0][0],frames[0][1],frames[0][2]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void render(SpriteBatch batch){

        timerAnimacion+= Gdx.graphics.getDeltaTime();
        TextureRegion frame=(TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame,x,y);
    }

    public void set(float x, float y){
        this.x=x;
        this.y=y;

    }

    public boolean estaColisionando(Personaje nave){


        //if(y>=nave.getY() && y <=nave.getY()+nave.getHeight())
        //if((x<=nave.getX()+nave.getWidth() && x>=nave.getX()-70)||(nave.getX()>=x && nave.getX()<=this.getWidth())){
        //  if((y<=nave.getY()+nave.getHeight() && y >= nave.getY())||(textura.getHeight()>=nave.getY() && y<=nave.getY()))
        //    return true;
        //}

        if((x+getWidth()>=nave.getX()+getWidth() && x<=nave.getX()+nave.getWidth())||(x+getWidth()>=nave.getX()&&x<=nave.getX())) {

            if ((y <= nave.getY() + nave.getHeight() && y + getHeight()-45 >= nave.getY() + nave.getHeight()) || (y + getHeight()-45 >= nave.getY() && nave.getY() >= y))
                return true;
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
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionWidth();
    }

    public float getHeight() {
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionHeight();
    }


    public void mover(float x, float y){
        this.x+=x;
        this.y+=y;
    }
}
