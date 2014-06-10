package com.Aborysa.Graphics;

import java.awt.image.BufferedImage;

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
	public DrawingBuffer(BufferedImage image, byte depth){
		this.pixels = new byte[image.getWidth()*image.getHeight() + 1/4*image.getWidth()*image.getHeight()];
		width = image.getWidth();
		size =  this.pixels.length/5;
		type = 5;
		int[] pixels = new int[image.getWidth()*image.getHeight()*4];
		image.getRaster().getPixels(0, 0, image.getWidth(), image.getHeight(), pixels);		
		for(int i=0; i < this.pixels.length;i++){
			if((i-4)%5 == 0){
				this.pixels[i] = depth;
			}else if((i) % 5 == 0){
				this.pixels[i] = (byte) pixels[(int) (i - Math.floor(i/5) + 3)];
			}else{
				this.pixels[i] =  (byte) pixels[(int) (i - Math.floor(i/5))-1];
			}
		//	System.out.println("[" + (i) +  "]:" + ((int)this.pixels[i] & 0xFF));		
		}
		
	}
	public DrawingBuffer(int[] pixels, int width, byte depth, boolean B){
		
		this.pixels = new byte[pixels.length + 1/4 * pixels.length];
		this.type = 5;
		this.width = width;
		this.size = pixels.length;
		for(int i=0; i<this.pixels.length;i++){
			if(B){
				if(i % 5 == 0){
					this.pixels[i] = depth;
				}else{
					this.pixels[i] = (byte) pixels[i-(int)Math.floor(i/5)];
					//System.out.println("[" +i+ "]:" +pixels[i]);
					//if(pixels[i-(int)Math.floor(i/5)] > 0){
					//	System.exit(0);
					//} 
				}
			}else{
				for(int j=1; j < 4;j++){
					this.pixels[i+j] = (byte)(pixels[i] >> ( ( 8*(4-j) )  & 0xFF ) );
				}
			}
			this.pixels[i] = depth;
		}
	
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
