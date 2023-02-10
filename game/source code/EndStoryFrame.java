import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class EndStoryFrame {
	// button to move to the credits page
	public Rectangle goToCredits = new Rectangle(Main.WIDTH / 2 + 325, 350, 100, 50);

	// renders the End of story text
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font textStyle = new Font("Times New Roman", Font.BOLD, 25);
		g.setFont(textStyle);
		g.setColor(Color.white);
		g.drawString("You have beaten these thugs!", 5, 70);
		g.drawString("The President has awarded you a medal.", 5, 120);
		g.drawString("(You throw it away because The President didn't help you)", 5, 160);
		g.drawString("You are now the hero of your town.", 5, 210);
		g.drawString("The town of Terrabyte", 5, 260);
		g.drawString("END", 300, 360);
		Font goToCreditsFont = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(goToCreditsFont);
		g.setColor(Color.green);
		g.drawString("Next", goToCredits.x + 30, goToCredits.y + 30);
		g2d.draw(goToCredits);
	}
}
