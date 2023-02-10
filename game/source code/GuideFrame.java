import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GuideFrame {
	// button to go back to menu
	public Rectangle backButton = new Rectangle(Main.WIDTH / 2 + 110, 10, 100, 40);

	// renders the guide for the player
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font textStyle = new Font("Times New Roman", Font.BOLD, 25);
		g.setFont(textStyle);
		g.setColor(Color.white);
		g.drawString("Up Arrow: Move Player Up", 5, 80);
		g.drawString("Down Arrow: Move Player Down", 5, 120);
		g.drawString("Right Arrow: Move Player Right", 5, 170);
		g.drawString("Left Arrow: Move Player Left", 5, 220);
		g.drawString("Space Bar: Fires When Bullet Is Off Screen", 5, 270);
		g.drawString("P Key: Pauses The Game", 5, 320);
		g.drawString("At the end of each level you will have a choice", 5, 370);
		g.drawString("between extra health and extra damage.", 5, 400);
		g.drawString("There is a chance that an enemy performs a critical hit.", 5, 440);
		Font backButtonFont = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(backButtonFont);
		g.setColor(Color.green);
		g.drawString("Back", backButton.x + 30, backButton.y + 25);
		g2d.draw(backButton);
	}
}
