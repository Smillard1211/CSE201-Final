import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;

	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	// grabs the sprite sheet that is 64x64
	public BufferedImage grabImage(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col * 64) - 64, (row * 64) - 64, width, height);
		return img;
	}

	// grabs the sprite sheet that is 32x32
	public BufferedImage grabImageSmall(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
		return img;
	}

	// grabs the sprite sheet that is 96x96
	public BufferedImage grabImageLarge(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col * 96) - 96, (row * 96) - 96, width, height);
		return img;
	}
}
