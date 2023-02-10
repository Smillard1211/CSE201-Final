import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PlayerWeapon {
	private static double x;
	private static double y;
	private int dmg;
	private int speed;
	
	private BufferedImage playerweapon;
	
	/*
	 * x is the value of the x position
	 * y is the value of the y position
	 * dmg is the amount of damage the weapon does 
	 * speed is the speed the weapon travels
	 * game is an instance of the Main class
	 */
	PlayerWeapon(double x, double y, int dmg, int speed, Main game) {
		this.x = x;
		this.y = y;
		this.dmg = dmg;
		this.speed = speed;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetPW());
		playerweapon = ss.grabImageSmall(1, 1, 32, 32);
	}
	
	/*
	 * Gets weapons current x value
	 */
	public static double getX() {
		return x;
	}
	/*
	 * Sets weapons current x value
	 */
	public void setX(double newX) {
		this.x = newX;
	}
	
	/*
	 * Gets weapons current y value
	 */
	public static double getY() {
		return y;
	}
	/*
	 * Sets weapons current y value
	 */
	public void setY(double newY) {
		this.y = newY;
	}
	
	/*
	 * Gets weapons current damage value
	 */
	public int getDmg() {
		return dmg;
	}
	
	/*
	 * Sets weapons current damage value
	 */
	public void setDmg(int newDmg) {
		this.dmg = newDmg;
	}
	
	/*
	 * Gets weapons current speed value
	 */
	public int getSpeed() {
		return speed;
	}
	/*
	 * Sets weapons current speed value
	 */
	public void setSpeed(int newSpeed) {
		this.speed = newSpeed;
	}
	
	/*
	 * METHODS TO MOVE THE WEAPON RIGHT, LEFT, UP, AND DOWN.
	 */
	public void moveRight() {
		x = x+speed;
	}
	public void moveLeft() {
		x = x-speed;
	}
	public void moveUp() {
		y = y-speed;
	}
	public void moveDown() {
		y = y+speed;
	}
	
	/*
	 * Checks if the weapon is moving or not
	 */
	public boolean isMoving() {
		if((getX() != Player.getX()) || getY() != Player.getY()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Checks if weapon is out of bounds
	 */
	public boolean outOfBounds() {
		if(getX() > 600 || getX() < -20 || getY() < -20 || getY() > 430) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Checks if weapon has hit enemy
	 * used to return weapon to player when it hits enemy
	 */
	public boolean hitEnemy(Regular_Enemy re) {
		if(Math.abs(getX()-re.getEnemyX()) <= 20 || Math.abs(getY()-re.getEnemyY()) <= 50) {
			return true;
		}
		else {
		return false;
		}
	}
	
	// renders weapon sprite
	public void render(Graphics g) {
		g.drawImage(playerweapon,  (int)x,  (int)y, null);
	}

}
