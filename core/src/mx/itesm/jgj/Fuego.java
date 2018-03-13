package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by alfonsoalquicer on 3/6/18.
 */

public class Fuego {

    private static final float G=99.81f;
    private static final float VY = 400;
    private Animation animacion;
    private float x,y;
    private float timerAnimacion;

    private EstadoNave estadoSalto;
    private final float MAX_ALTURA=150;
    private float yOriginal;

    public Fuego(Texture textura){
        TextureRegion region=new TextureRegion(textura);
        TextureRegion[][] frames=region.split(146,153);
        animacion=new Animation(0.001f,frames[0][0],frames[0][1],frames[0][2]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        x=Pantalla.ANCHO/5;
        y=-2;
        estadoSalto= EstadoNave.NOMRMAL;
    }
    public void render(SpriteBatch batch){
        timerAnimacion+= Gdx.graphics.getDeltaTime();
        TextureRegion frame=(TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame,x,y);
    }
    public void actualizar(float delta){

        if(estadoSalto== EstadoNave.SUBIENDO){
            setY(y+VY*delta-0.5f*G*delta*delta);
            if(y>=yOriginal+MAX_ALTURA){
                estadoSalto= EstadoNave.BAJANDO;

            }}
        else if(estadoSalto== EstadoNave.BAJANDO){
            setY(y-VY*delta);
            if(y<=yOriginal){
                y=yOriginal;
                estadoSalto= EstadoNave.NOMRMAL;
            }
        }
    }

    public void setY(float y){
        this.y=y;
    }
    public void saltar(){
        estadoSalto= EstadoNave.SUBIENDO;
        yOriginal=y;
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
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionWidth();
    }

    public float getHeight() {
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionHeight();
    }

    public boolean estaColisionando(Personaje nave) {
        Rectangle rectNave = new Rectangle(nave.getX(),nave.getY(),nave.getWidth(),nave.getHeight());
        Rectangle rectFuego = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        if (rectNave.overlaps(rectFuego)) {
                return true;
        }

        return false;

    }

}

