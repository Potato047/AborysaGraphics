package com.Aborysa.Graphics;

public class BlendMode {
	//Will contain information on what and how to blend
	public static final int NONE = 0xF;
	public static final int ADD = 1;
	public static final int SUB = 2;
	public static final int OVERLAY_1 = 3;
	public static final int MUL = 4;
	public static final int SCREEN = 6;
	//^ SUB | AND = OVERLAY
	public static final int ALPHA =   0b10000;
	public static final int RED =    0b100000;
	public static final int GREEN = 0b1000000;	
	public static final int BLUE = 0b10000000;	
	int tresholdMin = 0, tresholdMax = 255;
	float rFactor = 1, gFactor = 1,bFactor = 1, aFactor = 1;
	byte blendType = 0; //NONE, ADD, SUB
	byte blendFactor = 0; //ALPHA, RED, GREEN, BLUE
	//Overlay blending:
	// colour = (topColour/255 * topAlpha/255) + (backColour * (1 - topAlpha/255)
	//Mul blending:
	//colour = colour/255 * topColour/255
	//Screen blending:
	//colour = 1 - (1-colour/255)*(1-topColour/255) 
	//overlay2
	public BlendMode(int MODE, float r, float g, float b, float a){
		blendType = (byte) (MODE & 0xF);
		blendFactor = (byte) ((MODE >> 4)& 0xF);
		rFactor = r;
		gFactor = g;
		bFactor = b;
		aFactor = a;
	}
	public BlendMode(int MODE){
		this(MODE,1f,1f,1f,1f);
	}
	public int getBlend(int background, int forground){
		if(blendType == NONE)return forground;
		//Lag issues here got to make this code more optimal
		int blend = 0;
		int r1 = ((background >> (2*8)) & 0xFF);
		int g1 = ((background >> 8) & 0xFF);
		int b1 = (background & 0xFF);
		int a1 = ((background >> (3*8)) & 0xFF);
		int r2 = ((forground >> (2*8)) & 0xFF);
		int g2 = (forground >> (8) & 0xFF);
		int b2 = (forground & 0xFF);
		int a2 = (forground >> (3*8) & 0xFF);
		switch(blendType){
			case 0xF:
			
			break;
			case 1:
				//ADD:
				r2 = (r2*a2/255)+(r1*a1/255);
				g2 = (g2*a2/255)+(g1*a1/255);
				b2 = (b2*a2/255)+(b1*a1/255);
				a2 += a1;
			break;
			case 2:
				//SUB:
			break;
			case 3:
				//Overlay_1
				r2 = (r2 * a2/255) + (r1 * (1 - a2/255));
				g2 = (g2 * a2/255) + (g1 * (1 - a2/255));
				b2 = (b2 * a2/255) + (b1 * (1 - a2/255));
				a2 += a1;
			break;
				//Mul
			case 4:
				r2 = (r2*r1)/255;
				g2 = (g2*g1)/255;
				b2 = (b2*b1)/255;
				a2 += a1;
			break;	
			case 6:
				//colour = 1 - (1-colour/255)*(1-topColour/255) 
				r2 = (1-(1-r1/255) * (1-r2/255))*255;
				g2 = (1-(1-g1/255) * (1-g2/255))*255;
				b2 = (1-(1-b1/255) * (1-b2/255))*255;
				a2 += a1;
			break;

		}
		if(r2 > tresholdMax) r2=tresholdMax;
		if(g2 > tresholdMax) g2=tresholdMax;
		if(b2 > tresholdMax) b2=tresholdMax;
		if(a2 > tresholdMax) a2=tresholdMax;
		if(r2 < tresholdMin) r2=tresholdMin;
		if(g2 < tresholdMin) g2=tresholdMin;
		if(b2 < tresholdMin) b2=tresholdMin;
		if(a2 < tresholdMin) a2=tresholdMin;
	
		blend = (b2 | (g2 << 8) | (r2 << 16) | (a2 << 24));
		return blend;
	}
	
}