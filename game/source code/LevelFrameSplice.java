import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.sql.Date;
import java.util.Timer;

public class LevelFrameSplice {
	// button that will move to the game after a new level is reached
	public Rectangle proceedButton = new Rectangle(Main.WIDTH / 2 + 300, 150, 150, 50);

	// renders the frame for each level
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (Main.Level1 == true) {
			Font textStyle = new Font("Times New Roman", Font.BOLD, 40);
			g.setFont(textStyle);
			g.setColor(Color.red);
			g.drawString("LEVEL ONE", Main.WIDTH / 5, 70);
			Font proceedFont = new Font("Times New Roman", Font.BOLD, 20);
			g.setFont(proceedFont);
			g.setColor(Color.white);
			g.drawString("PROCEED", proceedButton.x + 25, proceedButton.y + 33);
			g2d.draw(proceedButton);

		} else if (Main.Level2 == true) {
			Font textStyle = new Font("Times New Roman", Font.BOLD, 40);
			g.setFont(textStyle);
			g.setColor(Color.red);
			g.drawString("LEVEL TWO", Main.WIDTH / 5, 70);
			Font proceedFont = new Font("Times New Roman", Font.BOLD, 20);
			g.setFont(proceedFont);
			g.setColor(Color.white);
			g.drawString("PROCEED", proceedButton.x + 25, proceedButton.y + 33);
			g2d.draw(proceedButton);

		} else if (Main.Level3 == true) {
			Font textStyle = new Font("Times New Roman", Font.BOLD, 40);
			g.setFont(textStyle);
			g.setColor(Color.red);
			g.drawString("LEVEL THREE", Main.WIDTH / 5, 70);
			Font proceedFont = new Font("Times New Roman", Font.BOLD, 20);
			g.setFont(proceedFont);
			g.setColor(Color.white);
			g.drawString("PROCEED", proceedButton.x + 25, proceedButton.y + 33);
			g2d.draw(proceedButton);
		} else if (Main.Level4 == true) {
			Font textStyle = new Font("Times New Roman", Font.BOLD, 40);
			g.setFont(textStyle);
			g.setColor(Color.red);
			g.drawString("LEVEL FOUR", Main.WIDTH / 5, 70);
			Font proceedFont = new Font("Times New Roman", Font.BOLD, 20);
			g.setFont(proceedFont);
			g.setColor(Color.white);
			g.drawString("PROCEED", proceedButton.x + 25, proceedButton.y + 33);
			g2d.draw(proceedButton);
		} else if (Main.Finallevel == true) {
			Font textStyle = new Font("Times New Roman", Font.BOLD, 40);
			g.setFont(textStyle);
			g.setColor(Color.red);
			g.drawString("SOMETHING APPROACHES...", Main.WIDTH / 5, 70);
			Font proceedFont = new Font("Times New Roman", Font.BOLD, 20);
			g.setFont(proceedFont);
			g.setColor(Color.white);
			g.drawString("PROCEED", proceedButton.x + 25, proceedButton.y + 33);
			g2d.draw(proceedButton);
		}
	}
}
