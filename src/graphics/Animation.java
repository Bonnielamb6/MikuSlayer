package graphics;

import java.awt.image.BufferedImage;

public class Animation {
    private int index = 0;
    private int count = 0;
    private int velocity = 0;
    private int frames = 0;
    private BufferedImage[] objImages;
    private BufferedImage actualImg;

    public Animation(int velocity, BufferedImage... args) {
        this.velocity = velocity;
        objImages = new BufferedImage[args.length];
        for (int i = 0; i < args.length; i++) {
            objImages[i] = args[i];
        }
        frames = args.length;
    }

    public void animate() {
        index++;
        if (index > velocity) {
            index = 0;
            nextFrame();
        }
    }

    public void nextFrame() {
        actualImg = objImages[count];
        count++;
        if (count >= frames) {
            count = 0;
        }
    }

    public void drawSprite(GraphicsLibrary g, int x, int y) {
        g.drawImage(actualImg, x, y);
    }

    public void drawReversedSprite(GraphicsLibrary g, int x, int y) {
        g.drawImage(actualImg, x + actualImg.getWidth(), y, -actualImg.getWidth(), actualImg.getHeight());
    }
}
