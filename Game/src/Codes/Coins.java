package Codes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Coins {
	int x, y;
	double scale = 2;
	
	List<List<Image>> coins = new ArrayList<>();
	double countCoinsAnimation = 0.0;	
	private int widthCoins = (int) (16 * scale), heightCoins = (int) (16 * scale);

	public Coins(int x, int y) {
		this.x = x;
		this.y = y;

		for (int i = 0; i < 4; i++) {
			this.coins.add(this.loadCoins(12));
		}
	}
	
	public List<Image> loadCoins(int quant) {
		List<Image> imgs = new ArrayList<>(quant);
		for (int i = 1; i < quant; i++) {
			ImageIcon referencia = new ImageIcon("src\\res\\Coins\\"+ i + ".png");
			imgs.add(referencia.getImage());
		}

		return imgs;
	}
	
	
	public Image coinsAnimation() {		
		this.countCoinsAnimation += 0.15;
		
		if (this.countCoinsAnimation > 5) {
			this.countCoinsAnimation = 0;
		}

		return coins.get(0).get((int) this.countCoinsAnimation);
	}
	
	public Rectangle coinRect() {
		Rectangle rect = new Rectangle(this.x, this.y, widthCoins, heightCoins);
		return rect;
	}
		
	public void render(Graphics g, int[] scroll) {
	    Graphics2D graficos = (Graphics2D) g;
	 
	    Image img = this.coinsAnimation();
	    
	    AffineTransform at = AffineTransform.getTranslateInstance(this.x - scroll[0], this.y - scroll[1]);
	    at.scale(scale, scale);
	    
	    graficos.drawImage(img, at, null);
	}

	
}
