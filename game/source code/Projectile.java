import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Projectile {

	// x and y will be the values that change as the projectile moves
	private double x;
	private double y;
	// setx and sety will be the initial postions of the projectles.
	// these should always be set to getEnemyX() and getEnemyY() in the Main class
	private double setx;
	private double sety;
	// the variable that will make the projectile graphics visible
	private BufferedImage projectile;

	double playerx = Player.getX() + add1IfOdd(Player.getX(), setx);
	double playery = Player.getY() + add1IfOdd(Player.getY(), sety);

	/*
	 * x = the initial setx that will change as it moves, should always be getEnemyX()
	 * y = the initial sety that will change as it moves, should always be getEnemyY()
	 * setx = the initial x position that does not change, should always be getEnemyX()
	 * sety = the initial y position that does not change, should always be getEnemyY()
	 * game = the class that runs the game. Needed to get the sprite sheet of the projectile
	 */
	public Projectile(double x, double y, double setx, double sety, Main game) {
		this.x = x;
		this.y = y;
		this.setx = setx;
		this.sety = sety;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetE());
		projectile = ss.grabImageSmall(1, 1, 32, 32);
	}

	/*
	 * Returns the set x value
	 */
	public double getSetX() {
		return setx;
	}

	/*
	 * Returns the set y value
	 */
	public double getSetY() {
		return sety;
	}

	// returns the x value
	public double getX() {
		return x;
	}

	// returns the y value
	public double getY() {
		return y;
	}

	// sets the setX
	public void setSetX(double nsx) {
		this.setx = nsx;
	}

	// sets the setY
	public void setSetY(double nsy) {
		this.sety = nsy;
	}

	// sets x
	public void setX(double nx) {
		this.x = nx;
	}

	// sets y
	public void setY(double ny) {
		this.y = ny;
	}

	/*
	 * Returns a quadrant of the position of the projectile
	 * (which should also be the position of the enemy)
	 * 6 = the enemy being on the same x coordinate as the player
	 * 5 = the enemy being on the same y coordinate as the player
	 * 4 = the enemy being in the top left corner
	 * 3 = the enemy being in the top right corner
	 * 2 = the enemy being in the bottom left corner
	 * 1 = the enemy being in the bottom right corner
	 */
	public int calcQuadrant() {
		double xDist = playerx - x;
		double yDist = playery - y;
		if (Math.abs(xDist) <= 1)
			return 6;
		else if (Math.abs(yDist) <= 1)
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
	 * Used to even things out. The program does not work if the difference
	 * between coordinates is odd. This helps fix things
	 * p = the players x or y coordinate
	 * e = the enemys x or y coordniate
	 */
	public double add1IfOdd(double p, double e) {
		if ((p - e) % 2 != 0) {
			return 1;
		}
		return 0;
	}

	/***************************************************************************** Lag Factors
	 * When the distance between the (player x and enemy x) and the (player y and enemy y)
	 * are different we end up with some problems. I.E. If the x distance is greater than
	 * the y distance then the program will simply move the projectile to the players y 
	 * coordinate, and then move along the x axis. This creates awkward movements.
	 * Lag Factors help keep the projectile moving in a relatively straight line by
	 * adding additional speed to the movement to which ever coordinate 
	 * has more ground to cover.
	 * Formula:
	 * if (distance between x coordinates) > (distance between y coordinates) 
	 *    (distance between y coordinates)/(distance between x coordinates) = |Ylagfactor|
	 *    Ylagfactor is added to x values that need additional speed to catch up to y.
	 *    (switch the xs and ys in the above formula to get the Xlagfactor) 
	 *  *************************************************************************************/
	
	/*
	 * Calculates the Ylagfactor using  setx and sety 
	 * Returns 1 if xDist is less than yDist
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
	 * Calculates the Ylagfactor using  x and y 
	 * Returns 1 if xDist is less than yDist
	 * This value will decrease as projectile approaches player
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
	 * Calculates the Xlagfactor using  setx and sety 
	 * Returns 1 if yDist is less than xDist
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
	 * Calculates the Xlagfactor using  x and y 
	 * Returns 1 if yDist is less than xDist
	 * This value will decrease as projectile approaches player
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
	 * Uses multiple methods that move the projectile towards the player
	 */
	public void tick() {
		// updatePlayerPos();
		whenSameY(playery);
		whenSameX(playerx);
		whenInQuad4(playerx, playery);
		whenInQuad3(playerx, playery);
		whenInQuad2(playerx, playery);
		whenInQuad1(playerx, playery);
	}

	/*
	 * Moves the projectile towards the player when the enemy has the same x coordinate
	 * playerx = the players x value
	 */
	public void whenSameX(double playerx) {
		if (calcQuadrant() == 6 && Math.abs(playerx - x) <= 1) {
			if (sety > playery)
				y = y - 2;
			else if (sety < playery)
				y = y + 2;
		}
	}

	/*
	 * Moves the projectile towards the player when the enemy has the same y coordinate
	 * playery = the players y value
	 */
	public void whenSameY(double playery) {
		if (calcQuadrant() == 5 && Math.abs(playery - y) <= 1) {
			if (setx > playerx)
				x = x - 2;
			else if (setx < playerx)
				x = x + 2;
		}
	}

	/*
	 * Moves the projectile towards the player when it is top left of the player
	 * playerx = the players x value
	 * playery = the players y value
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
	 * playerx = the players x value
	 * playery = the players y value
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
	 * Moves the projectile towards the player when it is bottom left of the player
	 * playerx = the players x value
	 * playery = the players y value
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
	 * playerx = the players x value
	 * playery = the players y value
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
	 * Checks if previous postion of the player has been hit
	 */
	public boolean hitPrev() {
		if (Math.abs(playerx - x) <= 3 && Math.abs(playery - y) <= 3) {
			return true;
		} else
			return false;
	}

	/*
	 * Updates the players previous position. Helps enemy track the player
	 */
	public void updatePlayerPos() {
		Player.setSetx(Player.getX());
		Player.setSety(Player.getY());
	}

	public void returnToEnemy(Regular_Enemy re) {
		setSetX(re.getEnemyX());
		setSetY(re.getEnemyY());
		setX(re.getEnemyX());
		setY(re.getEnemyY());
	}

	/*
	 * Creates the projectile image
	 */
	public void render(Graphics g) {
		g.drawImage(projectile, (int) x, (int) y, null);
	}

}