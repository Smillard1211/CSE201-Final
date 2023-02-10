import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Credits {
	// button for moving back to the menu page. Should reset every thing when
	// clicked
	public Rectangle skipButton = new Rectangle(Main.WIDTH / 2 + 375, 390, 85, 50);

	// renders the credits
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font textStyle = new Font("Times New Roman", Font.BOLD, 25);
		g.setFont(textStyle);
		g.setColor(Color.white);
		g.drawString("Sprites By...........Chris Pittman", 5, 40);
		g.drawString("Upgrade System By....Chris Pittman", 5, 90);
		g.drawString("Player By............Sean Millard", 5, 140);
		g.drawString("Background By........Sean Millard", 5, 190);
		g.drawString("Enemies By...........Colin Wilder", 5, 240);
		g.drawString("Boss By..............Colin Wilder", 5, 290);
		g.drawString("Story By.............Colin Wilder", 5, 340);
		g.drawString("Thank You For Playing............", 5, 380);
		// renders button(not a real button just a section a mouse can click on)
		Font skipButtonFont = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(skipButtonFont);
		g.setColor(Color.green);
		g.drawString("Skip", skipButton.x + 20, skipButton.y + 30);
		g2d.draw(skipButton);
	}
}
