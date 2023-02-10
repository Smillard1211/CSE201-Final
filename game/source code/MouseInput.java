import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		// When the mouse clicks defined area of begin button we go to story frame
		if (mouseX >= Main.WIDTH / 2 + 100 && mouseX <= Main.WIDTH / 2 + 200) {
			if (mouseY >= 150 && mouseY <= 200) {
				Player.setHealth(10);
				Player.setCurrent(10);
				Main.p.setDamage(1);
				Main.re1.setCurrHealth(Main.re1.getEnemyMaxHealth());
				Main.re2.setCurrHealth(Main.re2.getEnemyMaxHealth());
				Main.re3.setCurrHealth(Main.re3.getEnemyMaxHealth());
				Main.re4.setCurrHealth(Main.re4.getEnemyMaxHealth());
				Main.re5.setCurrHealth(Main.re5.getEnemyMaxHealth());
				Main.re6.setCurrHealth(Main.re6.getEnemyMaxHealth());
				Main.re7.setCurrHealth(Main.re7.getEnemyMaxHealth());
				Main.re8.setCurrHealth(Main.re8.getEnemyMaxHealth());
				Main.be.setCurrHealth(Main.be.getBossMaxHealth());
				Main.Level1 = true;
				Main.Level2 = false;
				Main.Level3 = false;
				Main.Level4 = false;
				Main.Finallevel = false;
				Main.state = Main.STATE.STORY;
			}
		}
		// when quit button is pressed program shuts down
		if (mouseX >= Main.WIDTH / 2 + 100 && mouseX <= Main.WIDTH / 2 + 200) {
			if (mouseY >= 220 && mouseY <= 270) {
				System.exit(1);
			}
		}
		// when guide button is pressed we go to the guide
		if (mouseX >= Main.WIDTH / 2 + 100 && mouseX <= Main.WIDTH / 2 + 200) {
			if (mouseY >= 290 && mouseY <= 340) {
				Main.state = Main.STATE.GUIDE;
			}
		}

		// When we click back button on guide we go back to menu
		if (mouseX >= Main.WIDTH / 2 + 110 && mouseX <= Main.WIDTH / 2 + 210) {
			if (mouseY >= 10 && mouseY <= 50) {
				Main.state = Main.STATE.MENU;
			}
		}

		// When the mouse clicks defined area of button save town we go to level frame
		if (mouseX >= Main.WIDTH / 2 + 45 && mouseX <= Main.WIDTH / 2 + 295) {
			if (mouseY >= 390 && mouseY <= 440) {
				Main.p.setX(300);
				Main.p.setY(165);
				Main.state = Main.STATE.LEVELSPLICE;
			}
		}

		// When the proceed button is clicked on the level splice we go to game
		if (mouseX >= Main.WIDTH / 2 + 300 && mouseX <= Main.WIDTH / 2 + 450) {
			if (mouseY >= 150 && mouseY <= 200) {
				Main.p.setX(300);
				Main.p.setY(165);
				Main.p.setSetx(300);
				Main.p.setSety(165);
				Main.re1.enemyRegainFullHealth();
				Main.re2.enemyRegainFullHealth();
				Main.re3.enemyRegainFullHealth();
				Main.re4.enemyRegainFullHealth();
				Main.re5.enemyRegainFullHealth();
				Main.re6.enemyRegainFullHealth();
				Main.re7.enemyRegainFullHealth();
				Main.re8.enemyRegainFullHealth();
				Main.projectile1.returnToEnemy(Main.re1);
				Main.projectile2.returnToEnemy(Main.re2);
				Main.projectile3.returnToEnemy(Main.re3);
				Main.projectile4.returnToEnemy(Main.re4);
				Main.projectile5.returnToEnemy(Main.re5);
				Main.projectile6.returnToEnemy(Main.re6);
				Main.projectile7.returnToEnemy(Main.re7);
				Main.projectile8.returnToEnemy(Main.re8);
				Main.be.setCurrHealth(Main.be.getBossMaxHealth());
				Main.be.setAlive(true);
				Main.be.setBossX(320);
				Main.be.setBossY(0);
				Main.state = Main.STATE.GAME;
			}
		}

		// On the end story page when we click next we go to credits
		if (mouseX >= Main.WIDTH / 2 + 325 && mouseX <= Main.WIDTH / 2 + 425) {
			if (Main.state != Main.STATE.STORY) {
				if (mouseY >= 350 && mouseY <= 400) {
					Main.state = Main.STATE.CREDITS;
				}
			}
		}

		// when we hit the skip button in credits we go back to menu
		// * to-do everything should reset*
		if (mouseX >= Main.WIDTH / 2 + 375 && mouseX <= Main.WIDTH / 2 + 465) {
			if (Main.state != Main.STATE.STORY) {
				if (mouseY >= 390 && mouseY <= 440) {
					Player.setHealth(10);
					Player.setCurrent(10);

					Main.re1.enemyRegainFullHealth();
					Main.re2.enemyRegainFullHealth();
					Main.re3.enemyRegainFullHealth();
					Main.re4.enemyRegainFullHealth();
					Main.re5.enemyRegainFullHealth();
					Main.re6.enemyRegainFullHealth();
					Main.re7.enemyRegainFullHealth();
					Main.re8.enemyRegainFullHealth();
					Main.be.setCurrHealth(Main.be.getBossMaxHealth());
					Main.be.setAlive(true);
					Main.be.setBossX(320);
					Main.be.setBossY(0);
					Main.projectile1.returnToEnemy(Main.re1);
					Main.projectile2.returnToEnemy(Main.re2);
					Main.projectile3.returnToEnemy(Main.re3);
					Main.projectile4.returnToEnemy(Main.re4);
					Main.projectile5.returnToEnemy(Main.re5);
					Main.projectile6.returnToEnemy(Main.re6);
					Main.projectile7.returnToEnemy(Main.re7);
					Main.projectile8.returnToEnemy(Main.re8);
					Main.Level1 = true;
					Main.Level2 = false;
					Main.Level3 = false;
					Main.Level4 = false;
					Main.Finallevel = false;
					Main.p.setX(300);
					Main.p.setY(165);
					Main.state = Main.STATE.MENU;
				}
			}
		}

	}

	/*
	 * Methods used for mouse input if pressed, released, entered, or edited.
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
