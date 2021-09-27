//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.geom.Line2D;
//import java.awt.geom.Rectangle2D;
//import java.awt.geom.RoundRectangle2D;
//import java.awt.image.BufferedImage;
//import java.util.Random;
//
//public class DaisyEnemy extends SolidEnemy {
//
//    private BufferedImage[] daisyWalk = new BufferedImage[] {
//            Entity.getBufferedImage("sprites/daisyEnemy/d1L.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/d2L.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/d3L.png", 80, 80)
//    };
//    private BufferedImage[] daisyWalkR = new BufferedImage[] {
//            Entity.getBufferedImage("sprites/daisyEnemy/d1R.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/d2R.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/d3R.png", 80, 80)
//    };
//
//    private BufferedImage[] daisyFlash = new BufferedImage[] {
//            Entity.getBufferedImage("sprites/daisyEnemy/flashes/d1LFlash.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/flashes/d2LFlash.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/flashes/d3LFlash.png", 80, 80)
//    };
//    private BufferedImage[] daisyFlashR = new BufferedImage[] {
//            Entity.getBufferedImage("sprites/daisyEnemy/flashes/d1RFlash.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/flashes/d2RFlash.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/flashes/d3RFlash.png", 80, 80)
//    };
//
//    private BufferedImage[] dieRImages = new BufferedImage[] {
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode1R.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode2R.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode3R.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode4R.png", 80, 80)
//    };
//    private BufferedImage[] dieLImages = new BufferedImage[] {
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode1L.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode2L.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode3L.png", 80, 80),
//            Entity.getBufferedImage("sprites/daisyEnemy/explode/explode4L.png", 80, 80)
//    };
//
//    private BufferedImage daisyFly = Entity.getBufferedImage("sprites/daisyEnemy/daisyFly.png", 80, 80);
//    private BufferedImage daisyFlyR = Entity.getBufferedImage("sprites/daisyEnemy/daisyFlyR.png", 80, 80);
//
//    private BufferedImage image;
//    private BufferedImage[] Images;
//    private int imageIndex = 0;
//
//    RoundRectangle2D healthBarOutline = new RoundRectangle2D.Double(450, 10, 350, 20, 10, 10);
//    Rectangle2D healthBar = new Rectangle2D.Double(452, 12, 346, 16);
//
//    private LevelInfo levelInfo;
//    private boolean zinzanInRange = false;
//    private boolean firstAttack = true;
//    //private boolean jumpNow;
//
//    private Line2D leftFloor;
//    private Line2D rightFloor;
//
//    private int lapCount = 0;
//    private int walkTicks = 0;
//    private int indexDirection = 1;
//    private int xVel;
//    private double yVel = 0;
//    private double grav = 0.2;
//
//    //private Timer walkTimer;
//    //private Timer jumpLTimer;
//    //private Timer flashTimer;
//    //private Timer dieTimer;
//
//
//    //constructor should take lines not rectangles
//    public DaisyEnemy(Rectangle2D leftFloor, Rectangle2D rightFloor) {
//        this.leftFloor = new Line2D.Double(leftFloor.getX(), leftFloor.getY(),
//                leftFloor.getX() + leftFloor.getWidth(), leftFloor.getY());
//        this.rightFloor = new Line2D.Double(rightFloor.getX(), rightFloor.getY(),
//            rightFloor.getX() + rightFloor.getWidth(), rightFloor.getY());
//
//        width = 60;
//        height = 50;
//        health = 300;
//
//        x = (int)Math.round(rightFloor.getX());
//        y = (int)Math.round(rightFloor.getY()) - height;
//
//        walkTimer = new Timer(5, walkRightFloor);
//        Images = daisyWalkR;
//        image = daisyWalkR[0];
//        xVel = 2;
//        //jumpNow = true;
//        walkTimer.start();
//    }
//
//    @Override
//    public void Update() {
//
//    }
//
//    public void Dispose() {
////        if (walkTimer != null) {
////            walkTimer.stop();
////        }
////        if (jumpLTimer != null) {
////            jumpLTimer.stop();
////        }
////        if (flashTimer != null) {
////            flashTimer.stop();
////        }
////        if (dieTimer != null) {
////            dieTimer.stop();
////        }
//        //don't need to dispose of images
//    }
//
////    public DaisyEnemy(Line2D attackFloor, Line2D restFloor) {
////        this.attackFloor = attackFloor;
////        this.restFloor = restFloor;
////    }
//
//    @Override
//    public void entityHit(int damage) {
//        if (Dying || Dead) {
//            return;
//        }
//        health -= damage;
//        double newWidth = (health / 400.0) * 346;
//        healthBar.setRect(healthBar.getX(), healthBar.getY(), newWidth, healthBar.getHeight());
//        if (health <= 0) {
//            startDying();
//        } else {
//            if (xVel > 0) {
//                Images = daisyFlashR;
//            } else {
//                Images = daisyFlash;
//            }
//            if (flashTimer == null || !flashTimer.isRunning()) {
//                flashTimer = new Timer(0, flash);
//                flashTimer.setInitialDelay(75);
//                flashTimer.start();
//            }
//        }
//    }
//
//    private ActionListener walkLeftFloor = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (walkTicks >= 8) {
//                imageIndex += indexDirection;
//                walkTicks = 0;
//            } else {
//                walkTicks++;
//            }
//            if (imageIndex == 2) {
//                indexDirection = -1;
//            } else if (imageIndex == 0) {
//                indexDirection = 1;
//            }
//            image = Images[imageIndex];
////            if (x >= leftFloor.getX2() - width) {
////                walkTimer.stop();
////                startRightJump();
////            }
//            if (xVel < 0) {
//                if (x <= leftFloor.getX1()) {
//                    Images = daisyWalkR;
//                    xVel = 2;
//                    walkTicks = 0;
//                }
//            } else if (xVel > 0) {
//                if (x >= leftFloor.getX2() - width) {
//                    if (catIsInRange()) {//(rand.nextBoolean()) {
//                        walkTimer.stop();
//                        startRightJump();
//                    } else {
//                        Images = daisyWalk;
//                        xVel = -2;
//                        walkTicks = 0;
//                    }
//                }
//            }
//            x += xVel;
//        }
//    };
//
//    private ActionListener walkRightFloor = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (walkTicks >= 8) {
//                imageIndex += indexDirection;
//                walkTicks = 0;
//            } else {
//                walkTicks++;
//            }
//            if (imageIndex == 2) {
//                indexDirection = -1;
//            } else if (imageIndex == 0) {
//                indexDirection = 1;
//            }
//            image = Images[imageIndex];
//            if (xVel > 0) {
//                if (x >= rightFloor.getX2() - width) {
//                    Images = daisyWalk;
//                    xVel = -2;
//                    walkTicks = 0;
//                    lapCount++;
//                }
//            } else if (xVel < 0) {
//                if (x <= rightFloor.getX1()) {
//                    Random rand = new Random();
//                    boolean jump = (rand.nextBoolean() || lapCount >= 6);
//                    if (jump) {
//                        lapCount = 0;
//                    }
//                    if (catIsInRange() && (jump || firstAttack)) {
//                    //if (jumpNow && catIsInRange()) {
//                        if (firstAttack) { firstAttack = false; }
//                        walkTimer.stop();
//                        startAttackJump();
//                    } else {
//                        Images = daisyWalkR;
//                        xVel = 2;
//                        walkTicks = 0;
//                        //jumpNow = rand.nextBoolean();
//                    }
//                }
//            }
//            x += xVel;
//        }
//    };
//
//    private boolean catIsInRange() {
//        double catx = levelInfo.getCatX();
//        double caty = levelInfo.getCatY();
//        return catx >= leftFloor.getX1() && catx <= leftFloor.getX2()
//                && caty > leftFloor.getY1() - 150 && caty + 45 < leftFloor.getY1();
//    }
//
//    private ActionListener jumpToRightFloor = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (y + height >= rightFloor.getY1() && x > rightFloor.getX1()) {
//                jumpLTimer.stop();
//                y = (int)Math.round(rightFloor.getY1() - height);
//                walkTimer = new Timer(5, walkRightFloor);
//                Images = daisyWalkR;
//                image = daisyWalkR[0];
//                xVel = 2;
//                walkTimer.start();
//            } else {
//                x += xVel;
//                y += yVel;
//                yVel += grav;
//            }
//        }
//    };
//    private ActionListener jumpToLeftFloor = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (y + height >= leftFloor.getY1() && x < leftFloor.getX2()) {
//                jumpLTimer.stop();
//                image = daisyWalk[1];
//                y = (int)Math.round(leftFloor.getY1() - height);
//                walkTimer = new Timer(5, walkLeftFloor);
//                xVel = -2;
//                Images = daisyWalk;
//                walkTimer.start();
//            } else {
//                x += xVel;
//                y += yVel;
//                yVel += grav;
//            }
//        }
//    };
//
//    private double getInitialVy(double target_x, double target_y) {
//        double deltaT = Math.abs((target_x - x) / xVel);
//        double deltaY = target_y - y;
//        return  (deltaY - (0.5 * grav * deltaT * deltaT)) / deltaT;
//    }
//
//    private void startRightJump() {
//        double target_x = rightFloor.getX1() + 5;
//        double target_y = rightFloor.getY1() - height - 5;
//        if (target_x < x) {
//            image = daisyFly;
//        } else {
//            image = daisyFlyR;
//        }
//        //walkTimer.stop();
//        jumpLTimer = new Timer(5, jumpToRightFloor);
//        xVel = 5;
//        yVel = getInitialVy(target_x, target_y);
//        jumpLTimer.start();
//    }
//
//    private void startAttackJump() {
//        double catX = levelInfo.getCatX();
//        double catY = levelInfo.getCatY();
//        if (catX < x) {
//            image = daisyFly;
//        } else {
//            image = daisyFlyR;
//        }
//        //walkTimer.stop();
//        jumpLTimer = new Timer(5, jumpToLeftFloor);
//        xVel = -5;
//        yVel = getInitialVy(catX + 25, catY);
//        jumpLTimer.start();
//    }
//
//    public void addLevelInfo(LevelInfo levelInfo) {
//        this.levelInfo = levelInfo;
//    }
//
//    @Override
//    public int getContactDamage() {
//        return 50;
//    }
//
//    @Override
//    public void hitCat() {
//        hitCoolingDown = true;
//        Timer cooldownTimer = new Timer(100, (e) -> {
//            hitCoolingDown = false;
//            ((Timer)e.getSource()).stop();
//        });
//        cooldownTimer.setInitialDelay(1000);
//        cooldownTimer.start();
//    }
//
//    @Override
//    public Rectangle2D getHitBox() {
//        return new Rectangle2D.Double(x + 6, y + 16, width - 18, height - 20);///change
//    }
//
//    private ActionListener flash = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            flashTimer.stop();
//            if (xVel > 0) {
//                Images = daisyWalkR;
//            } else {
//                Images = daisyWalk;
//            }
//        }
//    };
//
//    @Override
//    public void startDying() {
//        Dying = true;
//        if (walkTimer != null) {
//            walkTimer.stop();
//        }
//        if (jumpLTimer != null) {
//            jumpLTimer.stop();
//        }
//        if (flashTimer != null) {
//            flashTimer.stop();
//        }
//        dieTimer = new Timer(50, die);
//        if (xVel >= 0) {
//            Images = dieRImages;
//        } else {
//            Images = dieLImages;
//        }
//        imageIndex = 0;
//        image = Images[0];
//        dieTimer.start();
//    }
//
//    private ActionListener die = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            imageIndex++;
//            if (imageIndex >= 4) {
//                dieTimer.stop();
//                Dead = true;
//            } else {
//                image = Images[imageIndex];
//            }
//        }
//    };
//
//    @Override
//    public void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D)g;
//        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
//
//        //draw daisy health bar
//        g2.setColor(Color.BLUE);
//        g2.fill(healthBar);
//        g2.setStroke(new BasicStroke(4));
//        g2.setColor(Color.WHITE);
//        g2.draw(healthBarOutline);
//
//    }
//}
