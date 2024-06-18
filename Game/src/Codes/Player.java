package Codes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.swing.ImageIcon;

public class Player {
	private int x, y, speed = 2, action = 0;
	private double count = 0.0;
	double scale = 2;
	double BowScale = 0.5;
	private int width = (int) (24 * scale), height = (int) (24 * scale);
	private List<List<Image>> sprites = new ArrayList<>();
	private List<List<Image>> dying = new ArrayList<>();
	
	private String[] paths = { "walking\\right", "walking\\bottom", "walking\\left", "walking\\top" };
	public boolean dead = false;
	
	public int speelType = 0;
	private double life = 100;
	private double lifeLimit = 100;
	private double mana = 200;
	private double manaLimit = 200;
	private boolean attacking = false;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.action = 0;
		this.count = 0;

		
		for (int i = 0; i < 4; i++) {
			this.sprites.add(this.load(paths[i], 4));
		}
		this.dying.add(this.load("dying", 4));
		
	}


	public List<Image> load(String path, int quant) {
		List<Image> imgs = new ArrayList<>(quant);
		for (int i = 1; i <= quant; i++) {
			ImageIcon referencia = new ImageIcon("src\\res\\girl sprites\\" + path + "\\" + i + ".png");
			imgs.add(referencia.getImage());
		}

		return imgs;
	}
	
	public Image deadAnimation() {
		
		this.count += 0.04;

		if (this.count > 3) {
			this.count = 0;
		}

		return this.dying.get(0).get((int) this.count);	
	}

	public Image animation(int direction, boolean[] movement) {
		if (this.action != direction) {
			this.action = direction;
			this.count = 0;
		}

		if (!movement[this.action]) {
			this.count = 0;
		} else {
			this.count += 0.15;
		}

		if (this.count > 3) {
			this.count = 0;
		}

		return this.sprites.get(this.action).get((int) this.count);	
	}
	
	public Rectangle PlayerRect() {
		Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
		return rect;
	}
	
	public void buffs(int type) {
		switch (type) {
			case 3:
			case 4:
				if (lifeLimit < life + 5) {
					life = lifeLimit;
				}else {
					life += 5;					
				}
				break;
			case 5:
			case 6:
				manaLimit += 5;
				break;
			case 7:
			case 8:
				lifeLimit += 5;
				break;
			case 9:
			case 10:
				if (manaLimit < mana + 5) {
					mana = manaLimit;
				}else {
					mana += 5;					
				}
				break;

			default:
				break;
		}
	}
	
	public void update(boolean[] movement) {
		if (mana < 200) {
			if (mana+0.2 > 200) {
				mana = 200;
			}else {
				mana += 0.2;				
			}
		}
		
		if (life <= 0) {
			dead = true;
		}
		
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

	public void render(Graphics g, int direction, boolean[] movement, int[] scroll) {
		Graphics2D graficos = (Graphics2D) g;
		Image img;
		if (!dead) {
			img = this.animation(direction, movement);
		}else {
			img = this.deadAnimation();
		}
		AffineTransform at = AffineTransform.getTranslateInstance(this.x - scroll[0], this.y - scroll[1]);
		at.scale(scale, scale);

		graficos.drawImage(img, at, null);
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<List<Image>> getSprites() {
		return sprites;
	}

	public void setSprites(List<List<Image>> sprites) {
		this.sprites = sprites;
	}

	public String[] getPaths() {
		return paths;
	}

	public void setPaths(String[] paths) {
		this.paths = paths;
	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public double getLifeLimit() {
		return lifeLimit;
	}

	public void setLifeLimit(double lifeLimit) {
		this.lifeLimit = lifeLimit;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public double getManaLimit() {
		return manaLimit;
	}

	public void setManaLimit(double manaLimit) {
		this.manaLimit = manaLimit;
	}
	
	

}
