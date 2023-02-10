import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class HealthBar {
	private double x;
	private double y;
	private static int height;
	private BufferedImage HB;

	/*
	 * x is the x pos of the health bar y is the y pos of the health bar height is
	 * where you are grabbing from the sprite sheet Main is an instance of our main
	 * class
	 */
	public HealthBar(double x, double y, int height, Main game) {
		this.x = x;
		this.y = y;
		this.height = height;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetHB());
		HB = ss.grabImageLarge(1, height, 96, 96);
	}

	/*
	 * checks if the player is hit
	 */
	public boolean decreaseHealth(Player p, Projectile e, Regular_Enemy re) {
		if (p.playerIsHit(e, re) == true)
			return true;
		else
			return false;
	}

	/*
	 * Increases the health when a health back is picked up
	 */
	public boolean increaseHealth(boolean gethp) {
		if (gethp == true)
			return true;
		else
			return false;
	}

	/*
	 * Sets what sprite you use. IE the length of the health bar
	 */
	public static void setHeight(int h) {
		height = h;
	}

	/*
	 * Gets the height
	 */
	public int getHeight() {
		return height;
	}

	// renders the health bar
	public void render(Graphics g) {
		g.drawImage(HB, (int) x, (int) y, null);
	}
}
