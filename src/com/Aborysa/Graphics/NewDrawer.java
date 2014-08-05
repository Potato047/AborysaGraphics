package com.Aborysa.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferByte;
import java.util.Random;
	
public class NewDrawer {
	//This class will organize drawing
	private static boolean bufferDraw = false;
	private static boolean canvasDraw = false;
	private static boolean imageDraw = false;
	private static boolean colorBlending = false;
	private static BlendMode colorBlendMode= new BlendMode(BlendMode.MUL);
	private static BlendMode blendMode = new BlendMode(BlendMode.OVERLAY_1);
	private static DrawingBuffer bufferTarget = null;
	private static BufferedImage imageTarget = null;
	private static Canvas canvasTarget = null;
	private static byte depth = 0;
	private static Color color;
	private static Random ran = new Random();
	static int timer = 0;
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
	public static void drawTexture(){
		
	}
	public static void drawImage(int x, int y, BufferedImage image){
		byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		drawPixelArray(x,y,image.getWidth(),pixels);
	}

	public static void drawPixelArray(int x, int y, int width, int[] pixelArray){
		if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			for(int i = 0 ; i< pixelArray.length;i++){
				plotPixel(x+(i%width),y+(int)i/width,pixels,width,pixelArray[i]);
			}
		}	
	}
	public static void drawPixelArray(int x, int y, int width, byte[] pixelArray){
		if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			for(int i = 0 ; i< pixelArray.length;i+=4){
				plotPixel(x+((i/4)%width),y+(int)((i/4)/width),pixels,imageTarget.getWidth(),(pixelArray[i] << 8*3) | (pixelArray[i+1] << 8*2) | (pixelArray[i+2] << 8) | (pixelArray[i+3]));
			}
		}	
	}
	public static void drawBuffer(int x, int y, DrawingBuffer buffer){
		
		if(bufferDraw){
			
		}else if(canvasDraw){
						
		}else if(imageDraw){

		}
	}
	public static void plotPixel(int x, int y, int[] pixels, int width, int color){
		int index = x + (y*width);
		if(!(index >= pixels.length))
			pixels[index]  = blendMode.getBlend(pixels[index], color);
	}
	public static void plotPixel(int index, int[] pixels, int width, int color){
		if(!(index >= pixels.length))
			pixels[index] = blendMode.getBlend(pixels[index], color);
	}
	public static void setDepth(byte depth){
		NewDrawer.depth = depth;
	}
	public static void setBlendMode(BlendMode mode){
		blendMode = mode; 
	}
}
