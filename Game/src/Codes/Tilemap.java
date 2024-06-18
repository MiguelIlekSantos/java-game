package Codes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

public class Tilemap {
	private static int SIZE = 35;
	public int tile_size = 42;
	public int mush_size = 16;
	public int incrementor = 0;
	double scale = 2;
	
	Random random = new Random();
	
	List<Image> tiles = new ArrayList<>();
	List<Image> pinkEnemy = new ArrayList<>();
	List<Image> greenEnemy = new ArrayList<>();
	List<Image> coins = new ArrayList<>();
	List<Image> potions = new ArrayList<>();
	
	Map<String, List<Integer>> tilemap = new HashMap<>();
	public List<MushRects> mushRects = new ArrayList<>();
	
	public List<MushRects> getMushRects() {
		return mushRects;
	}
	public void setMushRects(List<MushRects> mushRects) {
		this.mushRects = mushRects;
	}


	public List<Image> loadImages(int limite, String path){
		List<Image> imgs = new ArrayList<>(limite);
		for (int i = 1; i <= limite; i++) {
			ImageIcon referencia = new ImageIcon("src\\res\\" + path + "\\" + i + ".png");
			imgs.add(referencia.getImage());
		}

		return imgs;
	}
	
	Tilemap(){
		this.tiles = this.loadImages(19, "Tiles\\Grounds");
		this.pinkEnemy = this.loadImages(12, "Enemys\\Pink");
		this.greenEnemy = this.loadImages(12, "Enemys\\Green");
		this.coins = this.loadImages(12, "Coins");
		this.potions = this.loadImages(4, "Tiles\\potions");
		
		incrementor = -2;
		for (int i = 0; i < SIZE; i++) {
			incrementor += 1;
            for (int j = 0; j < SIZE; j++) {
                List<Integer> tile = new ArrayList<>();
                if (j <= incrementor) {
                	tile.add(0); 					
				}else {
					tile.add(1);
				}
                
                tile.add(i);
                tile.add(j);
                
                String key = String.format("%02d%02d", i, j);
                tilemap.put(key, tile);
            }
        }
	
	}
	
	public void generateMushrooms() {
		Set<String> keys = tilemap.keySet();
        for (String key : keys) {            
            int chance = random.nextInt(100000);
            
            if (chance == 1) {
            	int x = random.nextInt(10, 1400);
                int y = random.nextInt(10, 1400);
                
                int type = random.nextInt(3, 10);
                	
               	Rectangle newRect = new Rectangle(x, y, (int)(mush_size * scale), (int)(mush_size * scale));
               	MushRects mushRect = new MushRects(type, newRect);
                mushRects.add(mushRect);  
			}         
        }
		
	}

	public void render(Graphics2D g, int[] scroll) {
		Graphics2D graficos = (Graphics2D) g;
		
		generateMushrooms();
			
		Set<String> keys = tilemap.keySet();
        for (String key : keys) {            
            List<Integer> values = tilemap.get(key);
            
    		AffineTransform at = AffineTransform.getTranslateInstance(values.get(1) * this.tile_size - scroll[0], values.get(2) * this.tile_size - scroll[1]);
    		at.scale(scale, scale);
    		graficos.drawImage(this.tiles.get(values.get(0)), at, null);
        }
        
        for (MushRects mushRect : mushRects) {
    		AffineTransform at = AffineTransform.getTranslateInstance(mushRect.getRect().x - scroll[0], mushRect.getRect().y - scroll[1]);
    		at.scale(scale, scale);
    		graficos.drawImage(this.tiles.get(mushRect.getType()), at, null);
		}   
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
