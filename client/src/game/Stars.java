package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Stars {
	public static final int DownRock = 0;
	public static final int NormalRock = 1;
	
	private PApplet parent;
	private PImage image;
	private int x, y, w, h;
	
	public Stars(PApplet parent, PImage image, int x, int y) {
		this.parent = parent;
		this.image = image;
		this.x = x;
		this.y = y;
		this.w = image.width;
		this.h = image.height;
		
	}
	
	public void display(boolean move){
		if(move)
			this.y += 2;
		this.parent.image(this.image, this.x, this.y, this.w, this.h);
	}
	
	public int getColor(){
		return parent.get(this.x+this.w/2, this.y+this.h/2);
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	
	public int getW(){
		return this.w;
	}
	
}
