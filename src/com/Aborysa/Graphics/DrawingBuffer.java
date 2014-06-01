package com.Aborysa.Graphics;

public class DrawingBuffer {
	private byte[] pixels;
	private int width;
	private int type;
	private int size;
	
	public DrawingBuffer(int size, int type, int width){
		pixels = new byte[size*type];
		this.type = type;
		this.width = width;
		this.size = size;
	}
	
	public byte[] getPixel(int x, int y){
		byte[] temp = new byte[type];
		int index = (int) (x + Math.floor(y/width)) * type;
		for(int i=0; i< type;i++){
			temp[i] = pixels[index + i];
		}
		return temp;
	}
	public void fill(byte[] buff){
		if (buff.length != 0){
			float ratio = type/buff.length;
			int buffType = buff.length;
			if (ratio < 1){
				buffType = type;
			}
			for(int i=0; i < pixels.length;i+=type){
				for(int k=0; k < buffType; k++){
					pixels[i+k] = buff[k];
				}
			}		
		}
	}
	public void fill(int x, int y, int width, int height, byte[] buff, int type){
		if (buff.length != 0){
			float ratio = type/buff.length;
			int buffType = buff.length;
			if (ratio < 1){
				buffType = type;
			}
			for(int i=(x+y*this.width); i < (width*height);i+=type){
				if((int)((Math.floor(i/width) *this.width) + i%width) > (pixels.length)){
					break;
				}
				for(int k=0; k < buffType; k++){
						pixels[(int)((Math.floor(i/width) *this.width) + i%width)  +k] = buff[k];
				}
			}		
		}
		
	}
	public int getType(){
		return type;
	}
	public int getWidth(){
		return width;
	}
	public int getPixelCount(){
		return size;
	}
	public byte[] getPixels(){
		return pixels;
	}
	
}
