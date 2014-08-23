package com.Aborysa.Graphics.Sprite;

import java.util.Vector;

public class Sprite{
	private int imageIndex = 0;
	private int sIndex, indexLength;
	private SpriteSheet sheet;
	
	public Sprite(SpriteSheet sheet, int sIndex, int indexL){
		this.sheet = sheet;
		this.sIndex = sIndex;
		this.indexLength = indexL;
	}
	public void setImageIndex(int index){
		imageIndex = (index % indexLength);
	}
	public int getSindex(){
		return sIndex;
	}
	public int getIndexLength(){
		return indexLength;
	}
	public SpriteSheet getSheet(){
		return sheet;
	}
	public int getImageIndex(){
		return imageIndex;
	}
	public void advance(){
		imageIndex = ((imageIndex+1) % indexLength);
	}
	public Vector<Float> getTexCords(){
		return sheet.getTexCords(imageIndex+sIndex);

	}
	
}