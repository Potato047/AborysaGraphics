package com.Aborysa.Graphics.Sprite;

import java.awt.image.BufferedImage;
import java.util.Vector;

public class SpriteSheet{
	private BufferedImage img;
	private Vector<Float> texCord = new Vector<Float>(2);
	private int TILE_WIDTH;
	private int TILE_HEIGHT;
	public SpriteSheet(BufferedImage img, int TileW, int TileH){
		TILE_WIDTH = TileW;
		TILE_HEIGHT = TileH;
		this.img = img;
		texCord.set(0, (float)((float)(TILE_WIDTH)/img.getWidth()));
		texCord.set(1,(float)((float)(TILE_HEIGHT)/img.getHeight()));
	}
	public BufferedImage getTex(){
		return img;
	}
	public int getTileWitdth(){
		return TILE_WIDTH;
	}
	public int getTileHeight(){
		return TILE_HEIGHT;
	}
	public Vector<Float> getTexCords(int index){
		int xIndex = (int)(index%(img.getWidth() / (float)(TILE_WIDTH)));
		int yIndex = (int) Math.floor(index / ((img.getWidth() / (float)(TILE_WIDTH))));
		Vector<Float> temp = new Vector<Float>(4);
		temp.set(0,((xIndex*(float)(TILE_WIDTH)) / (img.getWidth())));
		temp.set(1,((float)(yIndex*(float)(TILE_WIDTH) /img.getHeight())));
		temp.set(2,texCord.get(0));
		temp.set(3,texCord.get(1));
	//	Point2f  sTemp = new Point2f(((xIndex*(float)(TILE_WIDTH)) / (img.getWidth())),(float)(yIndex*(float)(TILE_WIDTH) /img.getHeight()),false);
	//	Vector2f tempVec = new Vector2f(texCord.get(0)+sTemp.getX(),texCord.get(1)+sTemp.getY(),sTemp);
		return temp;	
	}

}