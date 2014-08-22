package com.Aborysa.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferByte;
import java.util.Random;
	
public class Drawer {
	//This class will organize drawing
	private static boolean bufferDraw = false;
	private static boolean canvasDraw = false;
	private static boolean imageDraw = false;
	private static boolean bufferDraw2 = false;
	private static DrawingBuffer2 bufferTarget2 = null;
	private static DrawingBuffer bufferTarget = null;
	private static BufferedImage imageTarget = null;
	private static Canvas canvasTarget = null;
	private static int depth = 0;
	private static BlendMode blendMode;
	private static Color color;
	private static Random ran = new Random();
	//private static byte[] depthBuffer;
	
	static int timer = 0;
	public static void setTarget(Canvas canvas){
		bufferDraw = false;
		canvasDraw = true;
		imageDraw = false;
		bufferDraw2 = false;
		canvasTarget = canvas;	
	}

	public static void setTarget(DrawingBuffer buffer){
		bufferDraw = true;
		canvasDraw = false;
		imageDraw = false;
		bufferDraw2 = false;
		bufferTarget = buffer;
	}
	public static void setTarget(BufferedImage image){
		bufferDraw = false;
		canvasDraw = false;
		bufferDraw2 = false;
		imageDraw = true;
		imageTarget = image;
	}
	public static void setTarget(DrawingBuffer2 buffer){
		bufferDraw = false;
		canvasDraw = false;
		imageDraw = false;
		bufferDraw2 = true;
		bufferTarget2 = buffer;
	}
	public static void drawImage(int x, int y, BufferedImage image){
		//drawBuffer(x, y, new DrawingBuffer(image,depth));
		
	//	int[] pixels = new int[image.getWidth()*image.getHeight() * 4];
	//	int width = image.getWidth();
	//	int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	//	int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	//	image.getRaster().getPixels(0, 0, image.getWidth(), image.getHeight(), pixels);
	//	drawPixelArray(x,y,width,pixels);
	}
	public static void drawPixelColorArray(int x, int y, int width, int[] pixelArray){
		if(bufferDraw){
			
		}else if(canvasDraw){
			
		}else if(imageDraw){
			
		}
	}
	
	public static void drawPixelArray(int x, int y, int width, int[] pixelArray){
		if(bufferDraw){
			
		}else if(canvasDraw){
			BufferedImage tempImg = new BufferedImage(width,pixelArray.length/width,BufferedImage.TYPE_INT_ARGB);
			Drawer.setTarget(tempImg);
			Drawer.drawPixelArray(x, y, width, pixelArray);
			Drawer.setTarget(canvasTarget);
			canvasTarget.getBufferStrategy().getDrawGraphics().drawImage(tempImg,x,y,null);
		}
		else if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			int tempPixel = 0;
			int index = 0;
			int a = y*imageTarget.getWidth();
			for(int i=0; i< pixelArray.length;i++){
				if((i/width) > imageTarget.getHeight()){
					break;
				}
				if((x+i%width) > imageTarget.getWidth()){
					i = (i/width + 1);
					continue;
				}
				index = x+(a + i%width + (int)Math.floor(i/width)*imageTarget.getWidth());
				tempPixel = pixels[index];
				tempPixel = blendMode.getBlend(tempPixel, pixelArray[i]);
				pixels[index] = pixelArray[i];
			}
		}else if(bufferDraw2){
			//int[] pixels = bufferTarget2.getPixels();
			int tempPixel = 0;
			int index = 0;
			int a = y*bufferTarget2.getWidth();
			int height = bufferTarget2.getWidth()/bufferTarget2.getPixelCount();
			for(int i=0; i< pixelArray.length;i++){
				if((i/width) > height){
					break;
				}
				if((x+i%width) > bufferTarget2.getWidth()){
					i = (i/width + 1);
					continue;
				}
				index = x+(a + i%width + (int)Math.floor(i/width)*bufferTarget.getWidth());
				//tempPixel = pixels[index];
				//tempPixel = blendMode.getBlend(tempPixel, pixelArray[i]);
				//pixels[index] = pixelArray[i];
				bufferTarget2.plotPixelBlend(index, pixelArray[i],depth, blendMode);
			}
			
		}
	}
	public static void drawBuffer(int x, int y, DrawingBuffer2 buffer){
		if(bufferDraw2){
			int[] pixels = buffer.getPixels();
			drawPixelArray(x,y,buffer.getWidth(),pixels);	
		}
		if(canvasDraw){
			BufferedImage tempImg = new BufferedImage(buffer.getWidth(),buffer.getPixelCount()/buffer.getWidth(),BufferedImage.TYPE_INT_ARGB);
			Drawer.setTarget(tempImg);
			Drawer.drawPixelArray(x, y, buffer.getWidth(), buffer.getPixels());
			Drawer.setTarget(canvasTarget);
			canvasTarget.getBufferStrategy().getDrawGraphics().drawImage(tempImg,x,y,null);		
		}
		if(imageDraw){
			int[] pixels = buffer.getPixels();
			drawPixelArray(x,y,buffer.getWidth(),pixels);
		}

	}
	public static void drawBuffer(int x, int y, DrawingBuffer buffer){
		
		if(bufferDraw){
			
		}else if(canvasDraw){
			
			BufferedImage tempImg = new BufferedImage(buffer.getWidth(),buffer.getPixelCount()/buffer.getWidth(),BufferedImage.TYPE_INT_ARGB);
			Drawer.setTarget(tempImg);
			Drawer.drawBuffer(0, 0, buffer);
			Drawer.setTarget(canvasTarget);
			canvasTarget.getBufferStrategy().getDrawGraphics().drawImage(tempImg, x, y, null);
			
		}else if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			byte[] bufferPix = buffer.getPixels();
			int index = x + y * imageTarget.getWidth();
			int arrayIndex = 0;
			int temp = 0;
			for(int i=0; i<(bufferPix.length)/buffer.getType();i++){
				if ((int) (index + i%buffer.getWidth() + (Math.floor(i/buffer.getWidth())*imageTarget.getWidth())) >= pixels.length){
					break;
				}
				if(!(x + i%buffer.getWidth() > imageTarget.getWidth())){
					arrayIndex = (int) (index + i%buffer.getWidth() + (Math.floor(i/buffer.getWidth())*imageTarget.getWidth()));
					temp = pixels[arrayIndex];
					temp = blendMode.getBlend(temp, ((((int)(bufferPix[i*buffer.getType()]) & 0xFF) << (8*3)) | (((int)(bufferPix[i*buffer.getType()+1]) & 0xFF) << (8*2)) | (((int)(bufferPix[i*buffer.getType()+2]) & 0xFF) << (8)) | (((int)(bufferPix[i*buffer.getType()+3]) & 0xFF))));
					pixels[arrayIndex] = temp;
			
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
