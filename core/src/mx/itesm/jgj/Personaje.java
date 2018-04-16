package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jomai on 2/20/2018.
 */

public class   Personaje {
    private Animation animacionNormal,animacionSubir,animacionBajar, animacionSubirNormal,
            animacionNormalSubir, animacionBajarNormal,animacionNormalBajar, animacionChoque;
    private float x,y;
    private float timerAnimacion;

    private EstadoNave estadoNave;

    public Personaje(Texture textura){
        TextureRegion region=new TextureRegion(textura);
        //131,54

        TextureRegion[][] frames=region.split(131,54);
        animacionNormal=new Animation(0.2f,frames[0][0],frames[0][1],frames[0][2]);
        animacionNormal.setPlayMode(Animation.PlayMode.LOOP);
        animacionSubir=new Animation(0.2f,frames[0][3],frames[0][4],frames[0][5]);
        animacionSubir.setPlayMode(Animation.PlayMode.LOOP);
        animacionBajar=new Animation(0.2f,frames[0][6],frames[0][7],frames[0][8]);
        animacionBajar.setPlayMode(Animation.PlayMode.LOOP);
        animacionSubirNormal=new Animation(10.8f,frames[0][9],frames[0][10]);
        animacionSubirNormal.setPlayMode(Animation.PlayMode.NORMAL);
        animacionNormalSubir=new Animation(10.8f,frames[0][10],frames[0][9]);
        animacionNormalSubir.setPlayMode(Animation.PlayMode.NORMAL);
        animacionBajarNormal=new Animation(10.8f,frames[0][11],frames[0][11]);
        animacionBajarNormal.setPlayMode(Animation.PlayMode.NORMAL);
        animacionNormalBajar=new Animation(10.8f,frames[0][11],frames[0][11]);
        animacionNormalBajar.setPlayMode(Animation.PlayMode.NORMAL);
        animacionChoque=new Animation(10.8f,frames[0][11],frames[0][11]);
        animacionChoque.setPlayMode(Animation.PlayMode.NORMAL);
        x=Pantalla.ANCHO/5;
        y=Pantalla.ALTO/2;

        estadoNave= EstadoNave.NOMRMAL;
    }
    public void render(SpriteBatch batch){
        timerAnimacion+= Gdx.graphics.getDeltaTime();
        if(estadoNave==EstadoNave.NOMRMAL){
            TextureRegion frame=(TextureRegion) animacionNormal.getKeyFrame(timerAnimacion);
            batch.draw(frame,x,y);}
        else if (estadoNave==EstadoNave.SUBIENDO){

            TextureRegion frame=(TextureRegion) animacionSubir.getKeyFrame(timerAnimacion);
            batch.draw(frame,x,y);
        }
        else if(estadoNave==EstadoNave.BAJANDO){
            TextureRegion frame=(TextureRegion) animacionBajar.getKeyFrame(timerAnimacion);
            batch.draw(frame,x,y);
        }
        else if (estadoNave == EstadoNave.SUBIENDO_NORMAL) {
            TextureRegion frame=(TextureRegion) animacionSubirNormal.getKeyFrame(timerAnimacion);
            batch.draw(frame,x,y);
            estadoNave=EstadoNave.NOMRMAL;
        }
        else if (estadoNave == EstadoNave.BAJANDO_NORMAL) {
            TextureRegion frame=(TextureRegion) animacionBajarNormal.getKeyFrame(timerAnimacion);
            batch.draw(frame,x,y);
            estadoNave=EstadoNave.NOMRMAL;
        }
        else if (estadoNave == EstadoNave.NORMAL_SUBIENDO) {
            TextureRegion frame=(TextureRegion) animacionSubir.getKeyFrame(timerAnimacion);
            batch.draw(frame, x, y);
            estadoNave=EstadoNave.SUBIENDO;

        }
        else if (estadoNave == EstadoNave.NORMAL_BAJANDO) {
            TextureRegion frame=(TextureRegion) animacionNormalBajar.getKeyFrame(timerAnimacion);
            batch.draw(frame,x,y);
            estadoNave=EstadoNave.BAJANDO;
        }
        else if (estadoNave==EstadoNave.CHOQUE){
            TextureRegion frame=(TextureRegion) animacionChoque.getKeyFrame(timerAnimacion);

            //frame.flip(!frame.isFlipX(), false);
            batch.draw(frame,x,y);

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

    public void normal() {
        if(estadoNave==EstadoNave.SUBIENDO){
            estadoNave=EstadoNave.SUBIENDO_NORMAL;
        }
        else if(estadoNave==EstadoNave.BAJANDO){
            estadoNave=EstadoNave.BAJANDO_NORMAL;
        }
        else if(estadoNave==EstadoNave.CHOQUE){
            estadoNave=EstadoNave.NOMRMAL;
        }

    }

    public void subiendo() {
        if(estadoNave!=EstadoNave.CHOQUE){


        estadoNave=EstadoNave.NORMAL_SUBIENDO;
    }}

    public void bajando() {
        if(estadoNave!=EstadoNave.CHOQUE) {

            estadoNave = EstadoNave.NORMAL_BAJANDO;
        }
    }
    public void chocar(){
        estadoNave=EstadoNave.CHOQUE;
    }

    public mx.itesm.jgj.EstadoNave getEstado(){
        return estadoNave;
    }


}
