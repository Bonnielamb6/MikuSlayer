package objects;

import graphics.Animation;
import graphics.Camera;
import graphics.GraphicsLibrary;
import graphics.Textures;
import main.Game;
import org.w3c.dom.Text;
import tools.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Player extends GameObject {
    private Animation walkingAnimation;
    private int hp = 3;
    private int running = 1;//1 means is not running, 2 means its running
    private boolean walkingRight;
    private boolean walkingLeft;
    private boolean jumping;
    GraphicsLibrary graphics;
    private LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<Bullet> bulletsToDelete = new LinkedList<>();
    private Handler handler;

    public Player(float x, float y, Handler handler) {
        super(x, y, ObjectID.Player, 32, 32, 0);
        this.handler = handler;
        walkingAnimation = new Animation(15,
                Textures.getMikuTextures("mikuRight"),
                Textures.getMikuTextures("mikuRightWalking")
        );
    }

    @Override
    public void tick() {
        movement();
        gravity();
        colisions();
        walkingAnimation.animate();
        for(Bullet temp:bulletsToDelete){
            bullets.remove(temp);
        }
    }

    @Override
    public void render(GraphicsLibrary g) {
        graphics = g;
        if (getVelX() > 0) {
            walkingAnimation.drawSprite(g, (int) getPosX(), (int) getPosY());
        } else if (getVelX() < 0) {
            walkingAnimation.drawReversedSprite(g, (int) getPosX(), (int) getPosY());
        } else {
            g.drawImage(Textures.getMikuTextures("miku"), (int) getPosX(), (int) getPosY());
        }
        for (Bullet temp : bullets) {
            temp.render(graphics);
        }
        showBounds(g);
    }

    public void movement() {
        if (walkingRight || walkingLeft) {
            if (walkingRight) {
                setVelX(getVelX() + 0.1f);
                if (getVelX() > 3 * running) {
                    setVelX(3 * running);
                }
            } else {
                setVelX(getVelX() - 0.1f);
                if (getVelX() < -3 * running) {
                    setVelX(-3 * running);
                }
            }
        } else {
            setVelX(0);
        }
        setPosX(getVelX() + getPosX());
        setPosY(getVelY() + getPosY());

        for (Bullet temp : bullets) {
            temp.tick();
            if (temp.getPosX() < 0 || temp.getPosX() > getPosX()+Game.getMAX_RENDER()) {

                bulletsToDelete.add(temp);
                temp.setActive(false);
            }
        }

    }

    public void colisions() {
        for (GameObject temp : handler.getGameObjects()) {
            switch (temp.getId()) {
                case Block:
                case Floor:
                    solidColision(temp);
                    break;
                case Enemy:
                        enemyCollision(temp);
                default:
                    break;
            }
        }
    }

    public void solidColision(GameObject tempObject) {
        if (getBounds().intersects(tempObject.getBounds())) {
            setPosY(tempObject.getPosY() - 64);
            setVelY(0);
            jumping = false;
        }
        if (getBoundsRight().intersects(tempObject.getBounds())) {
            setPosX(tempObject.getPosX() - getWidth()-18);
        }
        if (getBoundsLeft().intersects(tempObject.getBounds())) {
            setPosX(tempObject.getPosX() + getWidth()-18);
        }
        if (getBoundsTop().intersects(tempObject.getBounds())) {
            setVelY(1);
        }
        if (!(getVelY() >= 0)) {
            jumping = true;
        }
    }

    public void enemyCollision(GameObject tempObject){
        if(getBoundsTop().intersects(tempObject.getBounds())){
            gotHit();
        }
    }

    public void shoot() {
        Bullet tempBullet = new Bullet((int) (getPosX() + getWidth()), (int) (getPosY() + getHeight() / 2), 8, 8, 0, walkingRight, "bullet");
        tempBullet.setHandler(handler);
        bullets.add(tempBullet);

    }

    @Override
    public Rectangle getBounds() {//BOTTOM RECTANGLE
        return new Rectangle(
                (int) (getPosX() + getWidth() / 2 - getWidth() / 4),
                (int) (getPosY() + getHeight() / 2),
                (int) (getWidth()),
                (int) (getHeight()) + 16);

    }

    public Rectangle getBoundsTop() {
        return new Rectangle(
                (int) (getPosX() + getWidth() / 2 - getWidth() / 4),
                (int) (getPosY() + 10),
                (int) (getWidth()),
                16);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(
                (int) (getPosX() + getWidth() + 20),
                (int) (getPosY() + 20),
                1,
                (int) (getHeight()));
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(
                (int) (getPosX() + 4),
                (int) (getPosY() + 20),
                1,
                (int) (getHeight()));
    }

    @Override
    public GameObject clone() {
        return null;
    }

    public void gotHit() {
        setPosY(20);
        setPosX(getPosX()-100);
        hp--;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setJumping(Boolean jum) {
        jumping = jum;
    }

    public boolean hasJumped() {
        return jumping;
    }


    public int isRunning() {
        return running;
    }

    public void setRunning(int running) {
        this.running = running;
    }

    public boolean isWalkingRight() {
        return walkingRight;
    }

    public void setWalkingRight(boolean walkingRight) {
        this.walkingRight = walkingRight;
    }

    public boolean isWalkingLeft() {
        return walkingLeft;
    }

    public void setWalkingLeft(boolean walkingLeft) {
        this.walkingLeft = walkingLeft;
    }

    public boolean isJumping() {
        return jumping;
    }


    private void showBounds(GraphicsLibrary g) {
        g.drawRectangle(getBounds(), Color.green);
        g.drawRectangle(getBoundsLeft(), Color.red);
        g.drawRectangle(getBoundsRight(), Color.yellow);
        g.drawRectangle(getBoundsTop(), Color.BLACK);


    }
}
