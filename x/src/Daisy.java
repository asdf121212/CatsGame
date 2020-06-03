import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Daisy extends Cat {

    private BufferedImage daisyFlyL = Entity.getBufferedImage("sprites/daisyEnemy/daisyFly.png", 80, 80);
    private BufferedImage daisyFlyR = Entity.getBufferedImage("sprites/daisyEnemy/daisyFlyR.png", 80, 80);

    private BufferedImage[] daisyWalkL = new BufferedImage[] {
            Entity.getBufferedImage("sprites/daisyEnemy/d1L.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d2L.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d3L.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d2L.png", 80, 80)
    };
    private BufferedImage[] daisyWalkR = new BufferedImage[] {
            Entity.getBufferedImage("sprites/daisyEnemy/d1R.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d2R.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d3R.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d2R.png", 80, 80)
    };
    private BufferedImage[] walkImages;

    private int walkIndex = 0;
    private int dieTicks = 0;

    public Daisy() {
        x = 150;
        y = 350;
        width = 60;
        height = 50;

        popImages = new BufferedImage[] {
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode1R.png", 80, 80),
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode2R.png", 80, 80),
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode3R.png", 80, 80),
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode4R.png", 80, 80)
        };
        popBackImages = new BufferedImage[] {
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode1L.png", 80, 80),
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode2L.png", 80, 80),
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode3L.png", 80, 80),
                Entity.getBufferedImage("sprites/daisyEnemy/explode/explode4L.png", 80, 80)
        };

        catImage = daisyWalkR[1];
    }

    @Override
    public catProjectile generateProjectile() {
        double vel = (state | 101) == 111 ? 9.5 : 7.5;
        if ((state | 110) == 111) {
            return new FishSkelly(x, y + 20, vel, -1);
        } else {
            return new FishSkelly(x + 50, y + 20, vel, 1);
        }
    }

    @Override
    public Rectangle2D getHitBox() {
        return super.getHitBox();
    }

    @Override
    protected void die() {
        if (dieTicks >= 6) {
            if (popIndex < 3) {
                x -= 11;
                y -= 18;
                width += 22;
                height += 22;
                popIndex++;
                catImage = popAnimationImages[popIndex];
            } else {
                //dieTimer.stop();
                Dying = false;
                Dead = true;
            }
            dieTicks = 0;
        } else {
            dieTicks++;
        }
    }

    @Override
    protected void setSprite() {
        if ((state | 001) == 011) {
            if (state == 010) {
                //walking
                walkImages = daisyWalkR;
            } else if (state == 011) {
                walkImages = daisyWalkL;
            }
            if (frameCount < 10) {
                catImage = walkImages[walkIndex];
            } else {
                frameCount = 0;
                walkIndex++;
                if (walkIndex >= walkImages.length) {
                    walkIndex = 0;
                }
                catImage = walkImages[walkIndex];
            }
        } else if (state == 000) {
            catImage = daisyWalkR[1];
        } else if (state == 001) {
            catImage = daisyWalkL[1];
        }
        else if ((state | 110) == 111) {
            catImage = daisyFlyL;
        } else {
            catImage = daisyFlyR;
        }
    }

}
