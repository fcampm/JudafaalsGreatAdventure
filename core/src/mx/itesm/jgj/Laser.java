package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jomai on 4/16/2018.
 */

public class Laser {
    private Animation animacionNormal,animacionDisparando;
    private float x,y;
    private float timerAnimacion;
    private int tipo;

    private EstadoLaser estadoLaser;

    public Laser(Texture textura, float x, float y,int tipo){
        TextureRegion region=new TextureRegion(textura);
        if(tipo==1) {
            TextureRegion[][] frames = region.split(1300, 500);
            animacionNormal = new Animation(0.2f, frames[0][0], frames[0][1], frames[0][2]);
            animacionNormal.setPlayMode(Animation.PlayMode.LOOP);
            animacionDisparando = new Animation(0.2f, frames[0][3], frames[0][4], frames[0][5]);
            animacionDisparando.setPlayMode(Animation.PlayMode.LOOP);
        }
        else if(tipo==2){
            TextureRegion[][] frames = region.split(1300, 200);
            animacionNormal = new Animation(0.2f, frames[0][0], frames[0][1], frames[0][2]);
            animacionNormal.setPlayMode(Animation.PlayMode.LOOP);
            animacionDisparando = new Animation(0.2f, frames[0][3], frames[0][4], frames[0][5]);
            animacionDisparando.setPlayMode(Animation.PlayMode.LOOP);
        }
        else if(tipo==3){
            TextureRegion[][] frames = region.split(1300, 100);
            animacionNormal = new Animation(0.2f, frames[0][0], frames[0][1], frames[0][2]);
            animacionNormal.setPlayMode(Animation.PlayMode.LOOP);
            animacionDisparando = new Animation(0.2f, frames[0][3], frames[0][4], frames[0][5]);
            animacionDisparando.setPlayMode(Animation.PlayMode.LOOP);
        }
        estadoLaser=EstadoLaser.Vacio;
        this.x=x;
        this.y=y;

    }
    public void render(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        if (estadoLaser == EstadoLaser.Cargando) {
            TextureRegion frame = (TextureRegion) animacionNormal.getKeyFrame(timerAnimacion);
            batch.draw(frame, x, y);
        } else if (estadoLaser == EstadoLaser.Disparando) {

            TextureRegion frame = (TextureRegion) animacionDisparando.getKeyFrame(timerAnimacion);
            batch.draw(frame, x, y);
        }
    }
    public void setY(float y){
        this.y=y;
    }

    public float getX(){
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }


    public float getWidth() {
        return ((TextureRegion) animacionNormal.getKeyFrame(0)).getRegionWidth();
    }

    public float getHeight() {
        return ((TextureRegion) animacionNormal.getKeyFrame(0)).getRegionHeight();
    }


    public EstadoLaser getEstado(){
        return estadoLaser;
    }

    enum EstadoLaser {
        Vacio,
        Cargando,
        Disparando,

    }

}
