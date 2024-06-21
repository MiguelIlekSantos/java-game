package Codes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class Enemy {
	private int variant = 0;
	public double x, y, speed = 0.0;
	public boolean[] movement = new boolean[] { false, false, false, false };
	private double count = 0.0;
	double scale = 2;
	private int width = (int) (24 * scale), height = (int) (24 * scale);
	private List<List<Image>> sprites = new ArrayList<>();
	private String[] paths = { "Green", "Pink"};
	public boolean die = false;
	
	private double life = 100;
	private double lifeLimit = 100;
	public int damage = 5;
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public Enemy(int x, int y, int variant, double speed) {
		this.speed = speed;
		this.variant = variant;
		this.x = x;
		this.y = y;
		this.count = 0;

		for (int i = 0; i < 2; i++) {
			this.sprites.add(this.load(paths[i]));
		}
	}

	public List<Image> load(String path) {
		List<Image> imgs = new ArrayList<>(12);
		for (int i = 1; i <= 12; i++) {
			ImageIcon referencia = new ImageIcon("src\\res\\Enemys\\" + path + "\\" + i + ".png");
			imgs.add(referencia.getImage());
		}

		return imgs;
	}
	
	
	
	public void dying() {
		die = true;	
	}

	public Image animation(int variant) {		
		this.count += 0.15;
		
		if (this.count > 10) {
			this.count = 0;
		}

		return this.sprites.get(variant).get((int) this.count);
	}
	
	public Rectangle EnemyRect() {
		Rectangle rect = new Rectangle((int) this.x + 5,(int) this.y + 5, this.width - 0, this.height - 20);
		return rect;
	}
	 
	public void update() {
		if (movement[0]) {
			this.x += this.speed;
			if (this.x + this.speed > 1420) {
				this.x -= this.speed;
			}
		}
		if (movement[1]) {
			this.y += this.speed;
			if (this.y + this.speed > 1420) {
				this.y -= this.speed;
			}
		}
		if (movement[2]) {
			this.x -= this.speed;
			if (this.x - this.speed < 0) {
				this.x += this.speed;
			}
		}
		if (movement[3]) {
			this.y -= this.speed;
			if (this.y - this.speed < 0) {
				this.y += this.speed;
			}
		}

	}

	public void render(Graphics g, int[] scroll) {
		Image img;
		
		if (!die) {
			img = this.animation(this.variant);
		} else {
			img = sprites.get(variant).get(11);
		}

		AffineTransform at = AffineTransform.getTranslateInstance(this.x - scroll[0], this.y - scroll[1]);
		at.scale(scale, scale);

		Graphics2D graficos = (Graphics2D) g;
		graficos.drawImage(img, at, null);
		
	}

	
	public boolean[] getMovement() {
		return movement;
	}

	public void setMovement(boolean[] movement) {
		this.movement = movement;
	}

	

}
