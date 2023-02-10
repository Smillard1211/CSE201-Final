import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DamagePack {

	private double x;
	private double y;
	private BufferedImage DP;
	
	/*
	 * x is the x pos
	 * y is the y pos
	 * Main is an instance of the main class
	 */
	public DamagePack(double x, double y, Main game) {
		this.x = x;
		this.y = y;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheetDP());
		DP = ss.grabImage(1, 1, 64, 64);
	}
	
	/*
	 * Sets the x position of the damage pack
	 */
	public void setX(double newx) {
		this.x = newx;
	}
	
	/*
	 * Sets the y position of the damage pack 
	 */
	public void setY(double newy) {
		this.y = newy;
	}
	
	/*
	 * Checks if player has walked over the pack
	 */
	public boolean playerWalksOver(Player p) {
		if(Math.abs(p.getX()-x)<=5 && Math.abs(p.getY()-y)<=5) {
			if(p.getDamage() != 8) {;
			p.setDamage(p.getDamage()+2);
			}
			return true;
		}
		return false;
	}
	
	/*
	 * renders the sprite for the damage upgrade
	 */
	public void render(Graphics g) {
		g.drawImage(DP,  (int)x,  (int)y, null);
	}
}
