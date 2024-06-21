package Codes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Speel {
	int x, y, speed = 0;
	double scale = 1;
	
	String[] pathsSpeels = { "blue", "dark", "green", "orange" };
	List<List<Image>> speels = new ArrayList<>();
	double countSpeelAnimation = 0.0;	
	private int widthSpeel = (int) (74 * scale), heightSpeel = (int) (52 * scale);
	public int speelType = 0;
	public int direction = 0;
	int[] clickLoc = {0, 0};

	public Speel(int x, int y, int direction, int speed, int speelType, int scale) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.speelType = speelType;
		this.scale = scale;
    	this.direction = direction;

		for (int i = 0; i < 4; i++) {
			this.speels.add(this.loadSpeels(pathsSpeels[i], 6));
		}
	}
	
	public int[] getClickLoc() {
		return clickLoc;
	}

	public void setClickLoc(int[] clickLoc) {
		this.clickLoc = clickLoc;
	}

	public List<Image> loadSpeels(String path, int quant) {
		List<Image> imgs = new ArrayList<>(quant);
		for (int i = 0; i < quant; i++) {
			ImageIcon referencia = new ImageIcon("src\\res\\Speels\\"+ path +"\\" + i + ".png");
			imgs.add(referencia.getImage());
		}

		return imgs;
	}
	
	
	public Image speelAnimation(int speelType) {		
		this.countSpeelAnimation += 0.15;
		
		if (this.countSpeelAnimation > 5) {
			this.countSpeelAnimation = 0;
		}

		return speels.get(speelType).get((int) this.countSpeelAnimation);
	}
	
	public Rectangle speelRect() {
		Rectangle rect = new Rectangle(this.x, this.y, widthSpeel, heightSpeel);
		return rect;
	}
	
	
	public void update() {
		if (this.direction == 1) {
			this.x += this.speed;
		}
		if (this.direction == 2) {
			this.x -= this.speed;
		}
		if (this.direction == 3) {
			this.y += this.speed;
		}
		if (this.direction == 4) {
			this.y -= this.speed;
		}
	}
	
	public void render(Graphics g, int[] scroll) {
	    Graphics2D graficos = (Graphics2D) g;
	 
	    Image img = this.speelAnimation(this.speelType);
	    
	    AffineTransform at = AffineTransform.getTranslateInstance(this.x - scroll[0], this.y - scroll[1]);
	    at.scale(scale, scale);
	    
	    if (this.direction == 2) {
	    	at.rotate(Math.PI, img.getWidth(null) / 2, img.getHeight(null) / 2);
		}
	    if (this.direction == 3) {
	    	at.rotate(Math.PI/2, img.getWidth(null) / 2, img.getHeight(null) / 2);
		}
	    if (this.direction == 4) {
	    	at.rotate(Math.PI*3/2, img.getWidth(null) / 2, img.getHeight(null) / 2);
		}
	    
	    graficos.drawImage(img, at, null);
	}

	
}
