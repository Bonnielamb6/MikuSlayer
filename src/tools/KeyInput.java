package tools;

import objects.Bullet;
import objects.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter {

    private Player player;

    public KeyInput(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(key);
        /*KEY CODES
        87=W
        65= A
        83=S
        68=D
        69=E
        17=CTRL
        */
        switch (key) {
            case 87://W
                if (!player.isJumping()) {
                    player.setVelY(-12);
                    player.setJumping(true);
                }
                break;
            case 65://A
                player.setWalkingLeft(true);
                break;
            case 83://S
                if (player.isJumping()) {
                    player.setVelY(12);
                }
                break;
            case 68://D
                player.setWalkingRight(true);
                break;
            case 69://E
                //HIT, IF MY TIME LETS ME
                break;
            case 17://CTRL
                player.setRunning(2);
                break;
            default:
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        /*KEY CODES
        87=W
        65= A
        83=S
        68=D
        69=E
        17=CTRL
        */
        switch (key) {
            case 87://W
                if (!player.isJumping()) {
                    player.setVelY(-12);
                    player.setJumping(false);
                }
                break;
            case 65://A
                player.setWalkingLeft(false);
                break;
            case 83://S
                if (player.isJumping()) {
                    player.setVelY(12);
                }
                break;
            case 68://D
                player.setWalkingRight(false);
                break;
            case 69://E
                //HIT, IF MY TIME LETS ME
                player.shoot();
                break;
            case 17://CTRL
                player.setRunning(1);
                break;
            default:
        }

    }
}
