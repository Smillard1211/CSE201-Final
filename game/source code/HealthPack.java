import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class HealthPack {

	private double x;
	private double y;
	private BufferedImage HP;

	/*
	 * x is the x pos of the health pack y is the y pos of the health pack game is
	 * an instance of our main class
	 */
	public HealthPack(double x, double y, Main game) {
		this.x = x;
		this.y = y;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetHP());
		HP = ss.grabImage(1, 1, 64, 64);
	}

	public boolean spawnHealth(Regular_Enemy re) {
		int rand = (int) (Math.random() * 10);
		if (re.getAlive() == false) {
			if (rand >= 5) {
				return true;
			} else if (rand < 5) {
				return false;
			}
		}
		return false;
	}

	/*
	 * Checks if player have touched it
	 */
	public boolean playerWalksOver(Player p) {
		if (Math.abs(p.getX() - x) <= 5 && Math.abs(p.getY() - y) <= 5) {
			p.setCurrent(p.getCurrent() + 2);
			return true;
		}
		return false;
	}

	/*
	 * Renders the sprite for the health upgrade
	 */
	public void render(Graphics g) {
		g.drawImage(HP, (int) x, (int) y, null);
	}
}
