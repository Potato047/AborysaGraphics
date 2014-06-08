package com.Aborysa.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tester {
	JFrame frame;
	Canvas can;
	Dimension size;
	boolean running = true;
	DrawingBuffer testBuff = new DrawingBuffer(1024,4,32);
	DrawingBuffer tempBuffer;
	BufferedImage tempImage;
	BufferStrategy bufferStrat;
	float tempTime = 0;
	public Tester(int width, int height){
		frame = new JFrame("Test");
		can = new Canvas();
		size = new Dimension(width,height);
		tempBuffer = new DrawingBuffer(width*height,4,width);
		tempImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
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
		testBuff.fill(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0,(byte)0});
		byte[] contains = testBuff.getPixels();
		for (int i=0; i< testBuff.getPixelCount();i++){
			String testStr = "";
			for(int j=0;j<testBuff.getType();j++){
				int temp = 0;
				temp += ((int)(contains[i*testBuff.getType()+j]) & 0xFF);
				testStr = testStr + "[" + j + "]: " + temp  + ", ";
			}
			System.out.println(testStr);
		}

		while(running){
			render();
			can.getBufferStrategy().getDrawGraphics().dispose();
			can.getBufferStrategy().show();
			
		}
	}
	
	private void render(){
		can.paint(can.getBufferStrategy().getDrawGraphics());
		//tempImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		//Drawer.setTarget(can);
		Drawer.setDepth((byte)0xF);
		Drawer.setTarget(tempImage);
		Drawer.drawBuffer((int)(tempTime), 0, testBuff);
		//tempTime+=1;
		//can.getBufferStrategy().getDrawGraphics().drawImage(tempImage, 0, 0,null);
		//BufferedImage testTempImage = new BufferedImage(640,480,BufferedImage.TYPE_INT_ARGB);
		//Drawer.setTarget(testTempImage);
		Drawer.setTarget(can);
		Drawer.drawImage(0, 0, tempImage);
		//can.getBufferStrategy().getDrawGraphics().drawImage(tempImage,0,0,null);
		//tempImage.flush();
		/*	testBuff.fill(new byte[]{(byte)0xFF,(byte)((tempTime+32)%256),(byte)0,(byte)0});
		Drawer.drawBuffer((tempTime+32)%256, 0, testBuff);
		testBuff.fill(new byte[]{(byte)0xFF,(byte)((tempTime+64)%256),(byte)0,(byte)0});
		Drawer.drawBuffer((tempTime+64)%256, 0, testBuff);
	*/	
		
	}
	public static void main(String args[]){
		new Tester(640,480);
	}
}
