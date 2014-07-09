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
		DrawingBuffer buffer = new DrawingBuffer(image,depth);
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
			Drawer.setTarget(tempImg);
			Drawer.drawBuffer(0, 0, buffer);
			Drawer.setTarget(canvasTarget);
			canvasTarget.getBufferStrategy().getDrawGraphics().drawImage(tempImg, x, y, null);
			/*int[] pixels = ((DataBufferInt)tempImg.getRaster().getDataBuffer()).getData();
			byte[] bufferPix = buffer.getPixels();
			int index = x + y * canvasTarget.getWidth();
	
			for(int i=0; i<(bufferPix.length)/buffer.getType();i++){
				int temp = 0;
			/*	for(int j=0; j < buffer.getType(); j++){
					if(j>4)break;
					pixels[i] |= ((int)(bufferPix[i*buffer.getType() + (buffer.getType() - (j+1))] &0xFF) << 8*(j));
				}
				temp = ((((int)(bufferPix[i*buffer.getType()]) & 0xFF) << (8*3)) | (((int)(bufferPix[i*buffer.getType()+1]) & 0xFF) << (8*2)) | (((int)(bufferPix[i*buffer.getType()+2]) & 0xFF) << (8)) | (((int)(bufferPix[i*buffer.getType()+3]) & 0xFF)));
				pixels[(int) (index + i%buffer.getWidth() + (Math.floor(i/buffer.getWidth())*canvasTarget.getWidth()))] = temp;
				//pixels[i] |= temp;
			}*/
		}else if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			byte[] bufferPix = buffer.getPixels();
			int index = x + y * imageTarget.getWidth();
			for(int i=0; i<(bufferPix.length)/buffer.getType();i++){
				if ((int) (index + i%buffer.getWidth() + (Math.floor(i/buffer.getWidth())*imageTarget.getWidth())) >= pixels.length){
					break;
				}
				if(!(x + i%buffer.getWidth() > imageTarget.getWidth())){
					int temp = pixels[(int) (index + i%buffer.getWidth() + (Math.floor(i/buffer.getWidth())*imageTarget.getWidth()))];
					/*for(int j=0; j < buffer.getType(); j++){
						if(j>4)break;
						temp |= ((int)(bufferPix[i*buffer.getType() + (buffer.getType() - (j+1))] &0xFF) << 8*(j));
					}*/
				//	temp = ((((int)(bufferPix[i*buffer.getType()]) & 0xFF) << (8*3)) | (((int)(bufferPix[i*buffer.getType()+1]) & 0xFF) << (8*2)) | (((int)(bufferPix[i*buffer.getType()+2]) & 0xFF) << (8)) | (((int)(bufferPix[i*buffer.getType()+3]) & 0xFF)));
					temp = blendMode.getBlend(temp, ((((int)(bufferPix[i*buffer.getType()]) & 0xFF) << (8*3)) | (((int)(bufferPix[i*buffer.getType()+1]) & 0xFF) << (8*2)) | (((int)(bufferPix[i*buffer.getType()+2]) & 0xFF) << (8)) | (((int)(bufferPix[i*buffer.getType()+3]) & 0xFF))));
					/*	System.out.println(bufferPix[i*buffer.getType()]);
					System.out.println(bufferPix[i*buffer.getType() + 1]);		
					System.out.println(bufferPix[i*buffer.getType() + 2]);
					System.out.println(bufferPix[i*buffer.getType() + 3]);
					System.out.println(temp);
					System.exit(0);*/
					pixels[(int) (index + i%buffer.getWidth() + (Math.floor(i/buffer.getWidth())*imageTarget.getWidth()))] = temp;
				}
			}
		}
	}
	public static void setDepth(byte depth){
		Drawer.depth = depth;
	}
	public static void setBlendMode(BlendMode mode){
		blendMode = mode; 
	}
}
