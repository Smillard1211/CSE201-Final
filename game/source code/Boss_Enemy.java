import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Boss_Enemy {
	private double x;
	private double y;
	private double setx;
	private double sety;
	private int health;
	private int curr;
	private boolean alive;

	double playerx = Player.getX() + add1IfOdd(Player.getX(), setx);
	double playery = Player.getY() + add1IfOdd(Player.getY(), sety);

	private BufferedImage enemyboss;

	/*
	 * setx is the x position of the enemy enemy sprite sety is the y position of
	 * the enemy boss sprite x is the changing x value of Enemy Boss y is the
	 * changing y value of Enemy Boss health is the health of the boss alive checks
	 * if the health is at 0 or not Main is an instance of the main class
	 */
	Boss_Enemy(double setx, double sety, double x, double y, int health, boolean alive, 
			   Main game) {
		this.setx = setx;
		this.sety = sety;
		this.x = x;
		this.y = y;
		this.health = health;
		this.curr = health;
		this.alive = alive;

		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetEB());
		enemyboss = ss.grabImage(1, 1, 64, 64);
	}

	/*
	 * Returns the x position of the boss
	 */
	public double getBossX() {
		return x;
	}

	/*
	 * Returns the y position of the boss
	 */
	public double getBossY() {
		return y;
	}

	/*
	 * Sets the x position of the boss
	 */
	public void setBossX(double newx) {
		this.x = newx;
	}

	/*
	 * Sets the y position of the boss
	 */
	public void setBossY(double newy) {
		this.y = newy;
	}

	/*
	 * Returns the Set x of the Enemy Boss
	 */
	public double getSetx() {
		return setx;
	}

	/*
	 * Returns the Set y of the Enemy Boss
	 */
	public double getSety() {
		return sety;
	}

	/*
	 * Changes the set x of enemy boss
	 */
	public void setSetx(double newSetx) {
		this.setx = newSetx;
	}

	/*
	 * Changes the set y of the enemy boss
	 */
	public void setSety(double newSety) {
		this.sety = newSety;
	}

	/*
	 * returns the maximum health of the boss
	 */
	public int getBossMaxHealth() {
		return health;
	}

	/*
	 * returns the current health of the boss
	 */
	public int getBossCurrentHealth() {
		return curr;
	}

	/*
	 * sets current health of boss when damaged
	 */
	public void takeDamage(int enemyDamage) {
		this.curr = curr - enemyDamage;
	}

	/*
	 * Sets the current health to new health
	 */
	public void setCurrHealth(int newH) {
		curr = newH;
	}

	/*
	 * Sets alive if dead or alive
	 */
	public void setAlive(boolean aod) {
		this.alive = aod;
	}

	/*
	 * Returns boolean of it the boss is dead or alive
	 */
	public boolean getAlive() {
		return alive;
	}

	/*
	 * Returns a quadrant of the position of the boss (which should also be the
	 * position of the enemy) 6 = the enemy being on the same x coordinate as the
	 * player 5 = the enemy being on the same y coordinate as the player 4 = the
	 * enemy being in the top left corner 3 = the enemy being in the top right
	 * corner 2 = the enemy being in the bottom left corner 1 = the enemy being in
	 * the bottom right corner
	 */
	public int calcQuadrant() {
		double xDist = playerx - x;
		double yDist = playery - y;
		if (xDist == 0)
			return 6;
		else if (yDist == 0)
			return 5;
		else if (xDist > 0 && yDist > 0)
			return 4;
		else if (xDist < 0 && yDist > 0)
			return 3;
		else if (xDist > 0 && yDist < 0)
			return 2;
		else if (xDist < 0 && yDist < 0)
			return 1;
		else
			return -1;
	}

	/*
	 * Used to even things out. The program does not work if the difference between
	 * coordinates is odd. This helps fix things p = the players x or y coordinate e
	 * = the enemies x or y coordinate
	 */
	public double add1IfOdd(double p, double e) {
		if ((p - e) % 2 != 0) {
			return 1;
		}
		return 0;
	}

	/*****************************************************************************
	 * Lag Factors When the distance between the (player x and enemy x) and the
	 * (player y and enemy y) are different we end up with some problems. I.E. If
	 * the x distance is greater than the y distance then the program will simply
	 * move the boss to the players y coordinate, and then move along the x axis.
	 * This creates awkward movements. Lag Factors help keep the boss moving in a
	 * relatively straight line by adding additional speed to the movement to which
	 * ever coordinate has more ground to cover. Formula: if (distance between x
	 * coordinates) > (distance between y coordinates) (distance between y
	 * coordinates)/(distance between x coordinates) = |Ylagfactor| Ylagfactor is
	 * added to x values that need additional speed to catch up to y. (switch the xs
	 * and ys in the above formula to get the Xlagfactor)
	 *************************************************************************************/

	/*
	 * Calculates the Ylagfactor using setx and sety Returns 1 if xDist is less than
	 * yDist
	 */
	public double initialYLag() {
		double enemyx = setx;
		double enemyy = sety;
		double setxDist = playerx - enemyx;
		double setyDist = playery - enemyy;

		if (setxDist > setyDist)
			return Math.abs(setyDist / setxDist);
		else
			return 1.0;
	}

	/*
	 * Calculates the Ylagfactor using x and y Returns 1 if xDist is less than yDist
	 * This value will decrease as boss approaches player
	 */
	public double calcYLagFactor() {
		double xDist = playerx - x;
		double yDist = playery - y;

		if (xDist > yDist)
			return Math.abs(yDist / xDist);
		else
			return 1.0;
	}

	/*
	 * Calculates the Xlagfactor using setx and sety Returns 1 if yDist is less than
	 * xDist
	 */
	public double initialXLag() {
		double enemyx = setx;
		double enemyy = sety;
		double setxDist = playerx - enemyx;
		double setyDist = playery - enemyy;

		if (setxDist < setyDist)
			return Math.abs(setxDist / setyDist);
		else
			return 1.0;
	}

	/*
	 * Calculates the Xlagfactor using x and y Returns 1 if yDist is less than xDist
	 * This value will decrease as boss approaches player
	 */
	public double calcXLagFactor() {
		double xDist = playerx - x;
		double yDist = playery - y;

		if (xDist < yDist)
			return Math.abs(xDist / yDist);
		else
			return 1.0;
	}

	/*
	 * Uses multiple methods that move the boss towards the player
	 */
	public void tick() {

		whenSameY(playery);
		whenSameX(playerx);
		whenInQuad4(playerx, playery);
		whenInQuad3(playerx, playery);
		whenInQuad2(playerx, playery);
		whenInQuad1(playerx, playery);
	}

	/*
	 * Moves the boss towards the player when the enemy has the same x coordinate
	 * playerx = the players x value
	 */
	public void whenSameX(double playerx) {
		if (calcQuadrant() == 5 && Math.abs(playerx - x) >= 1) {
			if (x != playerx) {
				if (setx > playerx)
					x = x - 2;
				else
					x = x + 2;
			}
		}
	}

	/*
	 * Moves the boss towards the player when the enemy has the same y coordinate
	 * playery = the players y value
	 */
	public void whenSameY(double playery) {
		if (calcQuadrant() == 6 && Math.abs(playery - y) >= 1) {
			if (y != playery) {
				if (sety > playery)
					y = y - 2;
				else
					y = y + 2;
			}
		}
	}

	/*
	 * Moves the boss towards the player when it is top left of the player playerx =
	 * the players x value playery = the players y value
	 */
	public void whenInQuad4(double playerx, double playery) {
		if (calcQuadrant() == 4) {
			if (Math.abs((playerx - setx)) >= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x + (calcXLagFactor() / (initialXLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y + (calcYLagFactor() / (initialXLag() / 2));
			} else if (Math.abs((playerx - setx)) <= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x + (calcXLagFactor() / (initialYLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y + (calcYLagFactor() / (initialYLag() / 2));
			}
		}
	}

	/*
	 * Moves the projectile towards the player when it is top right of the player
	 * playerx = the players x value playery = the players y value
	 */
	public void whenInQuad3(double playerx, double playery) {
		if (calcQuadrant() == 3) {
			if (Math.abs((playerx - setx)) >= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x - (calcXLagFactor() / (initialXLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y + (calcYLagFactor() / (initialXLag() / 2));
			} else if (Math.abs((playerx - setx)) <= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x - (calcXLagFactor() / (initialYLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y + (calcYLagFactor() / (initialYLag() / 2));
			}
		}
	}

	/*
	 * Moves the boss towards the player when it is bottom left of the player
	 * playerx = the players x value playery = the players y value
	 */
	public void whenInQuad2(double playerx, double playery) {
		if (calcQuadrant() == 2) {
			if (Math.abs((playerx - setx)) <= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x + (calcXLagFactor() / (initialYLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y - (calcYLagFactor() / (initialYLag() / 2));
			} else if (Math.abs((playerx - setx)) >= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x + (calcXLagFactor() / (initialXLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y - (calcYLagFactor() / (initialXLag() / 2));
			}
		}
	}

	/*
	 * Moves the projectile towards the player when it is bottom right of the player
	 * playerx = the players x value playery = the players y value
	 */
	public void whenInQuad1(double playerx, double playery) {
		if (calcQuadrant() == 1) {
			if (Math.abs((playerx - setx)) <= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x - (calcXLagFactor() / (initialYLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y - (calcYLagFactor() / (initialYLag() / 2));
			} else if (Math.abs((playerx - setx)) >= Math.abs((playery - sety))) {
				if (Math.ceil(x) == Math.ceil(playerx)) {
				} else
					x = x - (calcXLagFactor() / (initialXLag() / 2));
				if (Math.ceil(y) == Math.ceil(playery)) {
				} else
					y = y - (calcYLagFactor() / (initialXLag() / 2));
			}
		}
	}

	/*
	 * Determines if the Boss has reached the players previous position
	 */
	public boolean reached() {
		if (Math.abs(playerx - x) > 20 || Math.abs(playery - y) > 20)
			return false;
		else
			return true;
	}

	/*
	 * Determines if the boss has touched the player
	 */
	public boolean hitsPlayer() {
		double px = Player.getPlayerLocation()[0] + add1IfOdd(Player.getPlayerLocation()[0], setx);
		double py = Player.getPlayerLocation()[1] + add1IfOdd(Player.getPlayerLocation()[1], sety);
		if (Math.abs(px - x) > 20 || Math.abs(py - y) > 20)
			return false;
		else
			return true;
	}

	// updates where the boss is moving to
	public void updatePlayerPos(Player pl) {
		pl.setSetx(Player.getX());
		pl.setSety(Player.getY());
	}

	// Some complex code to basically ensure the boss is hit only once
	// when they payer weapon touches it
	int i = 0;

	public boolean bossIsHit(Player p) {
		if (Math.abs(getBossX() - PlayerWeapon.getX()) <= 25 && Math.abs(getBossY() - PlayerWeapon.getY()) < 25) {
			i++;
		}
		if (i == 2) {
			takeDamage(p.getDamage());
			i = 0;
			return true;
		} else {
			return false;
		}
	}

	// checks if the boss has moved to the previous position of the player
	public boolean hitPrev() {
		if (Math.abs(playerx - x) <= 3 && Math.abs(playery - y) <= 3) {
			return true;
		} else
			return false;
	}

	/*
	 * Renders boss sprite
	 */
	public void render(Graphics g) {
		g.drawImage(enemyboss, (int) x, (int) y, null);
	}

}
