import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Menu {

	// buttons to start the game, go to the guide, or close the program
	public Rectangle beginButton = new Rectangle(Main.WIDTH / 2 + 100, 150, 100, 50);
	public Rectangle quitButton = new Rectangle(Main.WIDTH / 2 + 100, 220, 100, 50);
	public Rectangle guideButton = new Rectangle(Main.WIDTH / 2 + 100, 290, 100, 50);

	// renders title and buttons
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font textStyle = new Font("Times New Roman", Font.BOLD, 40);
		g.setFont(textStyle);
		g.setColor(Color.red);
		g.drawString("GANGSTA'S TERRABYTE", Main.WIDTH / 5, 70);

		Font beginFont = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(beginFont);
		g.setColor(Color.white);
		g.drawString("BEGIN", beginButton.x + 17, beginButton.y + 33);
		g2d.draw(beginButton);

		Font quitFont = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(quitFont);
		g.setColor(Color.white);
		g.drawString("QUIT", quitButton.x + 24, quitButton.y + 33);
		g2d.draw(quitButton);

		Font guideFont = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(guideFont);
		g.setColor(Color.white);
		g.drawString("GUIDE", guideButton.x + 17, guideButton.y + 33);
		g2d.draw(guideButton);

		Font footer = new Font("Times New Roman", Font.PLAIN, 10);
		g.setFont(footer);
		g.setColor(Color.white);
		g.drawString("Copyright Sean Millard, Chris Pittman, Colin Wilder: 11/7/2022", 2, (Main.HEIGHT * 2) - 10);

	}
}
