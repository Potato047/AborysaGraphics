package com.Aborysa.Test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Aborysa.Graphics.BlendMode;
import com.Aborysa.Graphics.Drawer;
import com.Aborysa.Graphics.DrawingBuffer;
import com.Aborysa.Graphics.NewDrawer;
import com.Aborysa.Graphics.Texture.ImageLoader;

public class Tester {
	JFrame frame;
	Canvas can;
	Dimension size;
	boolean running = true;
	DrawingBuffer testBuff = new DrawingBuffer(1024,4,32);
	DrawingBuffer testBuff2 = new DrawingBuffer(1024,4,32);
	DrawingBuffer testBuff3 = new DrawingBuffer(1024,4,32);
	DrawingBuffer testBuff4;
	BufferedImage testLoadImg;
	DrawingBuffer tempBuffer;
	BufferedImage tempImage;
	BufferedImage imageByURL;
	BufferStrategy bufferStrat;
	FPSCounter fps = new FPSCounter();
	BlendMode bMode = new BlendMode(BlendMode.ADD);
	int[] pixelArrayTest = new int[32*32];
	float tempTime = 0;
	public Tester(int width, int height){
		frame = new JFrame("Test");
		can = new Canvas();
		size = new Dimension(width,height);
		tempBuffer = new DrawingBuffer(width*height,4,width);
		tempImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	/*	try {
			testLoadImg = ImageIO.read(new File("C:/Users/HNK/git/AborysaGraphics/rec/img/ant.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		testBuff4 = new DrawingBuffer(testLoadImg, (byte)0xF);*/
		for(int i=0; i<pixelArrayTest.length;i++){
			pixelArrayTest[i] = 0xFF00FF00;
		}
		init();
	}
	private void init(){
		//System.setProperty("sun.awt.noerasebackground", "true");
		can.setPreferredSize(size);
		can.setBackground(Color.BLACK);
		frame.add(can);
		frame.setPreferredSize(size);
		frame.setSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		can.createBufferStrategy(2);
		Drawer.setTarget(can);
		Drawer.setBlendMode(bMode);
		tempBuffer.fill(new byte[]{0,0,0,0});
		testBuff.fill(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0,(byte)0});
		testBuff2.fill(new byte[]{(byte)0xFF,(byte)0,(byte)0xFF,(byte)0});
		testBuff3.fill(new byte[]{(byte)0xFF,(byte)0,(byte)0,(byte)0xFF});
		testLoadImg = ImageLoader.loadImage("C:\\Users\\HNK\\git\\AborysaGraphics\\rec\\img\\ant.png");
		try {
			imageByURL = ImageLoader.getImageFromURL(new URL("http://fc09.deviantart.net/fs70/i/2010/031/9/0/Net_Hack_Sprite_sheet_v1_1_by_Chubbs_99.jpg"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		/*byte[] contains = testBuff4.getPixels();
		for (int i=0; i< testBuff4.getPixelCount();i++){
			String testStr = "";
			for(int j=0;j<testBuff4.getType();j++){
				int temp = 0;
				temp += ((int)(contains[i*testBuff4.getType()+j]) & 0xFF);
				testStr = testStr + "[" + j + "]: " + temp  + ", ";
			}
			System.out.println(testStr);
		}*/
 //MEH
		while(running){
			render();
			can.getBufferStrategy().getDrawGraphics().dispose();
			can.getBufferStrategy().show();
			fps.update();
			System.out.println("FPS: " + fps.getFPS());
		}
	}
	
	private void render(){
		
		can.paint(can.getBufferStrategy().getDrawGraphics());
		Drawer.setDepth((byte)0xF);
		Drawer.setTarget(tempImage);
		Drawer.setBlendMode(new BlendMode(BlendMode.NONE)); //Does not work with target set to a canvas for now
	//	Drawer.drawBuffer((int)(64+tempTime), 64, testBuff);
	//	Drawer.drawBuffer((int)(64+16+tempTime), 64, testBuff2);
	//	Drawer.drawBuffer((int)(64+16+tempTime), 64+16, testBuff3);
	//	Drawer.drawPixelArray(0, 0, 32, pixelArrayTest);
		NewDrawer.setBlendMode(new BlendMode(BlendMode.NONE));
		NewDrawer.setTarget(tempImage);
		for(int i = 0; i < 64;i++){
			NewDrawer.drawImage((i%8)*32 , 32*(i/8), testLoadImg);
		}
	//	Drawer.drawBuffer(64, 64+16, testBuff);
	//	tempTime +=0.1;
	//	Drawer.setTarget(can);
	//	Drawer.drawImage(0, 0, tempImage);		//<-- Very slow, needs optimization(slowest fucking thing ever, drops the FPS to a cap of 15 >:( )
		can.getBufferStrategy().getDrawGraphics().drawImage(tempImage, 0, 0, null);
	//	can.getBufferStrategy().getDrawGraphics().drawImage(testLoadImg, 0, 0, null);
		
	}
	public static void main(String args[]){
		new Tester(640,480);
	}
}
