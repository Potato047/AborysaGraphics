package com.Aborysa.Graphics.Texture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.Aborysa.Graphics.DrawingBuffer;

public class Texture {
	String name;
	int width, height;
	int[] pixels;
	float xScale = 1;
	float yScale = 1;
	public Texture(BufferedImage img, boolean copy){
		width = img.getWidth();
		height = img.getHeight();
		if(!copy){
			this.pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		}else{
			
		}
	}
	public Texture(int[] pixels, int width,boolean copy){
		this.width = width;
		this.height = pixels.length/width;
		if (!copy){
			this.pixels = pixels;
		}else{
				
		}

	}
	public Texture(DrawingBuffer buffer){
		
	}
	
}
