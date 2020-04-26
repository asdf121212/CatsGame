import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HitCheckListener {//implements ActionListener {

//    public
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        ////make the squirrel shoot balls
//        if (displayList.getEnemies().size() == 1 && !displayList.getEnemies().get(0).Dying) {
//            Squirrel squirrel = (Squirrel) displayList.getEnemies().get(0);/////////not good structure- should refactor
//            if (tickCount == 220) {
//                SwingUtilities.invokeLater(() -> displayList.AddDanger(squirrel.generateBall(-5)));
//                tickCount = 0;
//            } else {
//                tickCount++;
//            }
//        }
//
//        ////check if fluffballs hit enemies, remove dead enemies--- kill cat if cat touches enemy
//        for (Enemy enemy : displayList.getEnemies()) {
//            if (enemy.Dead) {
//                SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
//                continue;
//            } else if (enemy.Dying) {
//                continue;
//            }
//            for (Fluffball fluffball : displayList.getFluffballs()) {
//                if (fluffball.getHitBox().intersects(enemy.getHitBox())) {
//                    fluffball.stop();
//                    SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
//                    SwingUtilities.invokeLater(enemy::entityHit);
//                }
//            }
//            if (enemy.getHitBox().intersects(displayList.cat.getHitBox())) {
//                if (displayList.cat != null && !(displayList.cat.Dying || displayList.cat.Dead)) {
//                    SwingUtilities.invokeLater(() -> displayList.cat.catHit(200));///threw error
//                }
//            }
//        }
//
//        ////check if projectiles hit cat, remove dead dangers
//        for (Danger danger : displayList.getDangers()) {
//            if (danger.Dead) {
//                SwingUtilities.invokeLater(() -> displayList.removeDanger(danger));
//            } else if (danger.Dying) {
//                continue;
//            } else {
//                if (danger.getHitBox().intersects(displayList.cat.getHitBox())) {
//                    SwingUtilities.invokeLater(() -> displayList.cat.catHit(danger.getDamage()));//add
//                    danger.hitTarget();
//                }
//            }
//        }
//        if (displayList.cat.Dying) {
//            if (rightTimer != null && rightTimer.isRunning()) {
//                rightTimer.stop();
//            }
//            if (leftTimer != null && leftTimer.isRunning()) {
//                leftTimer.stop();
//            }
//            if (jumpTimer != null && jumpTimer.isRunning()) {
//                jumpTimer.stop();
//            }
//        }
//        if (displayList.cat.Dead) {
//            cat_die();
//        }
//    }
}
