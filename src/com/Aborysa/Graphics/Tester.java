package com.Aborysa.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	BufferStrategy bufferStrat;
	BlendMode bMode = new BlendMode(BlendMode.ADD);
	float tempTime = 0;
	public Tester(int width, int height){
		frame = new JFrame("Test");
		can = new Canvas();
		size = new Dimension(width,height);
		tempBuffer = new DrawingBuffer(width*height,4,width);
		tempImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		try {
			testLoadImg = ImageIO.read(new File("C:/Users/HNK/git/AborysaGraphics/bin/img/testImg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		testBuff4 = new DrawingBuffer(testLoadImg, (byte)0xF);
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
		byte[] contains = testBuff4.getPixels();
		for (int i=0; i< testBuff4.getPixelCount();i++){
			String testStr = "";
			for(int j=0;j<testBuff4.getType();j++){
				int temp = 0;
				temp += ((int)(contains[i*testBuff4.getType()+j]) & 0xFF);
				testStr = testStr + "[" + j + "]: " + temp  + ", ";
			}
			System.out.println(testStr);
		}
 //MEH
		while(running){
			render();
			can.getBufferStrategy().getDrawGraphics().dispose();
			can.getBufferStrategy().show();
			
		}
	}
	
	private void render(){
		
		can.paint(can.getBufferStrategy().getDrawGraphics());
		Drawer.setDepth((byte)0xF);
		Drawer.setTarget(tempImage);
		Drawer.setBlendMode(new BlendMode(BlendMode.ADD)); //Does not work with target set to a canvas for now
		Drawer.drawBuffer((int)(64+tempTime), 64, testBuff);
		Drawer.drawBuffer((int)(64+16+tempTime), 64, testBuff2);
		Drawer.drawBuffer((int)(64+16+tempTime), 64+16, testBuff3);
		Drawer.drawBuffer((int)tempTime,64,testBuff4);
		
		//Drawer.drawBuffer(64, 64+16, testBuff);
		tempTime +=0.5;
		Drawer.setTarget(can);
	//	Drawer.drawImage(0, 0, tempImage);		//<-- Very slow, needs optimization  
		can.getBufferStrategy().getDrawGraphics().drawImage(tempImage, 0, 0, null);
	//	can.getBufferStrategy().getDrawGraphics().drawImage(testLoadImg, 0, 0, null);
		
	}
	public static void main(String args[]){
		new Tester(640,480);
	}
}
