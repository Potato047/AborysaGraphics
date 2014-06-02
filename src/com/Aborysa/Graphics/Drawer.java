package com.Aborysa.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
	
public class Drawer {
	//This class will organize drawing
	private static boolean bufferDraw = false;
	private static boolean canvasDraw = false;
	private static boolean imageDraw = false;
	private static DrawingBuffer bufferTarget = null;
	private static BufferedImage imageTarget = null;
	private static Canvas canvasTarget = null;
	private static byte depth = 0;
	private static BlendMode blendMode;
	private static Color color;
	public static void setTarget(Canvas canvas){
		bufferDraw = false;
		canvasDraw = true;
		imageDraw = false;
		canvasTarget = canvas;	
	}
	
	public static void setTarget(DrawingBuffer buffer){
		bufferDraw = true;
		canvasDraw = false;
		imageDraw = false;
		bufferTarget = buffer;
	}
	public static void setTarget(BufferedImage image){
		bufferDraw = false;
		canvasDraw = false;
		imageDraw = true;
		imageTarget = image;
	}
	public static void drawImage(int x, int y, BufferedImage image){
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		int width = image.getWidth();
		DrawingBuffer buffer = new DrawingBuffer(pixels,width,depth);
		drawBuffer(x,y,buffer);
	}
	public static void drawImage(int x, int y, Image image){
		BufferedImage image2 = (BufferedImage) image;
		drawImage(x,y,image2);
	}
	public static void drawBuffer(int x, int y, DrawingBuffer buffer){
		int type = buffer.getType();
		int width = buffer.getWidth();
		if(bufferDraw){
			
		}else if(canvasDraw){
			BufferedImage tempImg = new BufferedImage(buffer.getWidth(),buffer.getPixelCount()/buffer.getWidth(),BufferedImage.TYPE_INT_ARGB);
			int[] pixels = ((DataBufferInt)tempImg.getRaster().getDataBuffer()).getData();
			byte[] bufferPix = buffer.getPixels();
			for(int i=0; i<(bufferPix.length)/buffer.getType();i++){
				for(int j=0; j < buffer.getType(); j++){
					pixels[i] += ((int)(bufferPix[i*buffer.getType() + j] &0xFF)<< 8*((buffer.getType()-1)-j));
				}
			}
			System.out.println("Width: " + tempImg.getWidth() + ", Height: " + tempImg.getHeight());
			canvasTarget.getBufferStrategy().getDrawGraphics().drawImage(tempImg, x, y, null);
		}else if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			byte[] bufferPix = buffer.getPixels();
			int index = x + y * imageTarget.getWidth();
			for(int i=0; i<(bufferPix.length)/buffer.getType();i++){
				for(int j=0; j < buffer.getType(); j++){
					pixels[(int) (index + i%buffer.getWidth() + Math.floor(i/buffer.getWidth())*buffer.getWidth())] += ((int)(bufferPix[i*buffer.getType() + j] &0xFF)<< 8*((buffer.getType()-1)-j));
				}
			}
		}
	}
	public static void setDepth(byte depth){
		Drawer.depth = depth;
	}
	public void setBlendMode(BlendMode mode){
		blendMode = mode; 
	}
}
