import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Regular_Enemy {

	// the x and y coordinates
	private double setx;
	private double sety;
	// max health and the current health which changes
	private int health;
	private int curr;
	private int width;
	private int height;
	private boolean alive;
	private BufferedImage regularEnemy;
	int i = 0;

	/*
	 * setx = x postition of the enemy
	 * sety = y postition of the enemy
	 * health = max health of the enemy
	 * game = the class that runs the game
	 */
	public Regular_Enemy(double setx, double sety, int width, int height, int health, boolean alive, Main game) {
		this.setx = setx;
		this.sety = sety;
		this.health = health;
		this.curr = health;
		this.width = width;
		this.height = height;
		this.alive = alive;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetRE());
		regularEnemy = ss.grabImage(width, height, 64, 64);
	}

	// checks is enemy is alive or dead
	public boolean getAlive() {
		return alive;
	}

	// sets wether the enemy is alive or dead
	public void setAlive(boolean a) {
		alive = a;
	}

	/*
	 * returns the enemy x position
	 */
	public double getEnemyX() {
		return setx;
	}

	/*
	 * returns the enemy y position
	 */
	public double getEnemyY() {
		return sety;
	}

	/*
	 * returns the max health of the enemy
	 */
	public int getEnemyMaxHealth() {
		return health;
	}

	/*
	 * returns the current health of the enemy
	 */
	public int getEnemyCurrentHealth() {
		return curr;
	}

	/*
	 * takes away from the health when hit by the player
	 * sets the current health to the new health
	 */
	public void takeDamage(int enemyDamage) {
		this.curr = curr - enemyDamage;
	}

	public void setCurrHealth(int newH) {
		curr = newH;
	}

	/*
	 * Checks if player hits enemy
	 */
	public boolean enemyIsHit(Player p) {
		if ((Math.abs((PlayerWeapon.getX() - getEnemyX())) <= 20
				&& Math.abs((PlayerWeapon.getY() - getEnemyY())) <= 20)) {
			i++;
		}
		if (i == 2) {
			takeDamage(p.getDamage());
			i = 0;
			return true;
		} else
			return false;
	}

	public void enemyRegainFullHealth() {
		setCurrHealth(getEnemyMaxHealth());
		setAlive(true);
	}

	/*
	 * returns an array of the enemy coordiantes
	 */
	public double[] getEnemyLocation() {
		double[] tmp = { getEnemyX(), getEnemyY() };
		return tmp;
	}

	/*
	 * sets the location of the enemy
	 */
	public void setEnemyLocation(double newX, double newY) {
		this.setx = newX;
		this.sety = newY;
	}

	/*
	 * renders the graphics
	 */
	public void render(Graphics g) {
		g.drawImage(regularEnemy, (int) setx, (int) sety, null);
	}
}
