import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Player {
	private static double x;
	private static double y;
	private static double setx;
	private static double sety;
	private static int width;
	private static int height;
	private static int health;
	private static int curr;
	private static int damage;
	private double velX = 0;
	private double velY = 0;
	private double startx = 300;
	private double starty = 165;
	private BufferedImage player;

	int i = 0;

	/*
	 * setx is the initial x position of the player
	 * sety is the initial y position of the player 
	 * x is the x position of the player that will change when player moves
	 * y is the y position of the player that will change when player moves
	 * health is the initial health of the player
	 * damage is the damage output of the player
	 * width is the x position on the sprite sheet
	 * height is the y position on the sprite sheet
	 * game is an instance of the main class
	 */
	public Player(double setx, double sety, double x, double y, int health, int damage, int width, int height,
			Main game) {
		this.x = x;
		this.y = y;
		this.setx = setx;
		this.sety = sety;
		this.width = width;
		this.height = height;
		this.health = health; // max health of the player
		this.curr = health; // current health of the player
		this.damage = damage;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetP());
		player = ss.grabImage(getWidth(), getHeight(), 64, 64);
	}

	/*
	 * Returns the width of selected sprite in sprite sheet
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * Sets width
	 */
	public void setWidth(int newW) {
		this.width = newW;
	}

	/*
	 * Returns the height of selected sprite in sprite sheet
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * Sets height
	 */
	public void setheight(int newH) {
		this.height = newH;
	}

	/*
	 * Gets current x position
	 */
	public static double getX() {
		return x;
	}

	/*
	 * Gets current y position
	 */
	public static double getY() {
		return y;
	}

	/*
	 * Sets the position for the x value
	 */
	public void setX(double newX) {
		this.x = newX;
	}

	/*
	 * Sets the position for the y value
	 */
	public void setY(double newY) {
		this.y = newY;
	}

	/*
	 * Gets the initial x value of the player
	 */
	public double getSetx() {
		return setx;
	}

	/*
	 * Gets the initial y value of the player
	 */
	public double getSety() {
		return sety;
	}

	/*
	 * Sets the initial x value of the player
	 */
	public static void setSetx(double n) {
		setx = n;
	}

	/*
	 * Sets the initial x value of the player
	 */
	public static void setSety(double n) {
		sety = n;
	}

	// gets the max health for a user
	public int getHealth() {
		return health;
	}

	// grabs the current health of the player
	public static void setCurrent(int nc) {
		curr = nc;
	}

	public static void setHealth(int nh) {
		health = nh;
	}

	// grabs the current health of the player
	public int getCurrent() {
		return curr;
	}

	// get damage output of player
	public int getDamage() {
		return damage;
	}

	public void setDamage(int newDmg) {
		this.damage = newDmg;
	}

	// subtract from health
	public void takeDamage(int damage) {
		this.curr = curr - damage;
	}

	// moves player. Boundaries are also set up
	public void tick() {
		if (x != 580 && x != 0)
			x += velX;
		if (x == 580)
			setX((x - 10));
		if (x == 0)
			setX((x + 10));

		if (y != 410 && y != 0)
			y += velY;
		if (y == 410)
			setY((y - 10));
		if (y == 0)
			setY((y + 10));
	}

	// returns array of the players coordinates
	public static double[] getPlayerLocation() {
		double[] loc = { x, y };
		return loc;
	}

	// Sets the velocity of the x movement
	public void setVelX(double vx) {
		this.velX = vx;
	}

	// Sets the velocity of the y movement
	public void setVelY(double vy) {
		this.velY = vy;
	}

	// adds health for an upgrade
	public void addHealth(int newHealth) {
		health = health + newHealth;
		curr = health + curr;
	}

	// player regains health
	public void heal(int newHealth) {
		curr += newHealth;
		if (curr > health)
			curr = health;
	}

	// Checks if player is hit by boss
	int j = 0;

	public boolean playerIsHitByBoss(Boss_Enemy b) {
		if (Math.abs(getX() - b.getBossX()) <= 3.5 && Math.abs(getY() - b.getBossY()) <= 3.5) {
			j++;
		}
		if (j == 2) {
			takeDamage(1);
			j = 0;
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 
	 * Because of the math and how
	 * the projectile converges on the player depending on where
	 * the player is, it requries a lot of code to take in to account all the scenarios
	 * 
	 */
	public boolean playerIsHit(Projectile e, Regular_Enemy re) {
		if (getX() - re.getEnemyX() < 0 && Math.abs(getY() - re.getEnemyY()) != 0 && getY() > re.getEnemyY()
				&& re.getEnemyY() == 0) {
			if ((Math.abs((getX() - e.getX())) <= 3 && Math.abs((getY() - e.getY())) <= 2)
					|| (Math.abs((getX() - e.getX())) <= 2 && Math.abs((getY() - e.getY())) <= 2)) {
				i++;
				if (i == 2 || i == 1) {
					takeDamage(1);
					i = 0;
					return true;
				}
			}
		} else if (getX() - re.getEnemyX() > 0 && Math.abs(getY() - re.getEnemyY()) != 0 && getY() > re.getEnemyY()
				&& re.getEnemyY() == 0) {
			if ((Math.abs((getX() - e.getX())) <= 1 && Math.abs((getY() - e.getY())) <= 2)
					|| (Math.abs((getX() - e.getX())) <= 2 && Math.abs((getY() - e.getY())) <= 1)) {
				i++;
				if (i == 2 || i == 1) {
					takeDamage(1);
					i = 0;
					return true;
				}
			}
		} else if (getX() - re.getEnemyX() < 0 && Math.abs(getY() - re.getEnemyY()) != 0 && getY() < re.getEnemyY()
				&& re.getEnemyY() == 400) {
			if ((Math.abs((getX() - e.getX())) <= 2.5 && Math.abs((getY() - e.getY())) <= 3)
					|| (Math.abs((getX() - e.getX())) <= 5.5 && Math.abs((getY() - e.getY())) <= 5.5)) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (getX() - re.getEnemyX() > 0 && Math.abs(getY() - re.getEnemyY()) != 0 && getY() < re.getEnemyY()
				&& re.getEnemyY() == 400) {
			if ((Math.abs((getX() - e.getX())) <= 2.5 && Math.abs((getY() - e.getY())) <= 3)
					|| (Math.abs((getX() - e.getX())) <= 5.5 && Math.abs((getY() - e.getY())) <= 5.5)) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (Math.abs(getX() - re.getEnemyX()) == 0 && Math.abs(getY() - re.getEnemyY()) != 0
				&& (re.getEnemyY() == 400 || re.getEnemyY() == 0)) {
			if (Math.abs((getX() - e.getX())) <= 2 && Math.abs((getY() - e.getY())) <= 2) {
				i++;
			}
			if (i == 2) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (getY() - re.getEnemyY() > 0 && Math.abs(getX() - re.getEnemyX()) != 0 && re.getEnemyX() == 0) {
			if (Math.abs((getX() - e.getX())) <= 4 && Math.abs((getY() - e.getY())) <= 1
					|| Math.abs((getX() - e.getX())) <= 1 && Math.abs((getY() - e.getY())) <= 4) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (getY() - re.getEnemyY() < 0 && Math.abs(getX() - re.getEnemyX()) != 0 && re.getEnemyX() == 0) {
			if (Math.abs((getX() - e.getX())) <= 4 && Math.abs((getY() - e.getY())) <= 3
					|| Math.abs((getX() - e.getX())) <= 3 && Math.abs((getY() - e.getY())) <= 4) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (getY() - re.getEnemyY() > 0 && Math.abs(getX() - re.getEnemyX()) != 0 && re.getEnemyX() == 575) {
			if (Math.abs((getX() - e.getX())) <= 5 && Math.abs((getY() - e.getY())) <= 5.5
					|| Math.abs((getX() - e.getX())) <= 5 && Math.abs((getY() - e.getY())) <= 2) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (getY() - re.getEnemyY() < 0 && Math.abs(getX() - re.getEnemyX()) != 0 && re.getEnemyX() == 575) {
			if (Math.abs((getX() - e.getX())) <= 5 && Math.abs((getY() - e.getY())) <= 3.5
					|| Math.abs((getX() - e.getX())) <= 4 && Math.abs((getY() - e.getY())) <= 5) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (getY() - re.getEnemyY() > 0 && Math.abs(getX() - re.getEnemyX()) != 0 && re.getEnemyX() == 575) {
			if (Math.abs((getX() - e.getX())) <= 5 && Math.abs((getY() - e.getY())) <= 5.5
					|| Math.abs((getX() - e.getX())) <= 5 && Math.abs((getY() - e.getY())) <= 2) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (Math.abs(getY() - re.getEnemyY()) == 0 && Math.abs(getX() - re.getEnemyX()) != 0
				&& (re.getEnemyX() == 575 || re.getEnemyX() == 0)) {
			if (Math.abs((getX() - e.getX())) <= 2 && Math.abs((getY() - e.getY())) <= 2) {
				i++;
			}
			if (i == 2) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (getX() - re.getEnemyX() > 0 && Math.abs(getX() - re.getEnemyX()) != 0 && getX() < re.getEnemyX()
				&& re.getEnemyX() == 0) {
			if ((Math.abs((getX() - e.getX())) <= 2.5 && Math.abs((getY() - e.getY())) <= 3)
					|| (Math.abs((getX() - e.getX())) <= 5.5 && Math.abs((getY() - e.getY())) <= 5.5)) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (((e.getY() % 2 != 0) && (getY() % 2 != 0) && getY() < e.getY())
				|| ((e.getY() % 2 == 0) && (getY() % 2 == 0) && getY() < e.getY())
				|| ((e.getY() % 2 == 0) && (getY() % 2 == 0) && getY() > e.getY())) {
			if (Math.abs((getX() - e.getX())) <= 2 && Math.abs((getY() - e.getY())) <= 2) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (((e.getY() % 2 == 0) && (getY() % 2 != 0) && getY() < e.getY())) {
			if (Math.abs((getX() - e.getX())) <= 5 && Math.abs((getY() - e.getY())) <= 5) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (((e.getY() % 2 != 0) && (getY() % 2 != 0) && getY() > e.getY())) {
			if (Math.abs((getX() - e.getX())) <= 0 && Math.abs((getY() - e.getY())) <= 0) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (((e.getX() % 2 == 0) && (getX() % 2 == 0) && getX() > e.getX())
				|| ((e.getX() % 2 != 0) && (getX() % 2 != 0) && getX() < e.getX())
				|| ((e.getX() % 2 == 0) && (getX() % 2 == 0) && getX() < e.getX())) {
			if (Math.abs((getX() - e.getX())) <= 4 && Math.abs((getY() - e.getY())) <= 4) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (((e.getX() % 2 == 0) && (getX() % 2 != 0) && getX() < e.getX())) {
			if (Math.abs((getX() - e.getX())) <= 5 && Math.abs((getY() - e.getY())) <= 5) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (((e.getX() % 2 != 0) && (getX() % 2 == 0) && getX() > e.getX())) {
			if (Math.abs((getX() - e.getX())) <= 3 && Math.abs((getY() - e.getY())) <= 3) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		} else if (((e.getX() % 2 != 0) && (getX() % 2 != 0) && getX() > e.getX())) {
			if (Math.abs((getX() - e.getX())) <= 2 && Math.abs((getY() - e.getY())) <= 2) {
				i++;
			}
			if (i == 2 || i == 1) {
				takeDamage(1);
				i = 0;
				return true;
			}
		}
		return false;
	}

	// renders players sprite
	public void render(Graphics g) {
		g.drawImage(player, (int) x, (int) y, null);
	}
}
