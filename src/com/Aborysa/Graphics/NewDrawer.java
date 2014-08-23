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
	private static boolean bufferDraw2 = false;
	private static DrawingBuffer2 bufferTarget2 = null;
	private static BlendMode colorBlendMode= new BlendMode(BlendMode.MUL);
	private static BlendMode blendMode = new BlendMode(BlendMode.OVERLAY_1);
	private static DrawingBuffer bufferTarget = null;
	private static BufferedImage imageTarget = null;
	private static Canvas canvasTarget = null;
	private static int depth = 0;
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
	public static void setTarget(DrawingBuffer2 buffer){
		bufferDraw = false;
		canvasDraw = false;
		imageDraw = false;
		bufferDraw2 = true;
		bufferTarget2 = buffer;
	}

	public static void drawImage(int x, int y, BufferedImage image){
		if (image.getRaster().getDataBuffer() instanceof DataBufferByte){
			byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
			drawPixelArray(x,y,image.getWidth(),image.getHeight(),image.getWidth(),pixels);
		}else if (image.getRaster().getDataBuffer() instanceof DataBufferInt){
			int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			drawPixelArray(x,y,image.getWidth(),image.getHeight(),image.getWidth(),pixels);
		}
	}
	public static void drawImagePart(int x, int y, int x2, int y2, BufferedImage image){
		if (image.getRaster().getDataBuffer() instanceof DataBufferByte){
			byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
			
			drawPixelArray(x,y,x2,y2,image.getWidth(),pixels);
		}else if (image.getRaster().getDataBuffer() instanceof DataBufferInt){
			int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			drawPixelArray(x,y,x2,y2,image.getWidth(),pixels);
		}		
	}
	
	public static void drawPixelArray(int x, int y,int x2,int y2, int width, int[] pixelArray){
		if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			for(int i = (x+y*width) ; i< (x2 + y2 * width);i++){
				if(i/width > pixelArray.length) break;
				plotPixel(x+(i%width),y+(int)i/width,pixels,width,pixelArray[i]);
			}
		}
		else if(canvasDraw){
			BufferedImage tempImg = new BufferedImage(width,pixelArray.length/width,BufferedImage.TYPE_INT_ARGB);
			NewDrawer.setTarget(tempImg);
			NewDrawer.drawPixelArray(x, y,x2,y2, width, pixelArray);
			NewDrawer.setTarget(canvasTarget);
			canvasTarget.getBufferStrategy().getDrawGraphics().drawImage(tempImg,x,y,null);
		}else if(bufferDraw2){
			int tempPixel = 0;
			int index = 0;
			int color = 0;
			int a = y*bufferTarget2.getWidth();
			int height = bufferTarget2.getPixelCount()/bufferTarget2.getWidth();
			for(int i=0; i< pixelArray.length;i++){
				int i4 = i;
				if((i4/width) > height){
					break;
				}
				if((x+(i4)%width) > bufferTarget2.getWidth()){
					i = (i/width + 4);
					continue;
				}
				index = x + i4%width + ((int)(i4/width) + y)*bufferTarget2.getWidth();
				color = pixelArray[i];
				bufferTarget2.plotPixelBlend(index, color,depth, blendMode);
			}
		}
		
	}
	public static void drawBuffer(int x, int y ,DrawingBuffer2 buffer){
		if(bufferDraw2){
			int[] pixels = buffer.getPixels();
			drawPixelArray(x,y,buffer.getWidth(),buffer.getPixelCount()/buffer.getWidth(),buffer.getWidth(),pixels);	
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
			drawPixelArray(x,y,buffer.getWidth(),buffer.getPixelCount()/buffer.getWidth(),buffer.getWidth(),pixels);	
		}

	}
	public static void drawPixelArray(int x, int y,int x2, int y2, int width, byte[] pixelArray){
		if(imageDraw){
			int[] pixels = ((DataBufferInt)imageTarget.getRaster().getDataBuffer()).getData();
			for(int i = 0 ; i< pixelArray.length;i+=4){
				int i4 = (i/4);;
				plotPixel(x+((i4)%width),y+(int)((i4)/width),pixels,imageTarget.getWidth(),(pixelArray[i] << 8*3) | (pixelArray[i+1] << 8*2) | (pixelArray[i+2] << 8) | (pixelArray[i+3]));
			}
		}else if(canvasDraw){
			BufferedImage tempImg = new BufferedImage(width,pixelArray.length/width,BufferedImage.TYPE_INT_ARGB);
			NewDrawer.setTarget(tempImg);
			NewDrawer.drawPixelArray(x, y,x2,y2, width, pixelArray);
			NewDrawer.setTarget(canvasTarget);
			canvasTarget.getBufferStrategy().getDrawGraphics().drawImage(tempImg,x,y,null);
		}else if(bufferDraw2){
			int tempPixel = 0;
			int index = 0;
			int color = 0;
			int a = y*bufferTarget2.getWidth();
			int height = bufferTarget2.getPixelCount()/bufferTarget2.getWidth();
			for(int i=0; i< pixelArray.length;i+=4){
				int i4 = i/4;
				if((i4/width) > height){
					break;
				}
				if((x+(i4)%width) > bufferTarget2.getWidth()){
					i = (i/width + 4);
					continue;
				}
				index = x + i4%width + ((int)(i4/width) + y)*bufferTarget2.getWidth();
				color = (pixelArray[i] << 8*3) | (pixelArray[i+1] << 8*2) | (pixelArray[i+2] << 8) | (pixelArray[i+3]);
				bufferTarget2.plotPixelBlend(index, color,depth, blendMode);
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
	public static void setDepth(int depth){
		NewDrawer.depth = depth;
	}
	public static void setBlendMode(BlendMode mode){
		blendMode = mode; 
	}
}
