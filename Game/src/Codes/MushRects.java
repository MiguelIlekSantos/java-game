package Codes;

import java.awt.Rectangle;

public class MushRects {
	
	int type = 0;
	Rectangle rect = new Rectangle();
	
	MushRects(int type, Rectangle rect){
		this.type = type;
		this.rect = rect;
	}

	public int getType() {
		return type;
	}

	public Rectangle getRect() {
		return rect;
	}
	
	
}
