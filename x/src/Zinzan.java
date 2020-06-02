import java.awt.image.BufferedImage;

public class Zinzan extends Cat {

    private BufferedImage still = getBufferedImage("sprites/zinzan/zinzanStill.png", 100, 50);
    private BufferedImage walk1 = getBufferedImage("sprites/zinzan/zinzanWalk1.png", 100, 50);
    private BufferedImage walk2 = getBufferedImage("sprites/zinzan/zinzanWalk2.png", 100, 50);
    private BufferedImage stillBack = getBufferedImage("sprites/zinzan/zinzanStillBack.png", 100, 50);
    private BufferedImage walk1Back = getBufferedImage("sprites/zinzan/zinzanWalk1Back.png", 100, 50);
    private BufferedImage walk2Back = getBufferedImage("sprites/zinzan/zinzanWalk2Back.png", 100, 50);

    private int dieTicks = 0;

    public Zinzan() {
        popImages = new BufferedImage[] {
                getBufferedImage("sprites/zinzan/pop/zinzanPop1.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop2.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop3.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop4.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop5.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop6.png", 100, 50)
        };
        popBackImages = new BufferedImage[] {
                getBufferedImage("sprites/zinzan/pop/zinzanPop1Back.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop2Back.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop3Back.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop4Back.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop5Back.png", 100, 50),
                getBufferedImage("sprites/zinzan/pop/zinzanPop6Back.png", 100, 50)
        };

        x = 150;
        y = 350;
        width = 75;
        height = 50;

        catImage = still;
    }

    public catProjectile generateProjectile() {
        double vel = (state | 101) == 111 ? 9.5 : 7.5;
        if ((state | 110) == 111) {
            return new FishSkelly(x, y + 20, vel, -1);
        } else {
            return new FishSkelly(x + 70, y + 20, vel, 1);
        }
    }

    @Override
    protected void die() {
        if (dieTicks >= 6) {
            if (popIndex < 5) {
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
        if (state == 010) {
            //walking
            if (frameCount < 10) {
                catImage = walk1;
            } else {
                catImage = walk2;
                if (frameCount >= 20) {
                    frameCount = 0;
                }
            }
        } else if (state == 011) {

            if (frameCount < 10) {
                catImage = walk1Back;
            } else {
                catImage = walk2Back;
                if (frameCount >= 20) {
                    frameCount = 0;
                }
            }
        } else if ((state | 110) == 111) {
            catImage = stillBack;
        } else {
            catImage = still;
        }
    }
}
