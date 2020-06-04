import java.awt.image.BufferedImage;

public class Phoebe extends Cat {

    private BufferedImage[] walkRImages = new BufferedImage[] {
            Entity.getBufferedImage("sprites/pheobe/phebWalk1R.png", 100, 100),
            Entity.getBufferedImage("sprites/pheobe/phebWalk2R.png", 100, 100),
            Entity.getBufferedImage("sprites/pheobe/phebWalk3R.png", 100, 100),
            Entity.getBufferedImage("sprites/pheobe/phebWalk2R.png", 100, 100)
    };
    private BufferedImage[] walkLImages = new BufferedImage[] {
            Entity.getBufferedImage("sprites/pheobe/phebWalk1L.png", 100, 100),
            Entity.getBufferedImage("sprites/pheobe/phebWalk2L.png", 100, 100),
            Entity.getBufferedImage("sprites/pheobe/phebWalk3L.png", 100, 100),
            Entity.getBufferedImage("sprites/pheobe/phebWalk2L.png", 100, 100)
    };
    private BufferedImage pheobeStillR = Entity.getBufferedImage("sprites/pheobe/phebStillR.png", 100, 100);
    private BufferedImage pheobeStillL = Entity.getBufferedImage("sprites/pheobe/phebStillL.png", 100, 100);
    private BufferedImage pheobeJumpR = Entity.getBufferedImage("sprites/pheobe/phebJumpR.png", 100, 100);
    private BufferedImage pheobeJumpL = Entity.getBufferedImage("sprites/pheobe/phebJumpL.png", 100, 100);

    private BufferedImage[] walkImages;

    private int walkIndex = 0;
    private int dieTicks = 0;

    public Phoebe() {
        x = 150;
        y = 350;
        width = 75;
        height = 50;

        popImages = new BufferedImage[] {
                getBufferedImage("sprites/pheobe/explode/phebExp1R.png", 100, 100),
                getBufferedImage("sprites/pheobe/explode/phebExp2R.png", 100, 100),
                getBufferedImage("sprites/pheobe/explode/phebExp3R.png", 100, 100),
                getBufferedImage("sprites/pheobe/explode/phebExp4R.png", 100, 100)
        };
        popBackImages = new BufferedImage[] {
                getBufferedImage("sprites/pheobe/explode/phebExp1L.png", 100, 100),
                getBufferedImage("sprites/pheobe/explode/phebExp2L.png", 100, 100),
                getBufferedImage("sprites/pheobe/explode/phebExp3L.png", 100, 100),
                getBufferedImage("sprites/pheobe/explode/phebExp4L.png", 100, 100)
        };

        catImage = pheobeStillR;
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

    public catProjectile generateProjectile() {
        double vel = (state | 101) == 111 ? 9.5 : 7.5;
        if ((state | 110) == 111) {
            return new FluffBone(x, y + 10, vel, -1);
        } else {
            return new FluffBone(x + 70, y + 10, vel, 1);
        }
    }

    @Override
    protected void setSprite() {
        if ((state | 001) == 011) {
            if (state == 010) {
                //walking
                walkImages = walkRImages;
            } else if (state == 011) {
                walkImages = walkLImages;
            }
            if (frameCount < 11) {
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
            catImage = pheobeStillR;
        } else if (state == 001) {
            catImage = pheobeStillL;
        }
        else if ((state | 110) == 111) {
            catImage = pheobeJumpL;
        } else {
            catImage = pheobeJumpR;
        }
    }
}
