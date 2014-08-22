package com.Aborysa.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class DrawingBuffer2 {
	private int[] pixels;
	private int[] depthBuffer;
	private int width;
	private int BgColor = 0;
	public DrawingBuffer2(int width,int height){
		pixels = new int[width * height];
		depthBuffer = new int[width * height];
	}
	public DrawingBuffer2(BufferedImage img){
		pixels =  ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		width = img.getWidth();
		depthBuffer = new int[img.getWidth() * img.getHeight()];
	}
	
	public int getPixel(int x, int y){
		return pixels[x + y*width];
	}
	public int getPixel(int index){
		return pixels[index];
	}
	public void fill(int color){
		for(int i=0; i< pixels.length;i++){
			pixels[i] = color;
		}
	}
	public void fill(int x, int y, int width, int height, int color){
		for(int i=(x+y*width); i< (x+y*this.width) + width + height*this.width;i++){
			pixels[i] = color;
		}
	}
	public void setBgColor(int color){
		BgColor = color;
	}
	public void clear(){
		for(int i=0; i < pixels.length;i++){
			pixels[i] = BgColor;
			depthBuffer[i] = 0;
		}
	}
	public void plotPixel(int x, int y,int color, byte depth){
		int index = x+(y*width);
		plotPixel(index, color, depth);
	}
	public void plotPixel(int index,int color, byte depth){
		if (depthBuffer[index] < depth){
			depthBuffer[index] = depth;
			pixels[index] = color;
		}
	}
	public void plotPixelBlend(int x, int y,int color, int depth, BlendMode mode){
		int index = x+(y*width);
		plotPixelBlend(index, color, depth, mode);
	}
	public void plotPixelBlend(int index,int color, int depth, BlendMode mode){
		if (depthBuffer[index] <= depth){
			depthBuffer[index] = depth;
			pixels[index] = mode.getBlend(pixels[index], color);
		}else{
			pixels[index] = mode.getBlend(color, pixels[index]);
		}
	}
	public int getWidth(){
		return width;
	}
	public int getPixelCount(){
		return pixels.length;
	}
	public int[] getPixels(){
		return pixels;
	}
	
}
