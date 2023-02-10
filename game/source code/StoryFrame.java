import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class StoryFrame {
	// the save town button that moves to the level one frame
	public Rectangle saveTown = new Rectangle(Main.WIDTH / 2 + 45, 390, 250, 50);

	// renders the story description
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font textStyle = new Font("Times New Roman", Font.BOLD, 25);
		g.setFont(textStyle);
		g.setColor(Color.white);
		g.drawString("One week ago a mysterious group of people appeared", 5, 70);
		g.drawString("in your neighborhood. They have been holding your", 5, 120);
		g.drawString("entire town for ransom, and The President has refused", 5, 160);
		g.drawString("to pay it. No one is coming.", 5, 210);
		g.drawString("The only one who can save your town is you!", 5, 260);
		g.drawString("Under the guise of night you have infiltrated", 5, 310);
		g.drawString("their base and diguised yourself as one of them...", 5, 360);
		Font saveTownFont = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(saveTownFont);
		g.setColor(Color.green);
		g.drawString("Save Your Town!", saveTown.x + 50, saveTown.y + 30);
		g2d.draw(saveTown);
	}
}
