package objects;

import graphics.GraphicsLibrary;
import graphics.Textures;

import java.awt.*;

public class Bullet extends GameObject {
    private String objectName;
    private boolean lookingRight;

    public Bullet(int posX, int posY,int width, int height,int movedX, boolean lookingRight,String objectName) {
        super(posX, posY, ObjectID.Bullet, width, height, 0);
        this.objectName = objectName;
        this.lookingRight = lookingRight;
        setVelX(5);
    }

    public void collisions(){

    }

    @Override
    public void tick() {
        if(lookingRight){
            setPosX(getPosX()+getVelX());
        }else{
            setPosX(getPosX()-getVelX());
        }
    }

    @Override
    public void render(GraphicsLibrary g) {
        if(lookingRight){
            g.drawImage(Textures.getBulletTexture(objectName), (int) (getPosX()), (int) (getPosY()),(int)getWidth(),(int)getHeight());
        }else{
            g.drawImage(Textures.getBulletTexture(objectName), (int) (getPosX()), (int) (getPosY()),-(int)getWidth(),(int)getHeight());
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getPosX(), (int) getPosY(), (int) getPosX() + 8, (int) getPosY() + 8);
    }

    @Override
    public GameObject clone() {
        return new Bullet((int) posX, (int) posY, (int) width, (int) height, (int) movedX,lookingRight, objectName);
    }


}
